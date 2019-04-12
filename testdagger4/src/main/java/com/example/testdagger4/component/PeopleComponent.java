package com.example.testdagger4.component;

import com.example.testdagger4.activity.MainActivity;
import com.example.testdagger4.activity.SecondActivity;
import com.example.testdagger4.app.MyApplication;
import com.example.testdagger4.module.StudentModule;
import com.example.testdagger4.scope.StudentScope;

import dagger.Component;

/**
 * Created by AndroidXJ on 2019/3/20.
 */
@StudentScope
@Component(modules = StudentModule.class,dependencies = AppComponent.class)
public abstract class PeopleComponent {
    public abstract void inject(MainActivity mainActivity);
    public abstract void inject(SecondActivity secondActivity);

    private static PeopleComponent mPeopleComponent;

    public static PeopleComponent getInstance(){
        if(mPeopleComponent==null){
            mPeopleComponent=DaggerPeopleComponent.builder()
                    .appComponent(MyApplication.getInstance().getAppComponent())
                    .build();
        }
        return mPeopleComponent;
    }


}
