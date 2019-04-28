package com.example.testannotation;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by AndroidXJ on 2019/4/17.
 */

public class AutoWiredProcess {
    public static void bind(final Object object) {
        Class parentClass = object.getClass();
        Field[] fields = parentClass.getDeclaredFields();
        for (final Field field : fields) {
            Log.i(MainActivity.TAG,"反射获取到的field-----"+field.getName());
            AutoWired autoWired = field.getAnnotation(AutoWired.class);
            if (autoWired != null) {
                field.setAccessible(true);
                try {
                    Class<?> autoCreateClass = field.getType();
                    Log.i(MainActivity.TAG,"getType-----"+autoCreateClass.getName());
                    Constructor autoCreateConstructor = autoCreateClass.getConstructor();
                    Log.i(MainActivity.TAG,"autoCreateConstructor-----"+autoCreateConstructor.getName());
                    field.set(object, autoCreateConstructor.newInstance());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
