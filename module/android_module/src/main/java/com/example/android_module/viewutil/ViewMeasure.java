package com.example.android_module.viewutil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.annotation.Size;

/**
 * author : Cong
 * date   : 2020-09-22
 */
public final class ViewMeasure {
    private ViewMeasure() {
    }

    public static void getScreenSize(WindowManager manager, @Size(2L) int[] screen) {
        Point point = new Point();
        manager.getDefaultDisplay().getRealSize(point);
        screen[0] = point.x;
        screen[1] = point.y;
    }

    public static DisplayMetrics getWindowsMetrics(WindowManager manager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getSystemBarHeight(Context context, ViewMeasure.SystemBar systemBar) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(systemBar.name, "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }

        return result;
    }

    public static enum SystemBar {
        Status("status_bar_height"),
        Navigation("navigation_bar_height");

        String name;

        private SystemBar(String name) {
            this.name = name;
        }
    }
}
