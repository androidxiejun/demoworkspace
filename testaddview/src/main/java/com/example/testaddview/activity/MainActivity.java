package com.example.testaddview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.testaddview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();
    private RelativeLayout mLayout;
    private Button mBtnAdd;
    private Button mBtnCut;
    private int width, height, btnWidth, btnHeight;
    private int index;
    private List<Button> mButtonTemporary = new ArrayList<>();
    private List<Button> mButtonList = new ArrayList<>();
    private int maxNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getPix();
    }

    private void initView() {
        mBtnAdd = findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
        mBtnCut=findViewById(R.id.btn_cut);
        mBtnCut.setOnClickListener(this);
        mLayout = findViewById(R.id.relative_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                createButton(index+++"");
                break;
            case R.id.btn_cut:
                mButtonList.remove(0);
                setButtonLocation(mButtonList);
                break;
        }

    }

    /**
     * 获取屏幕像素宽高
     */
    private void getPix() {
        //通过Resources获取
        DisplayMetrics dm = getResources().getDisplayMetrics();
        height = dm.heightPixels;
        width = dm.widthPixels;
        Log.i(TAG, "宽-----" + width);
        Log.i(TAG, "高-----" + height);
        measureBtn();
    }

    /**
     * 获取加号按钮的宽高
     */
    private void measureBtn() {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mBtnAdd.measure(w, h);
        btnWidth = mBtnAdd.getMeasuredHeight()+mBtnCut.getMeasuredHeight();
        btnHeight = mBtnAdd.getMeasuredWidth()+mBtnCut.getMeasuredWidth();
        maxNum=(width-btnWidth)/310;
    }

    private void createButton(String name){
        Button button=new Button(getApplicationContext());
        button.setText(name);
        mButtonList.add(button);
        addToRelative(button,mButtonList.size()-1);
    }

    /**
     * 当删除某一按钮后重新绘制界面
     * @param btnList
     */
    private void setButtonLocation(List<Button>btnList){
        mLayout.removeAllViews();
        for (int i=0;i<btnList.size();i++){
            addToRelative(btnList.get(i),i);
        }
    }
    private void addToRelative(Button button,int index){
        int widthNum=index%3;
        int heightNum=index/3;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 100);
        params.setMargins(widthNum*310,heightNum*110,0,0);
        mLayout.addView(button,params);
    }
}
