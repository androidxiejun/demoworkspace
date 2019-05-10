package com.example.testrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnTurn, mBtnTime;
    private ImageView mImg;
    private TextView mTxt;
    private String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnTurn = findViewById(R.id.btn_turn);
        mBtnTurn.setOnClickListener(this);
        mBtnTime = findViewById(R.id.btn_time);
        mBtnTime.setOnClickListener(this);
        mTxt = findViewById(R.id.txt);
    }

    /**
     * 观察者
     */
    private void initObserv() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("hello");
                e.onNext("你好");
                e.onComplete();
                e.onNext("哟哟哟");
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe----");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "接收到----" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete----");
            }
        };

        observable.subscribe(observer);
    }

    /**
     * interval 定时器
     */
    private void initTime() {
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.i(TAG, "收到消息------");
            }
        });
    }

    /**
     * map
     */
    private void doObserverMap() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "-----";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                data += s;
            }
        });
        mTxt.setText(data);
    }

    /**
     * flatMap
     */
    private void doObserverFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> dataList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    dataList.add(integer + "--" + i);
                }
                return Observable.fromIterable(dataList).delay(1000, TimeUnit.MICROSECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "data------" + s);
            }
        });
    }

    /**
     * compose
     */
    private void doObserverCompose() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
            }
        }).compose(this.<String>applyObservableAsync())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "data-----" + s);
                    }
                });
    }

    private void doObserverCompose2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("23");
            }
        }).compose(this.<String>applyObservableAsync())
                .subscribe(new BaseObserver<String>(new Icallback<String>() {
                    @Override
                    public void success(String s) {
                        Log.i(TAG, "data-----" + s);
                    }

                    @Override
                    public void failed(String message) {
                        Log.i(TAG, "failed-----" + message);
                    }
                }));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_turn:
                doObserverCompose2();
                break;
            case R.id.btn_time:
                initTime();
                break;
        }
    }

    public <T> ObservableTransformer<T, T> applyObservableAsync() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
