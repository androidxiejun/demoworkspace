package com.example.testgreendao.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.example.testgreendao.entity.dao.DaoSession;
import com.example.testgreendao.entity.dao.UserDao;
import com.example.testgreendao.entity.dao.GunDao;

/**
 * Created by AndroidXJ on 2019/4/8.
 */
@Entity(nameInDb = "db_user")
public class User {
    @Id
    private Long id;
    @Property(nameInDb = "user_name")
    private String userName;
    @Property(nameInDb = "user_no")
    private String userNo;
//    @Property(nameInDb = "custom_id")
//    private Long customId;
    @ToMany(referencedJoinProperty = "customId")
    private List<Gun>guns;
    /** Used to resolve relations */
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    @Generated(hash = 1205729264)
    public User(Long id, String userName, String userNo) {
        this.id = id;
        this.userName = userName;
        this.userNo = userNo;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserNo() {
        return this.userNo;
    }
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 689807708)
    public List<Gun> getGuns() {
        if (guns == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GunDao targetDao = daoSession.getGunDao();
            List<Gun> gunsNew = targetDao._queryUser_Guns(id);
            synchronized (this) {
                if (guns == null) {
                    guns = gunsNew;
                }
            }
        }
        return guns;
    }

    public void setGuns(List<Gun>gunList){
           this.guns=gunList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1413666689)
    public synchronized void resetGuns() {
        guns = null;
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
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
