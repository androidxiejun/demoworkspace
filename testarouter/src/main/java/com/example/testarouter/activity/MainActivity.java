package com.example.testarouter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.testarouter.R;
import com.example.testarouter.common.ARouterConstants;

@Route(path =  ARouterConstants.COM_ACTIVITY1)
public class MainActivity extends BaseActivity implements View.OnClickListener{
    public static final String TAG=MainActivity.class.getSimpleName();
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn=findViewById(R.id.btn_turn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ARouter.getInstance().build(ARouterConstants.COM_ACTIVITY2)
                .withString("key","hh")
                .withInt("key2",20)
                .navigation();
    }
}
