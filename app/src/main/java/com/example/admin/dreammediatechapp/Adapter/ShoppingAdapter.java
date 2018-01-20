package com.example.admin.dreammediatechapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.R;

/**
 * Created by Admin on 2018/1/20.
 */

public class ShoppingAdapter extends AbsRecyclerViewAdapter {
    private String[] itemNames = new String[]{
            "进入商城","我的订单"
    };
    private  int[] itemIcons = new int[]{
            R.mipmap.ic_launcher,R.mipmap.ic_launcher
    };

    public ShoppingAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_info_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mItemIcon.setImageResource(itemIcons[position]);
            itemViewHolder.mItemText.setText(itemNames[position]);
        }
        super.onBindViewHolder(holder, position);
    }
    @Override
    public int getItemCount() {
        return itemIcons.length;
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {
        ImageView mItemIcon;
        TextView mItemText;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemIcon = $ (R.id.item_info_icon);
            mItemText = $(R.id.item_info_title);
        }
    }
}
