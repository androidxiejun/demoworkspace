package com.example.testdagger3;

import dagger.Component;

/**
 * Created by AndroidXJ on 2018/12/18.
 */
@Component(modules = MainBModule.class)
public interface MainBComponet {
     void inject(MainBActivity mainActivity);
}
