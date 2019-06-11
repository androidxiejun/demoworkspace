package com.example.testlambda;

import android.app.Application;

import com.example.myapp.MyEventBusIndex;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by AndroidXJ on 2019/5/31.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}
