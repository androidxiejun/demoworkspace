package com.example.testlambda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by AndroidXJ on 2019/5/31.
 */

public class SecondActivity extends AppCompatActivity {
    private Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
    }

    private void initView() {
        mBtn = findViewById(R.id.senf_event);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainActivity.TAG, "1----------");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new MessageEvent("xiejun", 18));
                    }
                }).start();
            }
        });
    }
}
