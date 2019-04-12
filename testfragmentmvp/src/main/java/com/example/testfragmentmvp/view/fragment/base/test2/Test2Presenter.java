package com.example.testfragmentmvp.view.fragment.base.test2;

import com.example.testfragmentmvp.view.fragment.base.base.BasePresenter;

/**
 * Created by AndroidXJ on 2019/2/20.
 */

public class Test2Presenter extends BasePresenter<Test2Contract.View>implements Test2Contract.Presenter {
    @Override
    public String getData() {
        return "you get it ！";
    }
}
