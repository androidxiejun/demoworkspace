package com.example.testarouter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.BaseAdapter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.testarouter.R;
import com.example.testarouter.common.ARouterConstants;
import com.example.testarouter.util.ARouterUtils;

/**
 * Created by AndroidXJ on 2019/4/18.
 */
@Route(path = ARouterConstants.COM_ACTIVITY2)
public class SecondActivity extends BaseActivity {
    @Autowired
    String key;
    @Autowired
    int key2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, ARouterUtils.getFragment(ARouterConstants.COM_Fragment1))
                .commit();
    }
}
