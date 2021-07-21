package com.cong.android.imageloader.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.cong.android.imageloader.core.LoaderBuilder;
import com.cong.android.imageloader.core.type.DiskCacheType;
import com.cong.android.imageloader.core.type.ImageType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.cong.android.imageloader.core.type.ImageType.DEFAULT;

/**
 * author : Cong
 * date   : 2020-10-09
 */
public class GlideBuilder implements LoaderBuilder<Target> {

    /**
     * 预览图
     */
    private RequestBuilder thumbnailBuilder;
    /**
     * 需要加载的对象
     */
    private Object object;
    /**
     * 预加载大小
     */
//    private int[] preloadSize = null;
    /**
     * 请求选项集
     */
    private RequestOptions requestOptions;
    /**
     * 请求管理器
     */
    @Nullable
    private RequestManager requestManager;
    /**
     * 监听
     */
    private RequestListener requestListener;
    /**
     * 图片类型
     */
    private ImageType imageType = DEFAULT;

    private List<Transformation<Bitmap>> transformationList = new ArrayList<>();
    private CenterCrop centerCrop = new CenterCrop();
    private FitCenter fitCenter = new FitCenter();

    GlideBuilder() {
        this(null);
    }

    GlideBuilder(@Nullable RequestManager requestManager) {
        this.requestManager = requestManager;
        this.requestOptions = new RequestOptions();
    }

    @Override
    public LoaderBuilder load(@Nullable Uri uri) {
        object = uri;
        return this;
    }

    @Override
    public LoaderBuilder load(@Nullable File file) {
        object = file;
        return this;
    }

    @Override
    public LoaderBuilder load(@Nullable String path) {
        object = path;
        return this;
    }

    @Override
    public LoaderBuilder load(@Nullable Bitmap bitmap) {
        object = bitmap;
        return this;
    }

    @Override
    public LoaderBuilder load(@Nullable Drawable drawable) {
        object = drawable;
        return this;
    }

    @Override
    public LoaderBuilder load(int resourceId) {
        object = resourceId;
        return this;
    }

    @Override
    public LoaderBuilder thumbnail(@NonNull LoaderBuilder loaderBuilder) {
        RequestBuilder requestBuilder;
        GlideBuilder glideBuilder = (GlideBuilder) loaderBuilder;
        switch (glideBuilder.imageType) {
            case STATIC:
                requestBuilder = glideBuilder.requestManager.asBitmap();
                break;
            case GIF:
                requestBuilder = glideBuilder.requestManager.asGif();
                break;
            case DEFAULT:
            default:
                requestBuilder = glideBuilder.requestManager.asDrawable();
                break;
        }
        thumbnailBuilder = requestBuilder.load(glideBuilder.object);
        return this;
    }

    @Override
    public LoaderBuilder convert(ImageType type) {
        this.imageType = type;
        return this;
    }

    @Override
    public LoaderBuilder preload() {
//        preloadSize = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        return this;
    }

    @Override
    public LoaderBuilder preload(int width, int height) {
//        preloadSize = new int[]{width, height};
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder placeholder(@Nullable Drawable drawable) {
        requestOptions.placeholder(drawable);
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder placeholder(int resourceId) {
        requestOptions.placeholder(resourceId);
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder error(@Nullable Drawable drawable) {
        requestOptions.error(drawable);
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder error(int resourceId) {
        requestOptions.error(resourceId);
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder memoryCache(boolean isOpen) {
        requestOptions.skipMemoryCache(!isOpen);
        return this;
    }

    @Override
    public LoaderBuilder diskCacheStrategy(DiskCacheType strategy) {
        switch (strategy) {
            default:
            case DEFAULT_DISK_CACHE:
                requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            case NONE_DISK_CACHE:
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case META_DATE_DISK_CACHE:
                requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case RESOURCE_DISK_CACHE:
                requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case ALL_DISK_CACHE:
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder resize(int size) {
        requestOptions.override(size);
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder resize(int width, int height) {
        requestOptions.override(width, height);
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder circle() {
        transformationList.add(new CircleCrop());
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder centerCrop() {
        transformationList.add(new CenterCrop());
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder roundCorners(int radius) {
        transformationList.add(new RoundedCorners(radius));
        return this;
    }

    @Override
    public LoaderBuilder granularRoundedCorners(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        transformationList.add(new GranularRoundedCorners(topLeft, topRight, bottomRight, bottomLeft));
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public LoaderBuilder blur(Context context, @FloatRange(from = 1, to = 25) float blurRadius) {
        transformationList.add(new Blur(context, blurRadius));
        return this;
    }

    @Override
    public <Resource> LoaderBuilder listener(final OnLoadListener<Resource> callback) {
        requestListener = new RequestListener<Resource>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Resource> target, boolean isFirstResource) {
                callback.failed();
                return false;
            }

            @Override
            public boolean onResourceReady(Resource resource, Object model, Target<Resource> target, DataSource dataSource, boolean isFirstResource) {
                callback.success(resource);
                return false;
            }
        };
        return this;
    }

    /**
     * 必须在后台线程
     *
     * @return Drawable
     * @throws ExecutionException   ExecutionException
     * @throws InterruptedException InterruptedException
     */
    @Override
    @Nullable
    public Drawable save() throws ExecutionException, InterruptedException {
        if (requestManager == null) return null;
        return requestManager.load(object).apply(transformOptions()).submit().get();
    }

    @Override
    @Nullable
    public void into(final ImageView imageView) {
        RequestBuilder builder = build();
        if (builder == null) return;
        builder.apply(transformOptions()).into(imageView);
    }

    @Override
    public <Resource> void into(final OnLoadListener<Resource> loadListener) {
        RequestBuilder builder = build();
        if (builder == null) {
            loadListener.failed();
        } else {
            builder.apply(transformOptions()).into(new SimpleTarget<Resource>() {

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    loadListener.failed();
                }

                @Override
                public void onResourceReady(@NonNull Resource resource, @Nullable Transition<? super Resource> transition) {
                    loadListener.success(resource);
                }
            });
        }
    }

    /**
     * 构造器
     */
    @Nullable
    private RequestBuilder build() {
        if (requestManager == null) return null;
        RequestBuilder requestBuilder;
        switch (imageType) {
            case STATIC:
                requestBuilder = requestManager.asBitmap();
                break;
            case GIF:
                requestBuilder = requestManager.asGif();
                break;
            case DEFAULT:
            default:
                requestBuilder = requestManager.asDrawable();
                break;
        }
        requestBuilder = requestBuilder.listener(requestListener).load(object);
        if (thumbnailBuilder != null) {
            return requestBuilder.thumbnail(thumbnailBuilder);
        } else {
            return requestBuilder;
        }
    }

    private RequestOptions transformOptions() {
        final int position = transformationList.indexOf(centerCrop);
        if (position == -1) {
            transformationList.add(0, fitCenter);
        } else {
            Collections.swap(transformationList, 0, position);
        }
        return requestOptions.transform(new MultiTransformation<>(transformationList));
    }

    public static class SimpleOnLoadListener<T> implements LoaderBuilder.OnLoadListener<T> {

        @Override
        public void success(T drawable) {

        }

        @Override
        public void failed() {

        }
    }
}
