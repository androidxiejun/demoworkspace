package com.example.testlifecycle;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by AndroidXJ on 2019/6/18.
 */

public class MyObserver implements DefaultLifecycleObserver {
    private static final String TAG=MyObserver.class.getSimpleName();
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.d(TAG,"onCreate--------");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.d(TAG,"onStart--------");
        Log.d(TAG,"状态--------"+owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.d(TAG,"onResume--------");
        Log.d(TAG,"状态--------"+owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.d(TAG,"onPause--------");
        Log.d(TAG,"状态--------"+owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.d(TAG,"onStop--------");
        Log.d(TAG,"状态--------"+owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.d(TAG,"onDestroy--------");
        Log.d(TAG,"状态--------"+owner.getLifecycle().getCurrentState().name());
    }
}
