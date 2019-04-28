package com.example.testannotation;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by AndroidXJ on 2019/4/17.
 */

public class BindViewProcess {

    public static void bind(Activity activity){
        Class viewClass=activity.getClass();
        Field[] declaredFields = viewClass.getDeclaredFields();
        Method[] declaredMethods = viewClass.getDeclaredMethods();
       for (Method method:declaredMethods){
           
       }
    }
}
