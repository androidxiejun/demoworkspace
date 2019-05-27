package com.example.testmodule.presenter;

import android.util.Log;

import com.example.testmodule.view.IBaseView;
import com.example.testmodule.view.UserActivity;

/**
 * Created by AndroidXJ on 2019/5/27.
 */

public class BasePresenter<V extends IBaseView> {

    private V mView;

    public void attachView(V view) {
        this.mView = view;
    }

    public void detechView() {
        this.mView = null;
    }

    public V getView() {
        return mView;
    }
}
