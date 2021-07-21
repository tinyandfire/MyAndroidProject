package com.cong.android.imageloader.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;

import com.cong.android.imageloader.core.type.DiskCacheType;
import com.cong.android.imageloader.core.type.ImageType;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * author : Cong
 * date   : 2020-10-09
 */
public interface LoaderBuilder<T> {

    /**
     * 读取uri
     *
     * @param uri uri
     */
    LoaderBuilder load(@Nullable Uri uri);

    /**
     * 读取file
     *
     * @param file file
     */
    LoaderBuilder load(@Nullable File file);

    /**
     * 读取path
     *
     * @param path path
     */
    LoaderBuilder load(@Nullable String path);

    /**
     * 读取bitmap
     *
     * @param bitmap bitmap
     */
    LoaderBuilder load(@Nullable Bitmap bitmap);

    /**
     * 读取drawable
     *
     * @param drawable drawable
     */
    LoaderBuilder load(@Nullable Drawable drawable);

    /**
     * 读取resourceId
     *
     * @param resourceId resourceId
     */
    LoaderBuilder load(@DrawableRes int resourceId);

    /**
     * 缩略图 thumbnail
     * 构造器
     */
    LoaderBuilder thumbnail(LoaderBuilder thumbnailBuilder);

    /**
     * 图片类型转换
     *
     * @param type static dynamic
     */
    LoaderBuilder convert(ImageType type);

    /**
     * 预加载
     */
    LoaderBuilder preload();

    /**
     * 预加载
     *
     * @param width  宽
     * @param height 高
     */
    LoaderBuilder preload(int width, int height);

    /**
     * 预览图
     *
     * @param drawable drawable
     */
    LoaderBuilder placeholder(@Nullable Drawable drawable);

    /**
     * 预览图
     *
     * @param resourceId resourceId
     */
    LoaderBuilder placeholder(@DrawableRes int resourceId);

    /**
     * 异常图
     *
     * @param drawable drawable
     */
    LoaderBuilder error(@Nullable Drawable drawable);

    /**
     * 异常图
     *
     * @param resourceId resourceId
     */
    LoaderBuilder error(@DrawableRes int resourceId);

    /**
     * 内存缓存
     *
     * @param isOpen 是否开启
     */
    LoaderBuilder memoryCache(boolean isOpen);

    /**
     * 文件缓存机制
     *
     * @param strategy 缓存机制
     */
    LoaderBuilder diskCacheStrategy(DiskCacheType strategy);

    /**
     * 设定图片大小
     *
     * @param size 正方形
     */
    LoaderBuilder resize(int size);

    /**
     * 设定图片大小
     *
     * @param width  宽
     * @param height 高
     */
    LoaderBuilder resize(int width, int height);

    /**
     * 转为圆形
     */
    LoaderBuilder circle();

    /**
     * centerCrop
     */
    LoaderBuilder centerCrop();

    /**
     * 设置圆角
     *
     * @param radius px
     */
    LoaderBuilder roundCorners(int radius);

    /**
     * 设置四个圆角
     *
     * @param topLeft float
     * @param topRight float
     * @param bottomLeft float
     * @param bottomRight float
     */
    LoaderBuilder granularRoundedCorners(float topLeft, float topRight, float bottomRight, float bottomLeft);

    /**
     * 高斯模糊
     *
     * @param blurRadius 度数
     */
    LoaderBuilder blur(Context context, @FloatRange(from = 1, to = 25) float blurRadius);



    /**
     * 监听
     *
     * @param callback
     * @param <Resource>
     * @return
     */
    <Resource> LoaderBuilder listener(OnLoadListener<Resource> callback);

    /**
     * 加载到控件
     *
     * @param imageView 控件
     */
    void into(ImageView imageView);

    /**
     * 加载回调
     *
     * @param callback callback
     */
    <Resource> void into(OnLoadListener<Resource> callback);

    /**
     * 保存图片
     */
    Drawable save() throws ExecutionException, InterruptedException;

    /**
     * 加载时接口回调
     */
    interface OnLoadListener<T> {
        /**
         * 成功
         *
         * @param drawable drawable
         */
        void success(T drawable);

        /**
         * 失败
         */
        void failed();
    }
}
