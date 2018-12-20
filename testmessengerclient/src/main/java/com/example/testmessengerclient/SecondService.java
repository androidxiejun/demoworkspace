package com.example.testmessengerclient;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by AndroidXJ on 2018/11/27.
 */

public class SecondService extends Service {
    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgFromClient) {
            try {
                Message serverMessage = Message.obtain(msgFromClient);
                serverMessage.arg2 = msgFromClient.arg1 + msgFromClient.arg2;
                msgFromClient.replyTo.send(serverMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
