package com.example.textrxretrofit.entity.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.textrxretrofit.entity.dao.DaoMaster;
import com.example.textrxretrofit.entity.dao.DaoSession;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public class GreenDaoUtil {
    private static GreenDaoUtil mInstance;

    private static DaoMaster.OpenHelper mOpenHelper;
    private static DaoMaster mDaoMater;
    private static DaoSession mDaoSsssion;
    private static SQLiteDatabase mDb;

    public static GreenDaoUtil getInstance() {
        synchronized (GreenDaoUtil.class) {
            if (mInstance == null) {
                synchronized (GreenDaoUtil.class) {
                    mInstance = new GreenDaoUtil();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mOpenHelper = new MyOpenHelper(context, "gundata.db");
        mDb = mOpenHelper.getReadableDatabase();
        mDaoMater = new DaoMaster(mDb);
        mDaoSsssion = mDaoMater.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSsssion;
    }

}
