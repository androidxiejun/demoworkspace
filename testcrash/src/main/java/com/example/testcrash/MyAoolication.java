package com.example.testcrash;

import android.app.Application;

/**
 * Created by AndroidXJ on 2019/1/2.
 */

public class MyAoolication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
    }
}
