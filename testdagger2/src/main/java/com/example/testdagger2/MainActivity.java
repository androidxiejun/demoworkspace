package com.example.testdagger2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.testaar.SecondActivity;
import com.example.testaar.Util;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getSimpleName().toString();
    @BindView(R.id.btn)
    Button mBtn;
    @Inject
    Student mStudent;
    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inject();
    }

    @OnClick(R.id.btn)
    public void onViewClick(){
        Util.getCurrentTime();
//        Intent intent=new Intent(MainActivity.this, SecondActivity.class);
//        startActivity(intent);
        Log.i(TAG,"mStudent-----"+mStudent.toString());
        Log.i(TAG,"mSharedPreferences-----"+mSharedPreferences.toString());
    }

    private void inject(){
        DaggerSecondComponent.builder()
                .appComponent(ComponentHolder.getmAppComponent())
                .secondModule(new SecondModule(this))
                .build()
                .inject(this);
    }
}
