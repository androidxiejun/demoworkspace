package com.example.testgreendao;

import android.app.Application;

import com.example.testgreendao.entity.sql.GreenDaoUtil;

/**
 * Created by AndroidXJ on 2019/3/28.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance(){
        if(instance==null){
            synchronized (MyApplication.class){
                if(instance==null){
                    instance=new MyApplication();
                }
            }
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        GreenDaoUtil.getInstance().init(getApplicationContext());
    }
}
