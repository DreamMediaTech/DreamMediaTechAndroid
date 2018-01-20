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

public class InformationAdapter extends AbsRecyclerViewAdapter {
    //个人中心菜单标题
    private String[] itemNames = new String[]{
        "个人信息","我的积分","我的推广","我的分享","上传视频","我上传的视频","设置中心","热门提问"
    };

    //个人中心菜单Icon
    private int[] itemIcons = new int[]{
           R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round
    };

    public InformationAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    //
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
            mItemIcon = itemView.findViewById(R.id.item_info_icon);
            mItemText = $ (R.id.item_info_title);
        }
    }
}
