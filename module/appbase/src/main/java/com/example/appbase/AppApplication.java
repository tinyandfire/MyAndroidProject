package com.example.appbase;

import com.example.base2.ApplicationConfig;
import com.facebook.stetho.Stetho;

import java.util.logging.Logger;

import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.HttpException;

/**
 * author : Cong
 * date   : 2021-07-20
 * time   : 16:33
 * whats the fst
 */
public abstract class AppApplication extends ApplicationConfig {


    @Override
    public void onCreate() {
        super.onCreate();
//        ImCore.getInstance().initIm(this);
//        AppManager.getInstance().initializeApp(this);
        Stetho.initializeWithDefaults(this);
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof OnErrorNotImplementedException && throwable.getCause() != null) {
                    checkHttpExceptionUnauthorized(throwable.getCause());
                } else {
                    checkHttpExceptionUnauthorized(throwable);
                }
//                Logger.e("RxJava catch global exception", throwable.getMessage());
                throwable.printStackTrace();
            }
        });

//        /**
//         * 初始化过滤 Toast 关键字
//         */
//        UiToast.getInstance().addToastIgnoreKey("999998","请求过于频繁");
    }

    private void checkHttpExceptionUnauthorized(Throwable throwable) {
        if (throwable instanceof HttpException && ((HttpException) throwable).code() == 401) {
//            CoreManager.getInstance().redirectLogin();
        }
    }

}
