package com.ouyang.databinding;

import android.app.Application;


/**
 * Created by OuYang on 2016/1/4.
 */
public class BaseApp extends Application {

    private static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
    }

    public static synchronized BaseApp getInstance() {
        return instance;
    }
}
