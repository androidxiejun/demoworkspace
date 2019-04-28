package com.example.testarouter.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.example.testarouter.activity.MainActivity;

/**
 * Created by AndroidXJ on 2019/4/18.
 */
@Interceptor(priority = 3)
public class UserSecondIInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.i(MainActivity.TAG,"process------------UserSecondIInterceptor");
    }

    @Override
    public void init(Context context) {
        Log.i(MainActivity.TAG,"init------------UserSecondIInterceptor");
    }
}
