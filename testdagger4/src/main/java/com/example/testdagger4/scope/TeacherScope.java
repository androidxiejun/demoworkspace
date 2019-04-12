package com.example.testdagger4.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by AndroidXJ on 2019/3/20.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TeacherScope {
}
