package com.cong.android.imageloader.core;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.cong.android.imageloader.glide.GlideLoader;

/**
 * author : Cong
 * date   : 2020-10-09
 */
public abstract class ImageLoader {

    public static ImageLoader getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 生命周期绑定
     *
     * @param context context
     * @param <T>     构造器
     */
    public abstract <T extends LoaderBuilder> T with(@NonNull Context context);

    /**
     * 生命周期绑定
     *
     * @param activity activity
     * @param <T>      构造器
     */
    public abstract <T extends LoaderBuilder> T with(@NonNull Activity activity);

    /**
     * 生命周期绑定
     *
     * @param fragmentActivity fragmentActivity
     * @param <T>              构造器
     */
    public abstract <T extends LoaderBuilder> T with(@NonNull FragmentActivity fragmentActivity);

    /**
     * 生命周期绑定
     *
     * @param fragmentSupport fragmentSupport
     * @param <T>             构造器
     */
    public abstract <T extends LoaderBuilder> T with(@NonNull Fragment fragmentSupport);

    /**
     * 生命周期绑定
     *
     * @param fragment fragment
     * @param <T>      构造器
     */
    public abstract <T extends LoaderBuilder> T with(@NonNull android.app.Fragment fragment);

    private static class SingleHolder {
        private static final ImageLoader INSTANCE = new GlideLoader();
    }

}
