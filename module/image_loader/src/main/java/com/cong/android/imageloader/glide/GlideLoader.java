package com.cong.android.imageloader.glide;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.cong.android.imageloader.core.ImageLoader;

/**
 * author : Cong
 * date   : 2020-10-09
 */
public class GlideLoader extends ImageLoader {

    @Override
    public GlideBuilder with(@NonNull Context context) {
        if (Util.isOnMainThread() && !(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return with((FragmentActivity) context);
            } else if (context instanceof Activity) {
                return with((Activity) context);
            } else if (context instanceof ContextWrapper) {
                return with(((ContextWrapper) context).getBaseContext());
            } else {
                return empty();
            }
        } else {
            return empty();
        }

    }

    @Override
    public GlideBuilder with(@NonNull Activity activity) {
        if (activity.isDestroyed()) {
            return empty();
        } else {
            return new GlideBuilder(Glide.with(activity));
        }
    }

    @Override
    public GlideBuilder with(@NonNull FragmentActivity fragmentActivity) {
        if (fragmentActivity.isDestroyed()) {
            return empty();
        } else {
            return new GlideBuilder(Glide.with(fragmentActivity));
        }
    }

    @Override
    public GlideBuilder with(@NonNull Fragment fragmentSupport) {
        if (fragmentSupport.isDetached() || fragmentSupport.isRemoving()) {
            return empty();
        } else {
            return new GlideBuilder(Glide.with(fragmentSupport));
        }
    }

    @Override
    public GlideBuilder with(@NonNull android.app.Fragment fragment) {
        if (fragment.isDetached() || fragment.isRemoving()) {
            return empty();
        } else {
            return new GlideBuilder(Glide.with(fragment));
        }
    }

    private GlideBuilder empty(){
        return new GlideBuilder();
    }
}
