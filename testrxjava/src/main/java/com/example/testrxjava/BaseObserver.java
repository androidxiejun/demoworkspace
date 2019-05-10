package com.example.testrxjava;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by AndroidXJ on 2019/5/10.
 */

class BaseObserver<T> implements Observer<T> {
    private Icallback mIcallback;

    public BaseObserver(Icallback<T> callback) {
        this.mIcallback = callback;
    }

    @Override
    public void onNext(T t) {
        mIcallback.success(t);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        mIcallback.failed(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
