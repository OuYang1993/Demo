package com.ouyang.demo.app;

import android.app.Application;

import com.ouyang.demo.app.utils.LogUtil;

/**
 * Created by oylz on 2015/6/16.
 */
public class BaseApp extends Application {
    private static BaseApp instance;

    public static BaseApp getInstance() {
        if (instance == null)
            instance = new BaseApp();
        return instance;
    }

    public BaseApp() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(BuildConfig.DEBUG, "OUYANG DEMO");
    }
}
