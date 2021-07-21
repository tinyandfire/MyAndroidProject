package com.example.myview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.android_module.viewutil.Convert;
import com.example.base2.ApplicationConfig;
import com.example.myview.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

/**
 * author : Cong
 * date   : 2021-07-20
 * time   : 16:55
 * whats the fst
 */
public class UIUtils {

    private static int screenWidth = -1;
    private static int screenHeight = -1;


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        final Resources resources = ApplicationConfig.getInstance().getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取底部导航栏高度
     */
    public static int getNavigationBarHeight() {
        final Resources resources = ApplicationConfig.getInstance().getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取底部导航栏高度。
     */
    public static int getNavigationBarHeight(Context context) {
        if (!checkDeviceHasNavigationBar(context)) {
            return 0;
        }

        final Resources resources = context.getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 判断设备是否有导航栏。目前没找到更准确的方法来检测系统是否有导航栏；如果遇到特殊机型，可能需要特殊处理
     */
    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }


    /**
     * @param context 这个context应该传递Activity类型的context，如果是其他类型的context可能会导致获取的结果不对
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight > 0) {
            return screenHeight;
        }
        if (context == null) {
            return 0;
        }
        initScreenSize(context);
        return screenHeight > 0 ? screenHeight : 0;
    }

    private static void initScreenSize(Context context) {
        if (context == null) {
            return;
        }
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (wm != null) {
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                if (display == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    display.getRealSize(size);
                } else {
                    display.getSize(size);
                }
                screenHeight = size.y;
                screenWidth = size.x;
            } else {
                final DisplayMetrics dm = context.getResources().getDisplayMetrics();
                screenHeight = dm.heightPixels;
                screenWidth = dm.widthPixels;
            }
        } catch (Exception e) {

        }
    }

    public static int getScreenWidth() {
        return getScreenWidth(ApplicationConfig.getInstance());
    }

    public static int getScreenHeight() {
        return getScreenHeight(ApplicationConfig.getInstance());
    }


    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if (screenWidth > 0) {
            return screenWidth;
        }
        if (context == null) {
            return 0;
        }
        initScreenSize(context);
        return screenWidth > 0 ? screenWidth : 0;
    }


    public static void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(width, height);
        }
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);

    }


    public static void setViewMarginLeft(View view, int left) {
        if (view.getLayoutParams() == null || !(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp == null) {
            return;
        }
        lp.leftMargin = left;

        view.setLayoutParams(lp);

    }

    public static float dip2Px(Context context, float dipValue) {
        return Convert.dp(context, dipValue);
    }

    public static int dip2Px(float dipValue) {
        return (int) Convert.dp(ApplicationConfig.getInstance(), dipValue);
    }

    public static int[] getViewLocationOnScreen(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        return location;
    }

    public static void setTextDrawableLeft(TextView textView, int resId) {

        textView.setCompoundDrawablesWithIntrinsicBounds(ApplicationConfig.getInstance().getResources().getDrawable(resId), null, null, null);

    }

    public static void setTextDrawableRight(TextView textView, int resId) {

        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, ApplicationConfig.getInstance().getResources().getDrawable(resId), null);

    }

    public static void setTextDrawableNull(TextView textView) {

        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

    }

//    public static byte[] getAppIcon() {
//        Bitmap bmp = BitmapFactory.decodeResource(ApplicationConfig.getInstance().getResources(), R.mipmap.ic_launcher);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();
//    }


    /**
     * 获取虚拟按键的高度
     * 1. 全面屏下
     * 1.1 开启全面屏开关-返回0
     * 1.2 关闭全面屏开关-执行非全面屏下处理方式
     * 2. 非全面屏下
     * 2.1 没有虚拟键-返回0
     * 2.1 虚拟键隐藏-返回0
     * 2.2 虚拟键存在且未隐藏-返回虚拟键实际高度
     */
    public static int getNavigationBarHeightIfRoom(Context context) {
        if (navigationGestureEnabled(context)) {
            return 0;
        }
        return getCurrentNavigationBarHeight(((Activity) context));
    }

    /**
     * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
     *
     * @param context
     * @return
     */
    public static boolean navigationGestureEnabled(Context context) {
        int val = Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0);
        return val != 0;
    }

    /**
     * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
     *
     * @return
     */
    public static String getDeviceInfo() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min";

        if (brand.equalsIgnoreCase("HUAWEI")) {
            return "navigationbar_is_min";
        } else if (brand.equalsIgnoreCase("XIAOMI")) {
            return "force_fsg_nav_bar";
        } else if (brand.equalsIgnoreCase("VIVO")) {
            return "navigation_gesture_on";
        } else if (brand.equalsIgnoreCase("OPPO")) {
            return "navigation_gesture_on";
        } else {
            return "navigationbar_is_min";
        }
    }

    /**
     * 非全面屏下 虚拟键实际高度(隐藏后高度为0)
     *
     * @param activity
     * @return
     */
    public static int getCurrentNavigationBarHeight(Activity activity) {
        if (isNavigationBarShown(activity)) {
            return getNavigationBarHeight(activity);
        } else {
            return 0;
        }
    }

    /**
     * 非全面屏下 虚拟按键是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isNavigationBarShown(Activity activity) {
        //虚拟键的view,为空或者不可见时是隐藏状态
        View view = activity.findViewById(android.R.id.navigationBarBackground);
        if (view == null) {
            return false;
        }
        int visible = view.getVisibility();
        if (visible == View.GONE || visible == View.INVISIBLE) {
            return false;
        } else {
            return true;
        }
    }
}
