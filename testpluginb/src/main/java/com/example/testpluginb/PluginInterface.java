package com.example.testpluginb;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by AndroidXJ on 2018/12/10.
 */

public interface PluginInterface {
    void onCreate(Bundle saveInstance);
    void attachContext(FragmentActivity context);

    void onStart();

    void onResume();

    void onRestart();

    void onDestroy();

    void onStop();

    void onPause();
}
