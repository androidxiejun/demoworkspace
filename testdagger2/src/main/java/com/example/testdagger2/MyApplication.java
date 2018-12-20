package com.example.testdagger2;

import android.app.Application;

/**
 * Created by AndroidXJ on 2018/12/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        ComponentHolder.setmAppComponent(appComponent);
    }
}
