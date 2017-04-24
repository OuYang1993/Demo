package com.example.objectanimator;

import android.app.Application;


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
    }
}
