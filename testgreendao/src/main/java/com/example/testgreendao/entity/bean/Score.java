package com.example.testgreendao.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.testgreendao.entity.dao.DaoSession;
import com.example.testgreendao.entity.dao.StudentDao;
import com.example.testgreendao.entity.dao.ScoreDao;

/**
 * Created by AndroidXJ on 2019/4/8.
 */
@Entity(nameInDb = "db_score")
public class Score {
    @Id
    private Long id;
    @Property(nameInDb = "score_name")
    private String scoreName;
    @Property(nameInDb = "score_code")
    private String scoreCode;
    @ToMany
    @JoinEntity(
            entity = StudentScore.class,
            sourceProperty = "scoreId",
            targetProperty = "studentId"
    )
    List<Student> studentList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 302717168)
    private transient ScoreDao myDao;
    @Generated(hash = 2019085731)
    public Score(Long id, String scoreName, String scoreCode) {
        this.id = id;
        this.scoreName = scoreName;
        this.scoreCode = scoreCode;
    }
    @Generated(hash = 226049941)
    public Score() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getScoreName() {
        return this.scoreName;
    }
    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }
    public String getScoreCode() {
        return this.scoreCode;
    }
    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1514739298)
    public List<Student> getStudentList() {
        if (studentList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentDao targetDao = daoSession.getStudentDao();
            List<Student> studentListNew = targetDao._queryScore_StudentList(id);
            synchronized (this) {
                if (studentList == null) {
                    studentList = studentListNew;
                }
            }
        }
        return studentList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1628625923)
    public synchronized void resetStudentList() {
        studentList = null;
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
    @Generated(hash = 339145390)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getScoreDao() : null;
    }
}
