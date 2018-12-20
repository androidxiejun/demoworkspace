package com.example.testdagger3;

import dagger.Component;

/**
 * Created by AndroidXJ on 2018/12/18.
 */

@Component(modules = MainAModule.class)
public interface MainAComponet {
     void inject(MainAActivity mainActivity);
}
