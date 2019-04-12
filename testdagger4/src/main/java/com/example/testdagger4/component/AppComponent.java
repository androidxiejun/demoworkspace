package com.example.testdagger4.component;

import android.app.Application;

import com.example.testdagger4.entity.Teacher;
import com.example.testdagger4.module.AppModule;
import com.example.testdagger4.scope.TeacherScope;

import dagger.Component;

/**
 * Created by AndroidXJ on 2019/3/20.
 */
@TeacherScope
@Component(modules = AppModule.class)
public interface AppComponent {
    Teacher getTeacher();
}
