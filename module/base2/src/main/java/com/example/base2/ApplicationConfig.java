package com.example.base2;

import android.app.Application;

/**
 * author : Cong
 * date   : 2021-07-20
 * time   : 16:19
 * whats the fst
 */
public abstract class ApplicationConfig extends Application {

    private static ApplicationConfig application;

    public static ApplicationConfig getInstance() {
        return application;
    }


    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
    }

    public abstract boolean isProduction();

    public abstract int getImSDKAppID();

    public abstract String getApiHost();

    public abstract String getLogHost();

    public abstract String getWxAppID();

    public abstract String getCrashID();

}
