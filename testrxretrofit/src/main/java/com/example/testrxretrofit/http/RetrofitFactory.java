package com.example.testrxretrofit.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AndroidXJ on 2019/4/28.
 */

public class RetrofitFactory {
    private static RetrofitFactory mInstance;

    public static RetrofitFactory getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitFactory();
                }
            }
        }
        return mInstance;
    }


    public HttpService getCustomHaierAPi(String baseUrl) {
        return getRetrofit(baseUrl).create(HttpService.class);
    }

    public Retrofit getRetrofit(String baseUrl) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
//                AirLogger.debugE("okhttp",message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
}
