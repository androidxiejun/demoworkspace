package com.example.testdagger3;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2019/3/18.
 */
@Module
public class AppModule {
    @Singleton
    @Provides
    AppApi provideApi(){
        return new AppApi();
    }
}
