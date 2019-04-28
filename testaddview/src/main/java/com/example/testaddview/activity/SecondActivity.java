package com.example.testaddview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.testaddview.R;
import com.example.testaddview.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndroidXJ on 2019/4/19.
 */

public class SecondActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<String>dataList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();
        initAdapter();
        initView();
    }

    private void initData() {
        for (int i=0;i<11;i++){
           dataList.add("data"+i);
        }
    }

    private void initAdapter() {
        mAdapter=new MyAdapter(getApplicationContext(),dataList);
    }

    private void initView() {
        mRecyclerView=findViewById(R.id.list_view);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }
}
