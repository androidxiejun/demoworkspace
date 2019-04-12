package com.example.testgreendao.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AndroidXJ on 2019/4/8.
 */
@Entity(nameInDb = "db_gun")
public class Gun {
    @Id
    private Long id;
    @Property(nameInDb = "gun_name")
    private String gunName;
    @Property(nameInDb = "gun_no")
    private String gunNo;
    @Property(nameInDb = "custom_id")
    private Long customId;
    @Generated(hash = 457293640)
    public Gun(Long id, String gunName, String gunNo, Long customId) {
        this.id = id;
        this.gunName = gunName;
        this.gunNo = gunNo;
        this.customId = customId;
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
    public Long getCustomId() {
        return this.customId;
    }
    public void setCustomId(Long customId) {
        this.customId = customId;
    }
}
