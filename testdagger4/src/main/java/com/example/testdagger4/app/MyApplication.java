package com.example.testdagger4.app;

import android.app.Application;

import com.example.testdagger4.component.AppComponent;
import com.example.testdagger4.component.DaggerAppComponent;

/**
 * Created by AndroidXJ on 2019/3/20.
 */

public class MyApplication extends Application {
    private AppComponent mAppComponent;
    private static MyApplication mMyApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication=this;
        mAppComponent= DaggerAppComponent.builder().build();
    }
    public static MyApplication getInstance(){
       return mMyApplication;
    }
    public AppComponent getAppComponent(){
        return mAppComponent;
    }

}
