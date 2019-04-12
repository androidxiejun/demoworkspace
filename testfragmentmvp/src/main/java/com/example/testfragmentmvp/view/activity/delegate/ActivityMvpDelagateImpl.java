package com.example.testfragmentmvp.view.activity.delegate;

import android.os.Bundle;

import com.example.testfragmentmvp.view.fragment.base.callback.MvpCallback;
import com.example.testfragmentmvp.view.fragment.base.callback.MvpCallbackProxy;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class ActivityMvpDelagateImpl<V extends MvpView, P extends MvpPresenter<V>> implements ActivityMvpDelegate<V, P> {
    private MvpCallback<V, P> mCallback;
    private MvpCallbackProxy<V, P> mMvpCallbackProxy;

    public ActivityMvpDelagateImpl(MvpCallback<V, P> callback) {
        this.mCallback = callback;
    }

    private MvpCallbackProxy<V, P> getProxy() {
        if (mCallback != null) {
            this.mMvpCallbackProxy=new MvpCallbackProxy<V,P>(mCallback);
        }
        return this.mMvpCallbackProxy;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getProxy().createPresenter();
        getProxy().createView();
        getProxy().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        getProxy().detachView();
    }
}
