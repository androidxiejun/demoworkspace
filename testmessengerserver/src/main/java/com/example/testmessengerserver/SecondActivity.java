package com.example.testmessengerserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by AndroidXJ on 2018/11/27.
 */

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private Messenger mService;
    private TextView mStateTxt;
    private Button mSendBtn;
    private LinearLayout mLinearLayout;
    private int mA;
    private static final int MSG_SUM = 0x110;

    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgFromServer) {

        }
    });

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        bindServer();
    }

    private void initView() {
        mStateTxt = findViewById(R.id.state_txt_second);
        mSendBtn = findViewById(R.id.send_btn_second);
        mLinearLayout = findViewById(R.id.lieanr_layout_second);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        addSum();
    }

    private void addSum() {
        try {
            int a = mA++;
            int b = (int) (Math.random() * 100);
            TextView mTv = new TextView(this);
            mTv.setText(a + "+" + b + "=" + "caculating...");
            mLinearLayout.addView(mTv);
            Message message = Message.obtain(null, MSG_SUM, a, b);
            message.replyTo = mMessenger;
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private void bindServer(){
        Intent intent=new Intent();
        intent.setAction("com.zhy.aidl.calc");
        intent.setPackage("com.example.testmessengerclient");
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }
}
