package com.example.admin.dreammediatechapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.R;

/**
 * Created by Admin on 2018/1/25.
 */

public class HomeShortCutAdapter extends AbsRecyclerViewAdapter {
    private String []itemNames  = new String[]{
            "初中物理","初中数学","初中语文","初中英语",
            "高中数学","高中英语","高中生物","高中物理","高中地理","更多推荐"
    };
    private int[] itemIcons  = new int[]{
            R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round
    };

    public HomeShortCutAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_info_layout,parent,false));
    }

    @Override
    public int getItemCount() {
        return itemNames.length;

    }
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof HomeShortCutAdapter.ItemViewHolder) {
            HomeShortCutAdapter.ItemViewHolder itemViewHolder = (HomeShortCutAdapter.ItemViewHolder) holder;
            itemViewHolder.imageView.setImageResource(itemIcons[position]);
            itemViewHolder.textView.setText(itemNames[position]);
        }
        super.onBindViewHolder(holder, position);
    }
    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder{
        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = $ (R.id.item_info_icon);
            textView = $(R.id.item_info_title);
        }
    }
}
