package com.example.testgreendao.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AndroidXJ on 2019/4/8.
 */
@Entity(nameInDb = "db_student_score")
public class StudentScore {
    @Id
    private Long id;
    @Property(nameInDb = "student_id")
    private Long studentId;
    @Property(nameInDb = "score_id")
    private Long scoreId;
    @Generated(hash = 67916684)
    public StudentScore(Long id, Long studentId, Long scoreId) {
        this.id = id;
        this.studentId = studentId;
        this.scoreId = scoreId;
    }
    @Generated(hash = 311323642)
    public StudentScore() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStudentId() {
        return this.studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Long getScoreId() {
        return this.scoreId;
    }
    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }
}
