package com.example.testsocketserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by AndroidXJ on 2018/11/29.
 */

public class TCPServerService extends Service {
    private static boolean mIsServiceDestroyed = false;
    private String[] mDefinefMessages = new String[]{
            "你好啊",
            "今天天气不错啊",
            "你喜欢篮球吗"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8086);
                while (!mIsServiceDestroyed) {
                    try {
                        //接收客户端的请求
                        final Socket client = serverSocket.accept();
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    responseClient(client);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                    } catch (Exception e) {

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }


    private void responseClient(Socket client) throws IOException {
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎来到聊天室！");
        while (!mIsServiceDestroyed) {
            String str = in.readLine();
            if (str == null) {
                break;
            }
            int i = new Random().nextInt(mDefinefMessages.length);
            String msg = mDefinefMessages[i];
            out.println(msg);
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
            client.close();
        }
    }
}
