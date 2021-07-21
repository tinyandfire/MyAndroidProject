package com.cong.android.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * author : Cong
 * date   : 2020-10-09
 */
public class Blur extends BitmapTransformation {

    private Context context;

    @FloatRange(from = 1, to = 25)
    private float blurRadius;

    Blur(Context context, @FloatRange(from = 1, to = 25) float blurRadius) {
        this.context = context;
        this.blurRadius = blurRadius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap outputBitmap = Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, false);
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript, outputBitmap);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        blurScript.setRadius(blurRadius);
        blurScript.setInput(input);
        blurScript.forEach(output);
        output.copyTo(outputBitmap);

        input.destroy();
        output.destroy();
        blurScript.destroy();
        renderScript.destroy();
        return outputBitmap;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        byte[] radiusData = ByteBuffer.allocate(4).putFloat(blurRadius).array();
        messageDigest.update(radiusData);
    }
}
