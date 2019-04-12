package com.example.testdagger3;

import android.app.Application;

/**
 * Created by AndroidXJ on 2019/3/18.
 */

public class MyApplication extends Application {
    private AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject() {
        mAppComponent= DaggerAppComponent.create();
        ComponentHolder.setAppComponent(mAppComponent);
    }
//    public AppComponent getAppComponent(){
//        return mAppComponent;
//    }
}
