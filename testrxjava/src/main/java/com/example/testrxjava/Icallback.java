package com.example.testrxjava;

/**
 * Created by AndroidXJ on 2019/5/10.
 */

public interface  Icallback<T> {
    void success(T t);
    void failed(String message);
}
