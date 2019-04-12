package com.example.testgreendao.entity.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.testgreendao.entity.dao.DaoMaster;
import com.example.testgreendao.entity.dao.DaoSession;

/**
 * Created by AndroidXJ on 2019/3/28.
 */

public class GreenDaoUtil {
    private static GreenDaoUtil instance;

    private DaoMaster.OpenHelper mHelper;
    private SQLiteDatabase mDb;
    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;

    public static GreenDaoUtil getInstance() {
        if (instance == null) {
            synchronized (GreenDaoUtil.class) {
                if (instance == null) {
                    instance = new GreenDaoUtil();
                }
            }
        }
        return instance;
    }

    public void init(Context context){
        mHelper=new MyOpenHelper(context,"gundata.db",null);
        mDb=mHelper.getWritableDatabase();
        mDaoMaster=new DaoMaster(mDb);
        mDaoSession=mDaoMaster.newSession();
    }
    public SQLiteDatabase getDb(){
        return mDb;
    }
    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
