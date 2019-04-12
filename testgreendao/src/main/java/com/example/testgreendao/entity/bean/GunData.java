package com.example.testgreendao.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.example.testgreendao.entity.dao.DaoSession;
import com.example.testgreendao.entity.dao.UserDao;
import com.example.testgreendao.entity.dao.GunDataDao;

/**
 * Created by AndroidXJ on 2019/3/28.
 */
@Entity
public class GunData implements Parcelable{
    @Id
    private Long id;
    @Property(nameInDb = "gun_name")
    private String gunName;
    @Property(nameInDb = "gun_no")
    private String gunNo;
    @Property(nameInDb = "hander_name")
    private String handerName;
    @Property(nameInDb = "hander_no")
    private String handerNo;
    @Property(nameInDb = "user_id")
    private Long userId;
    @ToOne(joinProperty = "userId")
    private User mUser;

    protected GunData(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        gunName = in.readString();
        gunNo = in.readString();
        handerName = in.readString();
        handerNo = in.readString();
    }

    @Generated(hash = 1706522441)
    public GunData(Long id, String gunName, String gunNo, String handerName,
            String handerNo, Long userId) {
        this.id = id;
        this.gunName = gunName;
        this.gunNo = gunNo;
        this.handerName = handerName;
        this.handerNo = handerNo;
        this.userId = userId;
    }

    @Generated(hash = 747387418)
    public GunData() {
    }

    public static final Creator<GunData> CREATOR = new Creator<GunData>() {
        @Override
        public GunData createFromParcel(Parcel in) {
            return new GunData(in);
        }

        @Override
        public GunData[] newArray(int size) {
            return new GunData[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 944121605)
    private transient GunDataDao myDao;
    @Generated(hash = 1377221062)
    private transient Long mUser__resolvedKey;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(gunName);
        dest.writeString(gunNo);
        dest.writeString(handerName);
        dest.writeString(handerNo);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGunName() {
        return this.gunName;
    }

    public void setGunName(String gunName) {
        this.gunName = gunName;
    }

    public String getGunNo() {
        return this.gunNo;
    }

    public void setGunNo(String gunNo) {
        this.gunNo = gunNo;
    }

    public String getHanderName() {
        return this.handerName;
    }

    public void setHanderName(String handerName) {
        this.handerName = handerName;
    }

    public String getHanderNo() {
        return this.handerNo;
    }

    public void setHanderNo(String handerNo) {
        this.handerNo = handerNo;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 59229727)
    public User getMUser() {
        Long __key = this.userId;
        if (mUser__resolvedKey == null || !mUser__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User mUserNew = targetDao.load(__key);
            synchronized (this) {
                mUser = mUserNew;
                mUser__resolvedKey = __key;
            }
        }
        return mUser;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 15274313)
    public void setMUser(User mUser) {
        synchronized (this) {
            this.mUser = mUser;
            userId = mUser == null ? null : mUser.getId();
            mUser__resolvedKey = userId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1060012353)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGunDataDao() : null;
    }
}
