package com.example.testipc;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

/**
 * Created by AndroidXJ on 2018/11/22.
 */

public class MyApplication extends Application {
    public static final String TAG="MyAplication";
    @Override
    public void onCreate() {
        super.onCreate();
        String name=IpcUtil.getAppName(getApplicationContext());
        Log.i(TAG,"procese------"+name);
    }


}
