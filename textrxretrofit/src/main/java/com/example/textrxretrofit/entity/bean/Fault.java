package com.example.textrxretrofit.entity.bean;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public class Fault extends Throwable{
    public int status;
    public String message;

    public Fault(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
