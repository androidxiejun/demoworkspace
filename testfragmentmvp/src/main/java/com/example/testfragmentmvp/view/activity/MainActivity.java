package com.example.testfragmentmvp.view.activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.testfragmentmvp.R;
import com.example.testfragmentmvp.view.activity.mvp.MvpActivity;
import com.example.testfragmentmvp.view.fragment.base.base.BaseFragment;
import com.example.testfragmentmvp.view.fragment.base.test.TestFragment;
import com.example.testfragmentmvp.view.fragment.base.test2.Test2Fragment;
import com.example.testfragmentmvp.view.fragment.base.test3.Test3Fragment;

public class MainActivity extends MvpActivity<MainContract.View, MainPresenter> implements MainContract.View {
    private TextView mTxt;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public MainContract.View createView() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        getPresenter().addNum(1, 2);
        fragmentChange(new TestFragment());
    }

    @Override
    protected void initView() {
//        mTxt = (TextView) findViewById(R.id.show_result);
    }

    @Override
    public void showResult(int result) {
//        mTxt.setText(result + "");
    }

    public void fragmentChange(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
