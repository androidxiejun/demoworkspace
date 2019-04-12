package com.example.testfragmentmvp.view.activity.delegate;

import android.os.Bundle;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/18.
 */

public interface ActivityMvpDelegate <V extends MvpView,P extends MvpPresenter<V>>{
    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onRestart();

    void onStop();

    void onDestroy();
}
