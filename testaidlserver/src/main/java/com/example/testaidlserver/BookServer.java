package com.example.testaidlserver;


import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by AndroidXJ on 2018/11/28.
 */

public class BookServer extends Service {
    private static final String TAG = "MainActivity";
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListeners = new CopyOnWriteArrayList<>();
    //TODO　专用于跨进程回调用
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("冰与火之歌第一季", 80));
        mBookList.add(new Book("冰与火之歌第二季", 85));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("com.example.testaidlserver.TEST");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            Log.i(TAG,"onBind erro!");
//            return null;
//        }
        return new MyBinder();
    }
    class MyBinder extends IBookInterface.Stub {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }
        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListeners.contains(listener)) {
//                mListeners.add(listener);
//                Log.i(TAG, "add register success!");
//            } else {
//                Log.i(TAG, "listaner already exists!");
//            }
//            Log.i(TAG, "register size : " + mListeners.size());
            mListenerList.register(listener);
            Log.i(TAG, "注册的数量a----" + mListenerList.getRegisteredCallbackCount());
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListeners.contains(listener)) {
//                mListeners.remove(listener);
//                Log.i(TAG, "remove register success!");
//            } else {
//                Log.i(TAG, "register not exists!");
//            }
//            Log.i(TAG, "register size : " + mListeners.size());
            mListenerList.unregister(listener);
            Log.i(TAG, "注册的数量b----" + mListenerList.getRegisteredCallbackCount());
        }
    }
    private void onNewBookArrived(Book book) {
//        mBookList.add(book);
//        for (int i = 0; i < mListeners.size(); i++) {
//            try {
//                IOnNewBookArrivedListener listener = mListeners.get(i);
//                listener.onNewBookArrived(book);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
        mBookList.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    listener.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }
    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book("少狼第" + bookId + "季", 100);
                onNewBookArrived(newBook);
            }
        }
    }
}
