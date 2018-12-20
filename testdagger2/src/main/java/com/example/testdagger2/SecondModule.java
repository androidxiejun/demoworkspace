package com.example.testdagger2;


import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2018/12/17.
 */

@Module
public class SecondModule {
    private MainActivity mMainActivity;

    public SecondModule(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }

    @Provides
    Student provideStudent() {
        return new Student();
    }
}
