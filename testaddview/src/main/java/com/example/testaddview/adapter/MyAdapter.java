package com.example.testaddview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.testaddview.R;
import com.example.testaddview.activity.MainActivity;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by AndroidXJ on 2019/4/19.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> dataList;
    private LayoutInflater mLayoutInflater;
    private int TYPE_FOOT = 1002;

    public MyAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.dataList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            View footView = mLayoutInflater.inflate(R.layout.footer_layout, parent, false);
            return new FootViewHolder(footView);
        }
        View view = mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder && position < dataList.size()) {
            Log.i(MainActivity.TAG, "position-----------" + position);
            ((ViewHolder) holder).mItemBtn.setText(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= dataList.size()) {
            return TYPE_FOOT;
        } else {
            return super.getItemViewType(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button mItemBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemBtn = itemView.findViewById(R.id.btn_item);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {
        Button mFooterBtn;

        public FootViewHolder(View itemView) {
            super(itemView);
            mFooterBtn = itemView.findViewById(R.id.footer_btn);
        }
    }
}
