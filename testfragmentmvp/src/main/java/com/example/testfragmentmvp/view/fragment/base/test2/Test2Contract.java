package com.example.testfragmentmvp.view.fragment.base.test2;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/20.
 */

public class Test2Contract {

    interface  View extends MvpView{
        void saveData(String text);
    }

    interface Presenter extends MvpPresenter<View>{
        String getData();
    }
}
