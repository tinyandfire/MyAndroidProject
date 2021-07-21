package com.example.android_module.base;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_module.viewutil.ViewMeasure;

import org.greenrobot.eventbus.EventBus;

/**
 * author : Cong
 * date   : 2020-09-22
 */
public abstract class BaseActivity extends AppCompatActivity {
    private boolean isKeyboardShown = false;
    private int originalRectBottom = 0;
    private int lastRectBottom = 0;
    private int decorViewHeight = 0;
    private int navigationBarHeight = 0;
    private int softKeyboardHeight = 0;

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = () -> {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        if (originalRectBottom == 0) {
            originalRectBottom = rect.bottom;
            decorViewHeight = getWindow().getDecorView().getMeasuredHeight();
            navigationBarHeight = ViewMeasure.getSystemBarHeight(this, ViewMeasure.SystemBar.Navigation);
        } else if (originalRectBottom == rect.bottom) {
            isKeyboardShown = false;
            if (lastRectBottom != rect.bottom) {
                onSoftKeyboardHide();
            }
        } else {
            isKeyboardShown = true;
            softKeyboardHeight = decorViewHeight - navigationBarHeight - rect.bottom;
            if (lastRectBottom != rect.bottom) {
                onSoftKeyboardShow();
            }
        }
        lastRectBottom = rect.bottom;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        changeStatusBarStyle();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (calculationSoftKeyboardHeight()) {
            getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    @Override
    protected void onDestroy() {
        if (calculationSoftKeyboardHeight()) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    protected boolean calculationSoftKeyboardHeight() {
        return false;
    }

    protected boolean useEventBus() {
        return false;
    }

    protected boolean isKeyboardShown() {
        return isKeyboardShown;
    }

    protected void changeStatusBarStyle() {
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }


    protected void onSoftKeyboardShow() {

    }

    protected void onSoftKeyboardHide() {

    }
}
