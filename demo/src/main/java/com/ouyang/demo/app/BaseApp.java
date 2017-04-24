package com.ouyang.demo.app;

import android.app.Application;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.ouyang.demo.app.image.BoxingFrescoLoader;
import com.ouyang.demo.app.image.BoxingUCrop;
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
        IBoxingMediaLoader loader = new BoxingFrescoLoader(this);
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUCrop());
    }
}
