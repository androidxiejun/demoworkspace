package com.example.testeventbus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testeventbus.R;
import com.example.testeventbus.eventbean.FirstEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG=MainActivity.class.getSimpleName();
    private Button mBtn;
    private TextView mTxt;
    public static final int REQUEST_CODE=0;
    public static final int RESULT_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mBtn=findViewById(R.id.btn_turn);
        mTxt=findViewById(R.id.txt_content);
        mBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),SecondActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Subscribe()
    public void onEventMainThread(FirstEvent event) {
        Log.i(TAG,"线程-----"+Thread.currentThread().getName());
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        mTxt.setText(msg);
    }
    
    @Subscribe()
    public void onEvent(FirstEvent event){
        Log.i(TAG,"线程---2--"+Thread.currentThread().getName());
        String msg = "onEventMainThread收到了消息2：" + event.getMsg();
        mTxt.setText(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CODE){
            Log.i(TAG,"都回来了-------------");
        }
    }
}
