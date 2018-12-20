package com.example.testipc.bean;

import java.io.Serializable;

/**
 * Created by AndroidXJ on 2018/11/22.
 */

public class UserSerial implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
