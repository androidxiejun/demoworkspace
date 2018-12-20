package com.example.testmessengerserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int MSG_SUM = 0x110;
    private Button mBtn;
    private TextView mStateTxt;
    private LinearLayout mLinearLayout;
    private Messenger mService;
    private boolean isConn;
    private int mA;

    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgFromServer) {
            if (mStateTxt != null) {
                mStateTxt.setText(msgFromServer.arg2 + "");
            }
        }
    });

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            isConn = true;
            mStateTxt.setText("connected！");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConn = false;
            mStateTxt.setText("disconnected！");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindServiceInvoked();
    }

    private void initView() {
        mBtn = findViewById(R.id.send_btn);
        mStateTxt = findViewById(R.id.state_txt);
        mLinearLayout = findViewById(R.id.lieanr_layout);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            int a = mA++;
            int b = (int) (Math.random() * 100);

            //创建一个tv,添加到LinearLayout中
            TextView tv = new TextView(MainActivity.this);
            tv.setText(a + " + " + b + " = caculating ...");
            tv.setId(a);
            mLinearLayout.addView(tv);

            Message msgFromClient = Message.obtain(null, MSG_SUM, a, b);
            msgFromClient.replyTo = mMessenger;
            if (isConn) {
                //往服务端发送消息
                mService.send(msgFromClient);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindServiceInvoked() {
        Intent intent = new Intent();
        intent.setAction("com.zhy.aidl.calc");
        intent.setPackage("com.example.testmessengerclient");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.e(TAG, "bindService invoked !");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
