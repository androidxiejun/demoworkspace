package com.example.testannotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getSimpleName();

    @AutoWired
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoWiredProcess.bind(this);
        user.setName("xj");
    }
}
