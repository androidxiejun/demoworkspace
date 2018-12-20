package com.example.testproviderserver;

import android.content.Context;

/**
 * Created by AndroidXJ on 2018/11/29.
 */

public class DaoManager {
    private static final String DB_NAME = "db_book";
    private Context mContext;

    private volatile static DaoManager mDaoManager = new DaoManager();
    private static DaoMaster mDaoMaster;
    private static DaoMaster.DevOpenHelper mOpenHelper;
    private static DaoSession mDaoSession;

    public static DaoManager getInstance() {
        return mDaoManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     *
     * @return
     */
    public DaoMaster getmDaoMaster() {
        if (mDaoMaster == null) {
            mOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
            mDaoMaster = new DaoMaster(mOpenHelper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     *
     * @return
     */
    public DaoSession getSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getmDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
            mOpenHelper = null;
        }
    }

    public void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

}
