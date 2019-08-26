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

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView tv_hello;

    private EditText mPortEdit;
    private EditText mBaudrateEdit;
    private EditText mDataReceiveEdit;
    private EditText mDataSendEdit;

    private Button mBtnAdd;

    private SerialPortService serialPortService;

    private Button mSendBtn, mBtnOpenGate, mBtnCloseGate, mBtnInfrared, mBtnGateStatus, mBtnTurn, mBtnGunOpen, mBtnGunTYpe, mBtnClear, mBtnOPenLight, mBtnCloseLight;

    //开启1号继电器
    public static final byte[] cmd_OPEN = {(byte) 0x01, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0x8C, (byte) 0x3A};
    public static final String cmd_OPEN_STR = "01050000FF008C3A";
    //关闭1号继电器
    public static final byte[] cmd_CLOSE = {(byte) 0x01, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xCD, (byte) 0xCA};
    public static final String cmd_CLOSE_STR = "010500000000CDCA";
    //轮询红外状态
    public static final byte[] cmd_INFRARED = {(byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x79, (byte) 0xCC};
    public static final String cmd_INFRARED_STR = "01020000000879CC";
    //检测继电器状态
    public static final byte[] cmd_GATE_STATUS = {(byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x3D, (byte) 0xCC};
    public static final String cmd_GATE_STATUS_STR = "0101000000083DCC";
    //开启枪柜门
    public static final byte[] cmd_GUN_GATE_OPEN = {(byte) 0x55, (byte) 0xAA, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01};
    //检测枪柜门状态
    public static final byte[] cmd_GUN_GATE_TYPE = {(byte) 0x55, (byte) 0xAA, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x02};

    //开启补光灯
    public static final byte[] cmd_LIGHT_OPEN = {(byte) 0x55, (byte) 0xAA, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07};
    //关闭补光灯
    public static final byte[] cmd_LIGHT_CLOSE = {(byte) 0x55, (byte) 0xAA, (byte) 0x08, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x08};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSerial();
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
        mBtnOpenGate = findViewById(R.id.btn_open_gate);
        mBtnCloseGate = findViewById(R.id.btn_close_gate);
        mBtnInfrared = findViewById(R.id.btn_infrared);
        mBtnGateStatus = findViewById(R.id.btn_gate_status);
        mBtnOPenLight = findViewById(R.id.btn_open_light);
        mBtnCloseLight = findViewById(R.id.btn_close_light);
        mBtnAdd = findViewById(R.id.btn_add_task);

        mSendBtn.setOnClickListener(this);
        mBtnTurn = findViewById(R.id.btn_turn);
        mBtnOpenGate.setOnClickListener(this);
        mBtnCloseGate.setOnClickListener(this);
        mBtnInfrared.setOnClickListener(this);
        mBtnGateStatus.setOnClickListener(this);
        mBtnTurn.setOnClickListener(this);
        mBtnOPenLight.setOnClickListener(this);
        mBtnCloseLight.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);

        mBtnGunOpen = findViewById(R.id.gun_gate_open);
        mBtnGunTYpe = findViewById(R.id.gun_gate_type);
        mBtnGunOpen.setOnClickListener(this);
        mBtnGunTYpe.setOnClickListener(this);

        mBtnClear = findViewById(R.id.btn_clear);
        mBtnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_task:
//                new Thread(new MyTask()).start();
                break;
            case R.id.btn_open_gate:
//                byte[] byteOpen = serialPortService.sendData(cmd_OPEN);
////                byte[] byteOpen = serialPortService.sendData(cmd_OPEN_STR);
//                handResultData(byteOpen);
//                Util.doIt(byteOpen[4]);

                byte[] receiveLightData = serialPortService.sendData(cmd_LIGHT_OPEN);
                Log.i(TAG, "receiveData-----" + receiveLightData);


                break;
            case R.id.btn_close_gate:
////                byte[] byteClose = serialPortService.sendData(cmd_CLOSE);
//                byte[] byteClose = serialPortService.sendData(cmd_CLOSE_STR);
//                handResultData(byteClose);

                byte[] receiveLightData2 = serialPortService.sendData(cmd_LIGHT_CLOSE);
                Log.i(TAG, "receiveData-----" + receiveLightData2);
                break;
            case R.id.btn_infrared:
//                byte[] byteInfrared = serialPortService.sendData(cmd_INFRARED);
                byte[] byteInfrared = serialPortService.sendData(cmd_INFRARED_STR);
                handResultData(byteInfrared);
                break;
            case R.id.btn_gate_status:
//                byte[] byteStatus = serialPortService.sendData(cmd_GATE_STATUS);
                byte[] byteStatus = serialPortService.sendData(cmd_GATE_STATUS_STR);
                handResultData(byteStatus);
                break;
            case R.id.gun_gate_open:
                byte[] gunOpen = serialPortService.sendData(cmd_GUN_GATE_OPEN);
                handResultData(gunOpen);
                break;
            case R.id.gun_gate_type:
                byte[] gunType = serialPortService.sendData(cmd_GUN_GATE_TYPE);
                handResultData(gunType);
                break;
            case R.id.btn_send:
                String sendData = mDataSendEdit.getText().toString();
                byte[] receiveData = serialPortService.sendData(sendData);
                Log.i(TAG, "receiveData-----" + receiveData);
                handResultData(receiveData);

//                String data = Integer.toHexString(25);
//                Log.i(TAG,"data------"+data);
                break;
            case R.id.btn_turn:
//                byte[] bytes = Util.str2byte("04");
//                Log.i(TAG,"二进制数据--------"+ Arrays.toString(bytes));
                Util.doIt((byte) 0x3F);
                break;
            case R.id.btn_clear:
                mBaudrateEdit.setText("");
                mPortEdit.setText("");
                break;
            case R.id.btn_open_light:
//                byte[] receiveLightData = serialPortService.sendData(cmd_LIGHT_OPEN);
//                Log.i(TAG, "receiveData-----" + receiveLightData);
                break;
            case R.id.btn_close_light:
//                byte[] receiveLightData2 = serialPortService.sendData(cmd_LIGHT_CLOSE);
//                Log.i(TAG, "receiveData-----" + receiveLightData2);
                break;
        }
    }

    private void initSerial() {
        mDataReceiveEdit.setText("");
        String port = mPortEdit.getText().toString();
        int baudrate = Integer.valueOf(mBaudrateEdit.getText().toString());
        serialPortService = new SerialPortBuilder()
                .setTimeOut(100L)
                .setBaudrate(baudrate)
                .setDevicePath(port)
                .createService();
        serialPortService.isOutputLog(true);
    }

    private void handResultData(byte[] receiveData) {
        if (receiveData != null) {
            String receiveStr = ByteStringUtil.byteArrayToHexStr(receiveData);
            Log.e("MainActivity：", receiveStr);
            mDataReceiveEdit.setText(receiveStr);
        }

        if (receiveData.length > 8) {
            int i = Util.doIt(receiveData[7]);
            Log.i(TAG, "钥匙的状态------" + i);
        }
    }

    class MyTask implements Runnable {
        @Override
        public void run() {
            int num = 0;
            while (num < 500) {
                try {
                    num++;
                    Log.d(TAG, "当前线程-----" + Thread.currentThread().getName() + "       当前数值------" + num);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
