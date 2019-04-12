package com.example.testdagger3;

import android.util.Log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * Created by AndroidXJ on 2019/3/19.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TestLog {
    String value();
}
