package com.example.testfragmentmvp.view.fragment.base.base;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpBasePresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> implements IBasePresenter {
    @Override
    public void setFragmentNamr(String name) {

    }

    @Override
    public void getFragmentName() {

    }
}
