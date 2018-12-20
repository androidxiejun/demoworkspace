package com.example.testdagger2;

/**
 * Created by AndroidXJ on 2018/12/17.
 */

public class ComponentHolder {
    private static AppComponent mAppComponent;

    public static AppComponent getmAppComponent() {
        return mAppComponent;
    }

    public static void setmAppComponent(AppComponent mAppComponent) {
        ComponentHolder.mAppComponent = mAppComponent;
    }


}
