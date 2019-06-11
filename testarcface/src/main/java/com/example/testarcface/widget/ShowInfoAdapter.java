package com.example.testarcface.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testarcface.R;
import com.example.testarcface.model.ItemShowInfo;

import java.util.List;

public class ShowInfoAdapter extends RecyclerView.Adapter<ShowInfoAdapter.ShowInfoHolder> {

    private List<ItemShowInfo> showInfoList;
    private LayoutInflater inflater;

    public ShowInfoAdapter(List<ItemShowInfo> showInfoList, Context context) {
        this.showInfoList = showInfoList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShowInfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.item_show_info, viewGroup, false);
        ImageView ivHeadImage = itemView.findViewById(R.id.iv_item_head_img);
        TextView tvNotification = itemView.findViewById(R.id.tv_item_notification);
        ShowInfoHolder holder = new ShowInfoHolder(itemView);
        holder.ivHeadImage = ivHeadImage;
        holder.tvNotification = tvNotification;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowInfoHolder showInfoHolder, int i) {
        showInfoHolder.tvNotification.setText(showInfoList.get(i).toString());
        Glide.with(showInfoHolder.ivHeadImage.getContext())
                .load(showInfoList.get(i).getBitmap())
                .into(showInfoHolder.ivHeadImage);
    }

    @Override
    public int getItemCount() {
        return showInfoList==null?0:showInfoList.size();
    }

    class ShowInfoHolder extends RecyclerView.ViewHolder{
        ImageView ivHeadImage;
        TextView tvNotification;
        ShowInfoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
