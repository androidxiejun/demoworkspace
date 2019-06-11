package com.example.testlivedata.viewmodule;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by AndroidXJ on 2019/6/3.
 */

public class UserViewModel extends ViewModel {
    private MutableLiveData<String> mName;
    private MutableLiveData<Integer> mAge;

    public MutableLiveData<String> getName() {
        if (mName == null) {
            mName = new MutableLiveData<>();
        }
        return mName;
    }

    public MutableLiveData<Integer> getAge() {
        if (mAge == null) {
            mAge = new MutableLiveData<>();
        }
        return mAge;
    }
}
