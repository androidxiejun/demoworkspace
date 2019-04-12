package com.example.testdagger4.module;

import com.example.testdagger4.entity.Student;
import com.example.testdagger4.scope.StudentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2019/3/20.
 */
@Module
public class StudentModule {
    @StudentScope
    @Provides
    public Student provideStudent(){
        return new Student();
    }
}
