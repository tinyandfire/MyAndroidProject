package com.example.myview;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.cong.android.imageloader.core.ImageLoader;
import com.example.android_module.viewutil.Convert;
import com.example.myview.utils.UIUtils;

import java.util.HashSet;

public class UiToast {
    private HashSet<String> mIgnoreKeySet = new HashSet<>();

    private UiToast() {
    }

    public static UiToast getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void addToastIgnoreKey(String ...key) {
        if (key != null) {
            for (String s : key) {
                mIgnoreKeySet.add(s);
            }
        }
    }

    public static void displayBottomToast(Context context, int resourceId) {
        displayBottomToast(context, context.getString(resourceId));
    }

    public static void displayBottomToast(Context context, String text) {
        if (context == null || TextUtils.isEmpty(text)) return;
        UiToast.getInstance().showBottomToast(context, text, true);
    }

    public static void displayBottomToastAnyKey(Context context, String text) {
        if (context == null || TextUtils.isEmpty(text)) return;
        UiToast.getInstance().showBottomToast(context, text, false);
    }

    public static void displayBottomToastWithIcon(Context context, String iconUrl, String text) {
        if (context == null || TextUtils.isEmpty(text)) return;
        UiToast.getInstance().showBottomToastWithIcon(context, iconUrl, -1, text);
    }

    public static void displayBottomToastWithIcon(Context context, @DrawableRes int icon, String text) {
        if (context == null || TextUtils.isEmpty(text)) return;
        UiToast.getInstance().showBottomToastWithIcon(context,null, icon, text);
    }

    private void showBottomToast(Context context, String msg, boolean checkKey) {
        if (checkKey && checkIgnoreKey(msg)) return;
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(renderingToast(context, null, -1, msg));
        toast.show();
    }

    private void showBottomToastWithIcon(Context context, String iconUrl, @DrawableRes int icon, String msg) {
        if (checkIgnoreKey(msg)) return;
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(renderingToast(context, iconUrl, icon, msg));
        toast.show();
    }

    public View renderingToast(Context context, @Nullable String iconUrl, @DrawableRes int icon, @NonNull String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_bottom, null);

        AppCompatImageView imageView = view.findViewById(R.id.toast_icon);
        if (icon != -1) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(icon);
        } else if (!TextUtils.isEmpty(iconUrl)) {
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().with(context)
                    .load(iconUrl)
                    .centerCrop()
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        AppCompatTextView tvToastMessage = view.findViewById(R.id.toast_message);
        tvToastMessage.setText(msg);

        ViewGroup.LayoutParams layoutParams = ((ViewGroup) view).getChildAt(0).getLayoutParams();
        layoutParams.width = UIUtils.getScreenWidth(context) - (int) (Convert.dp(context, 8f)) * 2;
        ((ViewGroup) view).getChildAt(0).setLayoutParams(layoutParams);

        return view;
    }

    private boolean checkIgnoreKey(String s) {
        if (mIgnoreKeySet != null) {
           return mIgnoreKeySet.contains(s);
        }
        return false;
    }
    private static class SingleHolder {
        private static final UiToast INSTANCE = new UiToast();
    }

}
