package com.example.testdagger2;

import android.content.SharedPreferences;

import dagger.Component;

/**
 * Created by AndroidXJ on 2018/12/17.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    SharedPreferences sharedPreferences();

    MyApplication myApplication();
}
