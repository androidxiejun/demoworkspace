package com.example.testdagger3;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2019/3/18.
 */
@Module(includes = TestModuleB.class)
public class TestModule {
    @Provides
    Student provideStudent(Master master){
        return new Student(master);
    }
}
