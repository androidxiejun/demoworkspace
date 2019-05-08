package com.example.textrxretrofit.entity.sql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.textrxretrofit.entity.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
