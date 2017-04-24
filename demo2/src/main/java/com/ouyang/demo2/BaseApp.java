package com.ouyang.demo2;

import android.app.Application;
import android.os.Build;

import com.ouyang.demo2.util.LogUtil;

/**
 * Created by OuYang on 2016/7/5.
 * custom application
 */
public class BaseApp extends Application {
    private static BaseApp instance;

    public static synchronized BaseApp getInstance() {
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
        LogUtil.init(BuildConfig.DEBUG, "OuYang");
    }
}
