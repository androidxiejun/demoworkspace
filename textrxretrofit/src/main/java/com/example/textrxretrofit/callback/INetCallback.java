package com.example.textrxretrofit.callback;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public interface INetCallback<T> {
    void success(T t);
    void failed(String message);
}
