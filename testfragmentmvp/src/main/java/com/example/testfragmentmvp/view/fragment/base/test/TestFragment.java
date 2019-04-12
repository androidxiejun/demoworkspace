package com.example.testfragmentmvp.view.fragment.base.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testfragmentmvp.R;
import com.example.testfragmentmvp.view.fragment.base.base.BaseFragment;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public class TestFragment extends BaseFragment<TestContract.View,TestContract.Presenter>implements TestContract.View {
    @Override
    public void showResult(int num) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

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
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initView(View view) {
        super.initView(view);

    }

    @Override
    public TestContract.View createView() {
        return this;
    }

    @Override
    public TestContract.Presenter createPresenter() {
        return new TestPresenter();
    }
}
