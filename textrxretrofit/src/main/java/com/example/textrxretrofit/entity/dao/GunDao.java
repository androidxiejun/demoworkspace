package com.example.textrxretrofit.entity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.textrxretrofit.entity.bean.Gun;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "db_gun".
*/
public class GunDao extends AbstractDao<Gun, Long> {

    public static final String TABLENAME = "db_gun";

    /**
     * Properties of entity Gun.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property GunNo = new Property(1, String.class, "gunNo", false, "gun_no");
        public final static Property GunType = new Property(2, String.class, "gunType", false, "gun_type");
    }


    public GunDao(DaoConfig config) {
        super(config);
    }
    
    public GunDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"db_gun\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"gun_no\" TEXT," + // 1: gunNo
                "\"gun_type\" TEXT);"); // 2: gunType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"db_gun\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Gun entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String gunNo = entity.getGunNo();
        if (gunNo != null) {
            stmt.bindString(2, gunNo);
        }
 
        String gunType = entity.getGunType();
        if (gunType != null) {
            stmt.bindString(3, gunType);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Gun entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String gunNo = entity.getGunNo();
        if (gunNo != null) {
            stmt.bindString(2, gunNo);
        }
 
        String gunType = entity.getGunType();
        if (gunType != null) {
            stmt.bindString(3, gunType);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Gun readEntity(Cursor cursor, int offset) {
        Gun entity = new Gun( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // gunNo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // gunType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Gun entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGunNo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGunType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Gun entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Gun entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Gun entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
