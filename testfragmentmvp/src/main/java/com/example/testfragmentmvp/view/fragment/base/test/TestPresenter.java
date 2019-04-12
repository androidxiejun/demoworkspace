package com.example.testfragmentmvp.view.fragment.base.test;

import com.example.testfragmentmvp.view.fragment.base.base.BasePresenter;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class TestPresenter extends BasePresenter<TestContract.View>implements TestContract.Presenter {
    @Override
    public void addNum(int a, int b) {
        int result=a+b;
        getView().showResult(result);
    }
}
