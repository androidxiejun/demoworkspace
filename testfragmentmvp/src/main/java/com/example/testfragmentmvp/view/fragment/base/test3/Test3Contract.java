package com.example.testfragmentmvp.view.fragment.base.test3;

import com.example.testfragmentmvp.view.fragment.base.mvp.MvpPresenter;
import com.example.testfragmentmvp.view.fragment.base.mvp.MvpView;

/**
 * Created by AndroidXJ on 2019/2/22.
 */

public class Test3Contract {
   interface View extends MvpView{
       void sayNothing();
   }
   interface Presenter extends MvpPresenter<View>{
       void saySomeThing(String message);
   }
}
