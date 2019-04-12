package com.example.testfragmentmvp.view.fragment.base.test3;

import android.util.Log;

import com.example.testfragmentmvp.R;
import com.example.testfragmentmvp.view.activity.MainActivity;
import com.example.testfragmentmvp.view.fragment.base.base.BaseFragment;

/**
 * Created by AndroidXJ on 2019/2/22.
 */

public class Test3Fragment extends BaseFragment<Test3Contract.View,Test3Contract.Presenter>implements Test3Contract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test3;
    }

    @Override
    protected void init() {

    }

    @Override
    public void sayNothing() {
        Log.i(MainActivity.TAG,"say nothing!!!");
    }

    @Override
    public Test3Contract.View createView() {
        return this;
    }

    @Override
    public Test3Contract.Presenter createPresenter() {
        return new Test3Presenter();
    }
}
