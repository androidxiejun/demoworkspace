package com.example.testeventbus.eventbean;

/**
 * Created by AndroidXJ on 2019/4/18.
 */

public class FirstEvent {
    private String mMsg;
    public FirstEvent(String msg) {
        mMsg = msg;
    }
    public String getMsg(){
        return mMsg;
    }

}
