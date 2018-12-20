package com.example.testpluginc;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.testpluginb.PluginInterface;

/**
 * Created by AndroidXJ on 2018/12/10.
 */

public class BaseActivity extends FragmentActivity implements PluginInterface{
    //注意：这里命名为protected，以便于子类使用
    protected FragmentActivity thisContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisContext=this;
    }

    @Override
    public void setContentView(int layoutResID) {
        thisContext.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        thisContext.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        thisContext.setContentView(view, params);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return thisContext.getLayoutInflater();
    }

    @Override
    public Window getWindow() {
        return thisContext.getWindow();
    }

    @Override
    public View findViewById(int id) {
        return thisContext.findViewById(id);
    }

    @Override
    public void attachContext(FragmentActivity context) {
        thisContext = context;
    }

    @Override
    public ClassLoader getClassLoader() {
        return thisContext.getClassLoader();
    }

    @Override
    public WindowManager getWindowManager() {
        return thisContext.getWindowManager();
    }


    @Override
    public ApplicationInfo getApplicationInfo() {
        return thisContext.getApplicationInfo();
    }

    @Override
    public void finish() {
        thisContext.finish();
    }


    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void onBackPressed() {
        thisContext.onBackPressed();
    }

    @Override
    public void startActivity(Intent intent) {
        thisContext.startActivity(intent);
    }
}
