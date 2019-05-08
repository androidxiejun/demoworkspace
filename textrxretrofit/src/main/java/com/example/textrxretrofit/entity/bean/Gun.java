package com.example.textrxretrofit.entity.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AndroidXJ on 2019/5/8.
 */
@Entity(nameInDb = "db_gun")
public class Gun {
    @Id
    private Long id;
    @Property(nameInDb = "gun_no")
    private String gunNo;
    @Property(nameInDb = "gun_type")
    private String gunType;
    @Generated(hash = 1538208035)
    public Gun(Long id, String gunNo, String gunType) {
        this.id = id;
        this.gunNo = gunNo;
        this.gunType = gunType;
    }
    @Generated(hash = 398662952)
    public Gun() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGunNo() {
        return this.gunNo;
    }
    public void setGunNo(String gunNo) {
        this.gunNo = gunNo;
    }
    public String getGunType() {
        return this.gunType;
    }
    public void setGunType(String gunType) {
        this.gunType = gunType;
    }
}
