package com.example.testfragmentmvp.view.fragment.base.callback;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public interface MvpCallback<V extends MvpView, P extends MvpPresenter<V>> {
    /**
     * 创建P层
     * @return
     */
    P createPresenter();

    /**
     * 创建V层
     * @return
     */
    V createView();

    /**
     * 获取P层
     * @return
     */
    P getPresenter();

    /**
     * 获取V层
     * @return
     */
    V getMvpView();

    /**
     * 设置V层
     * @param view
     */
    void setMvpView(V view);

    /**
     * 设置P层
     * @param presenter
     */
    void setPresenter(P presenter);
}
