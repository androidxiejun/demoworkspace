package com.example.testdatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testdatabinding.databinding.ActivityMvvmBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMvvmBinding binding;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        initView();
        initData();
    }

    private void initView() {
        mBtn = findViewById(R.id.btn_change);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = new UserBean("李四", 20);
                binding.setUser(userBean);
            }
        });
    }

    private void initData() {
        UserBean userBean = new UserBean("张三", 19);
        binding.setUser(userBean);
    }
}
