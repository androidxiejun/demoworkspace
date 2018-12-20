package com.example.testdagger2;

import dagger.Component;

/**
 * Created by AndroidXJ on 2018/12/17.
 */

@Component(modules = SecondModule.class, dependencies = AppComponent.class)
public interface SecondComponent {
    void inject(MainActivity mainActivity);
}
