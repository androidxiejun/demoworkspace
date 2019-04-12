package com.example.testdagger3;

import android.util.Log;

/**
 * Created by AndroidXJ on 2019/3/18.
 */

public class Student {
    private Master mMaster;
    public Student(Master master){
        this.mMaster=master;
    }
    public String eatFruit(String fruit){
        return fruit;
    }
}
