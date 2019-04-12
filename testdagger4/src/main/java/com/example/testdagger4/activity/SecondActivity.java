package com.example.testdagger4.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.testdagger4.R;
import com.example.testdagger4.component.DaggerPeopleComponent;
import com.example.testdagger4.entity.Student;
import com.example.testdagger4.entity.Teacher;

import javax.inject.Inject;

/**
 * Created by AndroidXJ on 2019/3/20.
 */

public class SecondActivity extends AppCompatActivity {
    @Inject
    Student mStudent;
    @Inject
    Student mStudent2;
    @Inject
    Teacher mTeacher;
    @Inject
    Teacher mTeacher2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();
    }

    private void inject() {
        DaggerPeopleComponent.getInstance().inject(this);
        Log.i(MainActivity.TAG,"SecondActivity-------mStudent-----"+mStudent);
        Log.i(MainActivity.TAG,"SecondActivity-------mStudent2-----"+mStudent2);
        Log.i(MainActivity.TAG,"SecondActivity-------mTeacher-----"+mTeacher);
        Log.i(MainActivity.TAG,"SecondActivity-------mTeacher2-----"+mTeacher2);
    }
}
