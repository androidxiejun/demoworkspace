package com.example.textrxretrofit.retrofit;

import com.example.textrxretrofit.entity.bean.BaseResponse;
import com.example.textrxretrofit.entity.bean.Fault;

import io.reactivex.functions.Function;


/**
 * Created by AndroidXJ on 2019/5/8.
 * 数据分发
 */

public class ResultFunction<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(BaseResponse<T> baseResponse) throws Exception {
        if (!baseResponse.isSuccess()) {
            try {
                throw new Fault(baseResponse.status, baseResponse.message);
            } catch (Fault fault) {
                fault.printStackTrace();
            }
        }
        return baseResponse.data;
    }
}
