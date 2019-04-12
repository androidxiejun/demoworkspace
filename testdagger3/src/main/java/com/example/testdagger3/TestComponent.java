package com.example.testdagger3;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by AndroidXJ on 2019/3/18.
 */
@ActivityScope
@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(MainActivity mainActivity);
    void inject(TestActivity testActivity);
}
