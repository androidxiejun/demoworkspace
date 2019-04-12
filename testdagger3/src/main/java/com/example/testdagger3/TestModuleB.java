package com.example.testdagger3;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AndroidXJ on 2019/3/18.
 */
@Module
public class TestModuleB {
    @Provides
    Master provideMaster(){
        return new Master();
    }
}
