package com.example.testfragmentmvp.view.fragment.base.test2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testfragmentmvp.R;
import com.example.testfragmentmvp.view.fragment.base.base.BaseFragment;

/**
 * Created by AndroidXJ on 2019/2/20.
 */

public class Test2Fragment extends BaseFragment<Test2Contract.View,Test2Contract.Presenter>implements Test2Contract.View {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void saveData(String text) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test2;
    }

    @Override
    protected void init() {

    }

    @Override
    public Test2Contract.Presenter createPresenter() {
        return new Test2Presenter();
    }

    @Override
    public Test2Contract.View createView() {
        return this;
    }
}
