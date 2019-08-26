package com.example.dhht_70.demotest3;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName().toString();
    private Button mBtnAdd;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UscOperate.getInstance(this.getApplicationContext()).initTts();
        initView();

    }

    private void initView() {
        mBtnAdd = findViewById(R.id.btn_app_task);
        mBtnAdd.setOnClickListener(this);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_app_task:
                new Thread(new MyTask()).start();
                break;
        }
    }

    class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true){
                    String fileName = System.currentTimeMillis() + "";
                    stringTxt(Config.str, fileName);
                    Log.d(TAG, "当前线程-----" + Thread.currentThread().getName());
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stringTxt(String str, String fileName) {
        try{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(str.getBytes());

            FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/shooting/Gate/"+fileName+".txt");

            int len = -1;
            while((len = inputStream.read())!=-1){
                outputStream.write(len);
            }
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
