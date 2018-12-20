package com.example.testdagger2;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2018/12/17.
 */

@Module
public class AppModule {
    private MyApplication mApplicationp;

    public AppModule(MyApplication applicationp) {
        this.mApplicationp = applicationp;
    }

    @Provides
    SharedPreferences provideSharedPreference(){
        return  mApplicationp.getSharedPreferences("spfile", Context.MODE_PRIVATE);
    }

    @Provides
    MyApplication provideApplication(){
        return mApplicationp;
    }
}
