package com.example.testarouter.util;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by AndroidXJ on 2019/4/18.
 */

public class ARouterUtils {
    /**
     * 不带参数的跳转
     * @param path
     */
    public static void turnActivity(String path){
        ARouter.getInstance().build(path).navigation();
    }

    public static Fragment getFragment(String path){
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }
}
