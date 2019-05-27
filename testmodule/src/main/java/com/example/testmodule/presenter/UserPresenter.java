package com.example.testmodule.presenter;

import com.example.testmodule.view.UserActivity;

/**
 * Created by AndroidXJ on 2019/5/27.
 */

public class UserPresenter extends BasePresenter<UserActivity> {

    private UserActivity mView;

//    public UserPresenter() {
//        if (mView == null) {
//            mView = getView();
//        }
//    }

    public void changeTitle() {
        if (mView == null) {
            mView = getView();
        }
        mView.showMessage("开始改变!");
        mView.refreshName("改变了！");
    }
}
