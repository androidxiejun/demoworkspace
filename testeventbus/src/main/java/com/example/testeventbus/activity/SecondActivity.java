package com.example.testeventbus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.testeventbus.R;
import com.example.testeventbus.eventbean.FirstEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by AndroidXJ on 2019/4/18.
 */

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnEvent;
    private Button mBtnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
    }

    private void initView() {
        mBtnEvent =findViewById(R.id.btn_event);
        mBtnBack=findViewById(R.id.btn_back);
        mBtnEvent.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_event:
                EventBus.getDefault().post(new FirstEvent("初次尝试"));
                break;
            case R.id.btn_back:
                setResult(MainActivity.RESULT_CODE);
                finish();
                break;
        }
    }
}
