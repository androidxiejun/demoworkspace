package com.example.textrxretrofit.entity.bean;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public class BaseResponse<T> {
    public int status;
    public String message;
    public T data;
    public boolean isSuccess(){
        return status==200;
    }
}
