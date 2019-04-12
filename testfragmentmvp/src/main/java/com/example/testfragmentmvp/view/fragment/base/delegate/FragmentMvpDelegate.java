package com.example.testfragmentmvp.view.fragment.base.delegate;

import android.os.Bundle;
import android.view.View;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public interface FragmentMvpDelegate<V extends MvpView ,P extends MvpPresenter<V>> {
    void onCreate(Bundle savedInstanceState);

    void onActivityCreated(Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onStop();

    void onDestroyView();

    void onDestroy();
}
