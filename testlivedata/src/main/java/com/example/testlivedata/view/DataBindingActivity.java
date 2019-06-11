package com.example.testlivedata.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testlivedata.R;
import com.example.testlivedata.viewmodule.UserViewModel;

/**
 * Created by AndroidXJ on 2019/6/3.
 */

public class DataBindingActivity extends AppCompatActivity {
    private UserViewModel mModel;
    private TextView mTxt;
    private Button mChangeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTxt.setText(s);
            }
        };

        mModel.getName().observe(this, nameObserver);
    }

    private void initView() {
        mTxt = findViewById(R.id.txt);
        mChangeBtn = findViewById(R.id.btn_change);
        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name="张三";
                int age =20;
                mModel.getName().setValue(name);
            }
        });
    }
}
