package com.example.testfragmentmvp.view.fragment.base.callback;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class MvpCallbackProxy<V extends MvpView, P extends MvpPresenter<V>> implements MvpCallback<V, P> {
    private MvpCallback<V, P> mCallback;

    public MvpCallbackProxy(MvpCallback<V, P> callback) {
        this.mCallback = callback;
    }

    @Override
    public P createPresenter() {
        P presenter = this.mCallback.getPresenter();
        if (null == presenter) {
            presenter = this.mCallback.createPresenter();
        }
        if (null == presenter) {
            throw new NullPointerException("presenter is null");
        }
        this.mCallback.setPresenter(presenter);
        return presenter;
    }

    @Override
    public V createView() {
        V view = this.mCallback.getMvpView();
        if (null == view) {
            view = this.mCallback.createView();
        }
        if (null == view) {
            throw new NullPointerException("view is null");
        }
        this.mCallback.setMvpView(view);
        return view;
    }

    @Override
    public P getPresenter() {
        P presenter = this.mCallback.getPresenter();
        if (null == presenter) {
            throw new NullPointerException("presenter is null");
        }
        return presenter;
    }

    @Override
    public V getMvpView() {
        V view = this.mCallback.getMvpView();
        if (null == view) {
            throw new NullPointerException("view is null");
        }
        return view;
    }

    @Override
    public void setMvpView(V view) {
        this.mCallback.setMvpView(view);
    }

    @Override
    public void setPresenter(P presenter) {
        this.mCallback.setPresenter(presenter);
    }

    public void attachView() {
        getPresenter().attachView(getMvpView());
    }

    public void detachView() {
        getPresenter().detachView();
    }
}
