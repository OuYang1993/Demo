package com.ouyang.test;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by OuYang on 2017/4/12.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
