package com.example.textrxretrofit.retrofit;


import com.example.textrxretrofit.entity.bean.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by AndroidXJ on 2019/5/8.
 */

public class NetLoader extends ObjectLoader {
    private static IApi mService;
    private static NetLoader mInstance;

    public static NetLoader getInstance() {
        if (mInstance == null) {
            mInstance = new NetLoader();
        }
        return mInstance;
    }

    public IApi getService() {
        if (mService == null) {
            mService = RetrofitClient.getInstance().getRetrofit().create(IApi.class);
        }
        return mService;
    }

    public Observable<ResponseBody> getGithub(String name) {
        return observe(getService().listReposRx(name)).map(new Function<ResponseBody, ResponseBody>() {
            @Override
            public ResponseBody apply(ResponseBody responseBody) throws Exception {
                return responseBody;
            }
        });
    }

}

class ObjectLoader {
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
