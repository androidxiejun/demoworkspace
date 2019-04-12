package com.example.testfragmentmvp.view.activity.mvp;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.testfragmentmvp.view.activity.MainActivity;
import com.example.testfragmentmvp.view.activity.delegate.ActivityMvpDelagateImpl;
import com.example.testfragmentmvp.view.activity.delegate.ActivityMvpDelegate;
import com.example.testfragmentmvp.view.fragment.base.callback.MvpCallback;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpFragment;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public  abstract class MvpActivity<V extends MvpView,P extends MvpPresenter<V>> extends RxAppCompatActivity implements MvpCallback<V,P> {
   private ActivityMvpDelagateImpl<V,P> delegate;
   private V view;
   private P presenter;

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void init();

    protected abstract void initView();

   public ActivityMvpDelegate<V,P> getMvpDelegate(){
       if(null==delegate){
           delegate=new ActivityMvpDelagateImpl<>(this);
       }
       return delegate;
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG,"MvpActivity-----onCreate-------");
        getMvpDelegate().onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public P createPresenter() {
        return this.presenter;
    }

    @Override
    public V createView() {
        return this.view;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setMvpView(V view) {
        this.view=view;
    }

    @Override
    public P getPresenter() {
        return this.presenter;
    }

    @Override
    public V getMvpView() {
        return this.view;
    }
}
