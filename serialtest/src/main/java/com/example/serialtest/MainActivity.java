package com.example.serialtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.serialportlibrary.service.impl.SerialPortBuilder;
import com.serialportlibrary.service.impl.SerialPortService;
import com.serialportlibrary.util.ByteStringUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_hello;

    private EditText mPortEdit;
    private EditText mBaudrateEdit;
    private EditText mDataReceiveEdit;
    private EditText mDataSendEdit;

    private Button mSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
          //获取所有串口名字
//        String[] devices = new SerialPortFinder().getDevices();
//        //获取所用串口地址
//        String[] devicesPath = new SerialPortFinder().getDevicesPaths();
//
//
//        for (String path : devicesPath) {
//            Log.e("MainActivity：", path);
//        }
//        for (String device : devices) {
//            Log.e("MainActivity：", device);
//        }
    }

    private void initView() {
        tv_hello = findViewById(R.id.tv_hello);
        mPortEdit = findViewById(R.id.edit_port);
        mBaudrateEdit = findViewById(R.id.edit_baudrate);
        mDataSendEdit = findViewById(R.id.edit_send_data);
        mDataReceiveEdit = findViewById(R.id.edit_receive_data);
        mSendBtn = findViewById(R.id.btn_send);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDataReceiveEdit.setText("");
        String port = mPortEdit.getText().toString();
        int baudrate = Integer.valueOf(mBaudrateEdit.getText().toString());
        String sendData = mDataSendEdit.getText().toString();
        SerialPortService serialPortService = new SerialPortBuilder()
                .setTimeOut(100L)
                .setBaudrate(baudrate)
                .setDevicePath(port)
                .createService();
        serialPortService.isOutputLog(true);
        byte[] receiveData = serialPortService.sendData(sendData);
        if (receiveData != null) {
            String receiveStr = ByteStringUtil.byteArrayToHexStr(receiveData);
            Log.e("MainActivity：", receiveStr);
            mDataReceiveEdit.setText(receiveStr);
        }
    }
}
