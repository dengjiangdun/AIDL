package com.example.djd.myapplication;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by djd on 18-7-2.
 */

public class MyApplication extends Application {

    private static final boolean isDebug = true;
    @Override
    public void onCreate() {
        super.onCreate();

        if (isDebug) {
            ARouter.openDebug();
            ARouter.openLog();
        }

        ARouter.init(MyApplication.this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
