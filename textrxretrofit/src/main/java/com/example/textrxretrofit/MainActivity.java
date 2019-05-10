package com.example.textrxretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.textrxretrofit.callback.INetCallback;
import com.example.textrxretrofit.entity.sql.GreenDaoUtil;
import com.example.textrxretrofit.retrofit.NetLoader;
import com.example.textrxretrofit.retrofit.RetrofitClient;
import com.example.textrxretrofit.util.Util;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private Button mUploadBtn;
    private Button mUploadBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
    }

    private void init() {
        //初始化Retrofit;
        RetrofitClient.getInstance().init();
        //初始化GreenDao
        GreenDaoUtil.getInstance().init(getApplicationContext());
    }

    private void initView() {
        mUploadBtn = findViewById(R.id.btn_upload);
        mUploadBtn.setOnClickListener(this);
        mUploadBtn2 = findViewById(R.id.btn_upload_two);
        mUploadBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload:
                RetrofitClient.getInstance().uploadRx("octocat", new INetCallback<String>() {
                    @Override
                    public void success(String data) {
                        Log.i(TAG, "上传数据成功-----" + data);
                    }

                    @Override
                    public void failed(String message) {
                        Log.i(TAG, "上传数据失败-----" + message);
                    }
                });

                break;
            case R.id.btn_upload_two:
//                NetLoader.getInstance().getGithub("octocat").subscribe(new Consumer<ResponseBody>() {
//                    @Override
//                    public void accept(ResponseBody responseBody) throws Exception {
//                        Log.i(TAG, "上传数据成功-----" + responseBody.string().toString());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.i(TAG, "上传数据失败-----" + throwable.getMessage());
//                    }
//                });
                RetrofitClient.getInstance().uploadRx(new INetCallback<ResponseBody>() {
                    @Override
                    public void success(ResponseBody responseBody) {
                        try {
                            Log.i(TAG, "上传数据成功-----" + responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(String message) {
                        Log.i(TAG, "上传数据失败-----" + message);
                    }
                });
                break;
        }
    }
}
