package com.example.testdagger4.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.testdagger4.R;
import com.example.testdagger4.component.PeopleComponent;
import com.example.testdagger4.entity.Student;
import com.example.testdagger4.entity.Teacher;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getSimpleName();
    @Inject
    Student mStudent;
    @Inject
    Student mStudent2;
    @Inject
    Teacher mTeacher;
    @Inject
    Teacher mTeacher2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();
    }

    private void inject() {
        PeopleComponent.getInstance().inject(this);
        Log.i(TAG,"mStudent-----"+mStudent);
        Log.i(TAG,"mStudent2-----"+mStudent2);
        Log.i(TAG,"mTeacher-----"+mTeacher);
        Log.i(TAG,"mTeacher2-----"+mTeacher2);

        Intent intent=new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
