package com.example.app_debug;

import com.example.appbase.AppApplication;

/**
 * author : Cong
 * date   : 2021-07-20
 * time   : 17:33
 * whats the fst
 */
public class TextApplication extends AppApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean isProduction() {
        return true;
    }

    @Override
    public int getImSDKAppID() {
        return 0;
    }

    @Override
    public String getApiHost() {
        return null;
    }

    @Override
    public String getLogHost() {
        return null;
    }

    @Override
    public String getWxAppID() {
        return null;
    }

    @Override
    public String getCrashID() {
        return null;
    }
}
