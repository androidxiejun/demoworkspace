package com.example.textrxretrofit.retrofit;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public interface IApi {
    @GET("users/{user}/repos")
    Call<ResponseBody> listRepos(@Path("user") String user);

    @GET("users/{user}/repos")
    Observable<ResponseBody> listReposRx(@Path("user") String user);
}
