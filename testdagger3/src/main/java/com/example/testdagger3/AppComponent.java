package com.example.testdagger3;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by AndroidXJ on 2019/3/18.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    AppApi getApi();
}
