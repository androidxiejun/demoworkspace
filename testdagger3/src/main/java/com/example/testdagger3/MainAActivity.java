package com.example.testdagger3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAActivity extends AppCompatActivity {
    public static final String TAG=MainAActivity.class.getSimpleName().toString();
    @BindView(R.id.btn)
    Button mBtn;
    @Inject
    Student student1;
    @Inject
    Student student2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inject();
    }

    private void inject() {
//        DaggerMainAComponet.builder()
//                .mainAModule(new MainAModule(this))
//                .build()
//                .inject(this);
    }

    @OnClick(R.id.btn)
    public void onViewClick(){
        Log.i(TAG,"student1----------"+student1.toString());
        Log.i(TAG,"student2----------"+student2.toString());
    }
}
