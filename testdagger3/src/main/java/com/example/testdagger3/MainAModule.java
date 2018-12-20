package com.example.testdagger3;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2018/12/18.
 */

@Module
public class MainAModule {
    private MainAActivity mMainActivity;

    public MainAModule(MainAActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }
    @Provides
    @ActivityScope
    Student provideStudent(){
        return new Student();
    }
}
