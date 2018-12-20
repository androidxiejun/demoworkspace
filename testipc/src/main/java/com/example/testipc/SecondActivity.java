package com.example.testipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by AndroidXJ on 2018/11/22.
 */

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mIntentBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("IPC","SecondActivity:sUserId-------------"+UserManager.sUserId);
        initView();
    }

    private void initView() {
        mIntentBtn=findViewById(R.id.intent_btn);
        mIntentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
        startActivity(intent);
    }
}
