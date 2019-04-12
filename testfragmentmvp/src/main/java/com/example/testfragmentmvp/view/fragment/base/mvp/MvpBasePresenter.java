package com.example.testfragmentmvp.view.fragment.base.mvp;

import com.example.testfragmentmvp.view.fragment.base.base.BasePresenter;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;

    public V getView() {
        return view;
    }

    @Override
    public void attachView(V view) {
        this.view=view;
    }

    @Override
    public void detachView() {
        this.view=null;
    }
}
