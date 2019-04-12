package com.example.testfragmentmvp.view.activity;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class MainContract {
    interface View extends MvpView{
        void showResult(int result);
    }
    interface Presenter extends MvpPresenter<MainContract.View>{
        void  addNum(int a,int b);
    }
}
