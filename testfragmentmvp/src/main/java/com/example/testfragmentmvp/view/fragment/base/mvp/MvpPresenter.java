package com.example.testfragmentmvp.view.fragment.base.mvp;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public interface MvpPresenter<V extends MvpView> {
    void attachView(V view);

    void detachView();
}
