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
 * Created by AndroidXJ on 2018/11/26.
 */

public class MessengerService extends Service {

    private static final int MSG_SUM = 0x110;

    private Messenger mMessenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msgfromClient) {
            Message msgToClient=Message.obtain(msgfromClient);
            switch (msgfromClient.what){
                case MSG_SUM:
                    try {
                        msgToClient.what = MSG_SUM;
                        Thread.sleep(2*1000);
                        msgToClient.arg2 = msgfromClient.arg1 + msgfromClient.arg2;
                        msgfromClient.replyTo.send(msgToClient);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    });
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
