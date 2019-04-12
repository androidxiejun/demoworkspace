package com.example.testdagger3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName().toString();
    private Button mBtn;
    private TextView mTxt;
    @Inject
    Student mStudent;
    @Inject
    Student mStudentB;
    @TestLog("sss")
    Student mStudentC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        inject();
    }

    private void initView() {
        mBtn = findViewById(R.id.btn);
        mTxt = findViewById(R.id.txt);
        mBtn.setOnClickListener(this);
        mStudentC=new Student(new Master());
    }

    private void inject() {
        DaggerTestComponent.builder().build().inject(this);
//        DaggerTestComponent.builder().appComponent(ComponentHolder.geyAppComponent())
//                .build().inject(this);
        AppApi api = ComponentHolder.geyAppComponent().getApi();
        Log.i(MainActivity.TAG, "api------1----" + api);
        Log.i(MainActivity.TAG,"student---1----"+mStudent);
    }

    @Override
    public void onClick(View v) {
//        mTxt.setText(mStudent.eatFruit("apple"));
//        Log.i(TAG,"mStudent------"+mStudent);
//        Log.i(TAG,"mStudentB------"+mStudentB);
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivity(intent);
    }
}
