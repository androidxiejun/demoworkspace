package com.example.testfragmentmvp.view.fragment.base.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testfragmentmvp.view.fragment.base.callback.MvpCallback;
import com.example.testfragmentmvp.view.fragment.base.delegate.FragmentMvpDelegateImpl;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends RxFragment implements MvpCallback<V, P> {
    private P presenter;
    private V view;
    /**
     * 持有目标对象引用
     **/
    private FragmentMvpDelegateImpl<V, P> delegate;

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void init();

    protected abstract void initView(View view);

    @Override
    public P createPresenter() {
        return this.presenter;
    }

    @Override
    public V createView() {
        return this.view;
    }

    @Override
    public P getPresenter() {
        return this.presenter;
    }

    @Override
    public V getMvpView() {
        return this.view;
    }

    @Override
    public void setMvpView(V view) {
        this.view = view;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    private FragmentMvpDelegateImpl<V, P> getMvpDelegate() {
        if (delegate == null) {
            this.delegate = new FragmentMvpDelegateImpl<V, P>(this);
        }
        return delegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View layoutView=inflater.inflate(getLayoutId(),container,false);
        initView(layoutView);
        return layoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }
}
