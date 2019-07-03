package com.example.loglibrary;

import android.util.Log;

/**
 * Created by AndroidXJ on 2019/7/3.
 */

public class LogUtil {
    public static final String TAG = LogUtil.class.getSimpleName();

    public static void printLog(String log) {
        Log.d(TAG, log);
    }
}
