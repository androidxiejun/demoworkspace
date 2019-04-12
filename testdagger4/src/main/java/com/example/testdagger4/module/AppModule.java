package com.example.testdagger4.module;

import com.example.testdagger4.entity.Teacher;
import com.example.testdagger4.scope.TeacherScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2019/3/20.
 */
@Module
public class AppModule {

    @TeacherScope
    @Provides
     public Teacher provideTeacher(){
         return new Teacher();
     }
}
