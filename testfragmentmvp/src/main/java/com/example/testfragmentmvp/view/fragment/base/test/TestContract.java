package com.example.testfragmentmvp.view.fragment.base.test;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class TestContract {
    interface  View extends MvpView{
       void showResult(int num);
    }
    interface Presenter extends MvpPresenter<View>{
        void addNum(int a,int b);
    }
}
