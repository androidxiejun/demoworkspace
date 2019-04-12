package com.example.testhttps;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by AndroidXJ on 2018/12/28.
 */

public interface PostService {
    @POST("test")
    @FormUrlEncoded
    Call<ResponseBody> postFormUrlEncoded(@Field("name") String name, @Field("pwd") String pwd);
}
