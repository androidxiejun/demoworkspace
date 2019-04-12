package com.example.testfragmentmvp.view.fragment.base.base;

import android.view.View;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpFragment;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public abstract class BaseFragment<V extends MvpView,P extends MvpPresenter<V>>extends MvpFragment<V,P> {

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
