package com.example.testmodule.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testmodule.R;
import com.example.testmodule.presenter.BasePresenter;

/**
 * Created by AndroidXJ on 2019/5/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected BasePresenter mPresenter;
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mToast = new Toast(getApplicationContext());
        mPresenter = bindPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        initView();
        init();
    }

    @Override
    public void showToast(String message) {
        View view = getLayoutInflater().inflate(R.layout.layout_toast, null);
        TextView textView = view.findViewById(R.id.tv_toast);
        textView.setText(message);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        mToast.show();
    }

    protected abstract void init();

    protected abstract void initView();

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract <P extends BasePresenter> BasePresenter bindPresenter();


}
