package com.example.testfragmentmvp.view.activity;

import android.util.Log;

import com.example.testfragmentmvp.view.activity.MainContract.Presenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpBasePresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class MainPresenter extends MvpBasePresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void addNum(int a, int b) {
        getView().showResult(a+b);
    }
}
