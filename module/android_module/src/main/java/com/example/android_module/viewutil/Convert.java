package com.example.android_module.viewutil;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * author : Cong
 * date   : 2020-09-22
 */
public final class Convert {
    private Convert() {
    }

    private static float size(int unit, Context context, float size) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }

        return TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
    }

    public static float dp(Context context, float size) {
        return size(1, context, size);
    }

    public static float sp(Context context, float size) {
        return size(2, context, size);
    }
}
