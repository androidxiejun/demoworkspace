package com.example.testarouter.application;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by AndroidXJ on 2019/4/18.
 */

public class MyApplication extends Application{
    private static boolean isDebug=true;
    @Override
    public void onCreate() {
        super.onCreate();
        if(isDebug){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
