package com.example.testdagger3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by AndroidXJ on 2019/3/18.
 */

public class TestActivity extends AppCompatActivity {
    @Inject
    Student mStudent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();
    }

    private void inject() {
//        DaggerTestComponent.builder().build().inject(this);
//        DaggerTestComponent.builder().appComponent(ComponentHolder.geyAppComponent())
//                .build().inject(this);
        AppApi api = ComponentHolder.geyAppComponent().getApi();
        Log.i(MainActivity.TAG,"api------2-----"+api);
    }

}
