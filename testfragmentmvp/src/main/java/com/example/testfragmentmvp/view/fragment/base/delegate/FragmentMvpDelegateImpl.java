package com.example.testfragmentmvp.view.fragment.base.delegate;

import android.os.Bundle;
import android.view.View;

import com.example.testfragmentmvp.view.fragment.base.callback.MvpCallback;
import com.example.testfragmentmvp.view.fragment.base.callback.MvpCallbackProxy;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>> implements FragmentMvpDelegate<V, P> {
    private MvpCallback<V, P> mCallback;
    private MvpCallbackProxy<V, P> mMvpCallbackProxy;

    public FragmentMvpDelegateImpl(MvpCallback<V, P> callback) {
        this.mCallback = callback;
    }

    private MvpCallbackProxy<V, P> getMvpCallbackProxy() {
        if (null != this.mCallback) {
            this.mMvpCallbackProxy = new MvpCallbackProxy<V, P>(mCallback);
        }
        return this.mMvpCallbackProxy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
         getMvpCallbackProxy().createPresenter();
         getMvpCallbackProxy().createView();
         getMvpCallbackProxy().attachView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
    public void onStop() {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroy() {
       getMvpCallbackProxy().detachView();
    }
}
