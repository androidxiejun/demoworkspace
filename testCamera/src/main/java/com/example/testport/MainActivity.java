package com.example.testport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn = findViewById(R.id.btn_camera);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> backCamera = CameraUtik.getBackCamera();
                List<Integer> fontCamera = CameraUtik.getFontCamera();
                if (backCamera == null || backCamera.size() == 0) {
                    Log.i("MainA", "后置为空-----");
                } else {
                    for (int data : backCamera) {
                        Log.i("MainA", "后置编号-----"+data);
                    }
                }

                if (fontCamera == null || fontCamera.size() == 0) {
                    Log.i("MainA", "前置为空-----");
                } else {
                    for (int data : fontCamera) {
                        Log.i("MainA", "前置编号-----"+data);
                    }
                }

            }
        });
    }
}
