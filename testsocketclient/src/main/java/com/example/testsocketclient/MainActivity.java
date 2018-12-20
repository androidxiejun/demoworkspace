package com.example.testsocketclient;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private Button mSendBtn;
    private TextView mReceiveTxt;
    private EditText mSendEdit;
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent service = new Intent(this, TcpClientServer.class);
        startService(service);
        new Thread(new TcpClientServer()).start();
    }

    private void initView() {
        mSendBtn = findViewById(R.id.btn_send);
        mReceiveTxt = findViewById(R.id.receive_txt);
        mSendEdit = findViewById(R.id.edit_send_txt);
    }

    class TcpClientServer implements Runnable {
        @Override
        public void run() {
            Socket socket = null;
            while (socket == null) {
                try {
                    socket = new Socket("localhost", 8086);
                    mClientSocket = socket;
                    mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                } catch (IOException e) {
                    SystemClock.sleep(1000);
                }
            }
            try{
                //接收服务器端的消息
                BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!MainActivity.this.isFinishing()){
                    String msg=br.readLine();
                    if(msg!=null){
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,msg).sendToTarget();
                    }
                }
            }catch (Exception e){

            }
        }
    }
}
