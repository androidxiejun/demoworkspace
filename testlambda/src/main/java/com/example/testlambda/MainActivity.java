package com.example.testlambda;

import android.content.Intent;
import android.os.Build;
import android.os.Trace;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtn1;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        intiView();
    }

    private void intiView() {
        mBtn1 = findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mCheckBox = findViewById(R.id.check_box);
        mCheckBox.setOnCheckedChangeListener((view, isChecked) -> {
            Log.i(TAG, "view---" + view.getId());
            Log.i(TAG, "isChecked---" + isChecked);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
//        Trace.beginSection("aaaa");
//        try {
//            switch (v.getId()) {
//                case R.id.btn1:
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.i(TAG, "1-----------------");
//                        }
//                    }).start();
//
//                    new Thread(() -> doMatch()).start();
//                    break;
//            }
//        } catch (Exception e) {
//            Log.i(TAG, "exception----" + e);
//        } finally {
//            Trace.endSection();
//        }

    }

    private void doMatch() {
        Log.i(TAG, "2-----------------");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceives(MessageEvent messageEvent) {
        Log.i(TAG, "name---" + messageEvent.name);
        Log.i(TAG, "age---" + messageEvent.age);
    }
}
