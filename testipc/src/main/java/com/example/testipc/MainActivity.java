package com.example.testipc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mIntentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserManager.sUserId=2;
        initView();
    }

    private void initView() {
        mIntentBtn=findViewById(R.id.intent_btn);
        mIntentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("IPC","MainActivity:sUserId-------------"+UserManager.sUserId);
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
    }
}
