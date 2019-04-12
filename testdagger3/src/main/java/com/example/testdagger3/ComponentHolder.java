package com.example.testdagger3;

/**
 * Created by AndroidXJ on 2019/3/18.
 */

public class ComponentHolder {
    private static AppComponent mAppComponent;
    public static void setAppComponent(AppComponent appComponent){
        ComponentHolder.mAppComponent=appComponent;
    }
    public static AppComponent geyAppComponent(){
        return mAppComponent;
    }
}
