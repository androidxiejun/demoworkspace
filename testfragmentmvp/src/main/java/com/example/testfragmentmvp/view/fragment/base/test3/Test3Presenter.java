package com.example.testfragmentmvp.view.fragment.base.test3;

import android.util.Log;

import com.example.testfragmentmvp.view.activity.MainActivity;
import com.example.testfragmentmvp.view.fragment.base.base.BasePresenter;

/**
 * Created by AndroidXJ on 2019/2/22.
 */

public class Test3Presenter extends BasePresenter<Test3Contract.View>implements Test3Contract.Presenter {

    @Override
    public void saySomeThing(String message) {
        Log.i(MainActivity.TAG,"say love!!!");
    }
}
