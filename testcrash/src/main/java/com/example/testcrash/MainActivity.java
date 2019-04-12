package com.example.testcrash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catchCrash();
    }
    private void catchCrash(){
        try{
            String data=null;
            if(data.equals("a")){

            }
        }catch (Exception e){
            //在这里捕获异常并将异常上送到服务器或者保存在本地
            CrashHandler.getInstance().handleExceptionByCatch(e);
        }
    }
}
