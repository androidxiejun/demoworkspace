package com.example.testaidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.testaidlserver.Book;
import com.example.testaidlserver.IBookInterface;
import com.example.testaidlserver.IOnNewBookArrivedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IBookInterface mRemoteManager;

    private Handler mHamdler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private IOnNewBookArrivedListener mIOnNewBookArrivedListener=new IOnNewBookArrivedListener.Stub() {
        //TODO  运行在Binder线程池中，不能执行UI操作
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.i(TAG,"到新书啦----"+newBook.toString());
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //TODO  运行在主线程中
            try {
                IBookInterface bookInterface = IBookInterface.Stub.asInterface(service);
                mRemoteManager=bookInterface;
                List<Book> list = bookInterface.getBookList();
                Log.i(TAG, "list:" + list.toString());
                Book newBook = new Book("冰与火之歌第三季", 90);
                bookInterface.addBook(newBook);
                List<Book> list2 = bookInterface.getBookList();
                Log.i(TAG, "list2:" + list2.toString());
                bookInterface.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //TODO  运行在主线程中
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent("com.xjun.aidl");
        intent.setPackage("com.example.testaidlserver");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        if(mRemoteManager!=null&&
                mRemoteManager.asBinder().isBinderAlive()){
            try {
                mRemoteManager.unRegisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
