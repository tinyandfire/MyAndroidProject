package com.example.myview.myuiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatImageView;

import com.cong.android.imageloader.core.LoaderBuilder;
import com.cong.android.imageloader.core.type.ImageType;
import com.example.myview.R;


/**
 * author : Cong
 * date   : 2020-10-09
 */
public class ProgressImageView extends AppCompatImageView {

    private final float DEFAULT_STROKE_WIDTH = 10f;

    private int max;
    private int progress;
    private float radius;
    private int progressColor;
    private int progressBackgroundColor;

    private volatile boolean showProgress = false;
    private Paint paint = new Paint();
    private RectF rectF = new RectF();
    private String sourceUrl;

    public ProgressImageView(Context context) {
        this(context, null);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initPaint();
        this.initAttributeSet(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showProgress) {
            updateRectF();
            paint.setColor(progressBackgroundColor);
            canvas.drawCircle(getWidth() * 0.5f, getHeight() * 0.5f, radius, paint);
            paint.setColor(progressColor);
            canvas.drawArc(rectF, -90, 360f * progress / max, false, paint);
        }
    }

    private void initPaint() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void initAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressImageView);
        showProgress = typedArray.getBoolean(R.styleable.ProgressImageView_showProgress, true);
        max = typedArray.getInteger(R.styleable.ProgressImageView_max, 100);
        progress = typedArray.getInteger(R.styleable.ProgressImageView_progress, 0);
        int color = typedArray.getColor(R.styleable.ProgressImageView_progressColor, Color.WHITE);
        float strokeWidth = typedArray.getDimension(R.styleable.ProgressImageView_progressStroke, DEFAULT_STROKE_WIDTH);
        int bgColor = typedArray.getColor(R.styleable.ProgressImageView_progressBackgroundColor, Color.parseColor("#33000000"));
        float width = typedArray.getDimension(R.styleable.ProgressImageView_progressWidth, 100);
        float height = typedArray.getDimension(R.styleable.ProgressImageView_progressHeight, 100);
        typedArray.recycle();

        setProgressSize(width, height);
        setProgressColor(color);
        setProgressBarWidth(strokeWidth);
        setProgressBackgroundColor(bgColor);
    }

    public void setProgressSize(float w, float h) {
        radius = Math.min(w, h);
    }

    private void updateRectF() {
        rectF.left = getWidth() * 0.5f - radius;
        rectF.right = getWidth() * 0.5f + radius;
        rectF.top = getHeight() * 0.5f - radius;
        rectF.bottom = getHeight() * 0.5f + radius;
    }

    public void setProgressBarWidth(float width) {
        paint.setStrokeWidth(width);
        invalidate();
    }

    public void setProgressColor(@ColorInt int color) {
        progressColor = color;
        invalidate();
    }

    public void setProgressBackgroundColor(@ColorInt int color) {
        progressBackgroundColor = color;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void bindUrl(String url) {
        sourceUrl = url;
    }

    public void load(final LoaderBuilder builder) {
        load(builder, this::setProgress);
    }

    public void load(final LoaderBuilder builder, OnLoadImageListener onLoadImageListener) {
        if (sourceUrl == null) throw new NullPointerException("The sourceUrl of resource image must be bind !");
        ready(onLoadImageListener);
        builder.convert(ImageType.DEFAULT)
                .listener(new LoaderBuilder.OnLoadListener<Drawable>() {
                    @Override
                    public void success(Drawable drawable) {
                        recycle();
                    }

                    @Override
                    public void failed() {
                        recycle();
                    }
                })
                .into(this);
    }

    private void ready(OnLoadImageListener onLoadImageListener) {
        showProgress(true);
//        ProgressInterceptor.addListener(sourceUrl, onLoadImageListener::onLoading);
    }

    public void recycle() {
        showProgress(false);
//        ProgressInterceptor.removeListener(sourceUrl);
    }

    private void showProgress(boolean isShow) {
        showProgress = isShow;
        postInvalidate();
    }

    public interface OnLoadImageListener {
        void onLoading(int progress);
    }
}
