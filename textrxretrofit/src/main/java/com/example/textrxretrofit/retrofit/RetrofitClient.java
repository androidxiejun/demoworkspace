package com.example.textrxretrofit.retrofit;

import android.util.Log;

import com.example.textrxretrofit.callback.INetCallback;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private static Retrofit mRetrofit;
    private static IApi mService;

    public static RetrofitClient getInstance() {
        synchronized (RetrofitClient.class) {
            if (mInstance == null) {
                synchronized (RetrofitClient.class) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mService = mRetrofit.create(IApi.class);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public void upLoad(final INetCallback<ResponseBody> callback) {
        Call<ResponseBody> data = mService.listRepos("octocat");
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.failed(t.getMessage());
            }
        });
    }

    public void uploadRx(final INetCallback<ResponseBody> callback) {
        mService.listReposRx("octocat")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        callback.success(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.failed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
