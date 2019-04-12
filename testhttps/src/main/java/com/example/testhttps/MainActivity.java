package com.example.testhttps;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName().toString();
    private Context context;
    private Button mBtn;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        setData();
        initView();
        doHttpsServer();
    }

    private void initView() {
        mBtn=findViewById(R.id.data_btn);
        mBtn.setOnClickListener(this);
    }

    private void setData(){
        name="xiejun";
    }

    /**
     * 使用Retrofit开始进行https通讯
     */
    private void doHttpsServer() {
        //retrofit2访问https
        SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket(context).getSocketFactory();
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xjun.com/") //url自行配置
                .client(okHttpClient.build())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<ResponseBody> call = postService.postFormUrlEncoded("", "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = response.body().toString();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "数据-----"+name, Toast.LENGTH_SHORT).show();
    }
}
