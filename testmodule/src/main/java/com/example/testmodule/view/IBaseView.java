package com.example.testmodule.view;

import android.content.Context;

/**
 * Created by AndroidXJ on 2019/5/27.
 */

public interface IBaseView {
    void refreshName(String message);

    void refreshAge(int age);

    void showToast(String message);

    Context getContext();
}
