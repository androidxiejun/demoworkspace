package com.example.testmodule.view;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testmodule.R;
import com.example.testmodule.presenter.BasePresenter;
import com.example.testmodule.presenter.UserPresenter;

/**
 * Created by AndroidXJ on 2019/5/27.
 */

public class UserActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG=UserActivity.class.getSimpleName();
    private Button mBtn;
    private TextView mTxt;
    private Context mContext;

    @Override
    public void refreshName(String message) {
        mTxt.setText(message);
    }

    @Override
    public void refreshAge(int age) {
        mTxt.setText(age + "");
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    protected void init() {
        mContext = this;
    }

    @Override
    protected void initView() {
        mTxt = findViewById(R.id.txt);
        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected BasePresenter<UserActivity> bindPresenter() {
        mPresenter = new UserPresenter();
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                getPresenter().changeTitle();
                break;
        }
    }

    /**
     * 显示弹出框
     *
     * @param message
     */
    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(message);
            }
        });
    }

    private UserPresenter getPresenter() {
        return (UserPresenter) mPresenter;
    }
}
