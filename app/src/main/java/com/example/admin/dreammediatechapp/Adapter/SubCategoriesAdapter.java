package com.example.admin.dreammediatechapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;

import java.util.List;

/**
 * Created by Admin on 2018/1/25.
 */

public class SubCategoriesAdapter extends AbsRecyclerViewAdapter {

    private List<VideoType> subTypeList;
    public SubCategoriesAdapter(RecyclerView recyclerView ,List<VideoType> list) {
        super(recyclerView);
        this.subTypeList= list;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_sub_categories,parent,false));
    }

    @Override
    public int getItemCount() {
        return subTypeList.size();

    }
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof SubCategoriesAdapter.ItemViewHolder) {
            SubCategoriesAdapter.ItemViewHolder itemViewHolder = (SubCategoriesAdapter.ItemViewHolder) holder;
            Glide.with(getContext()).load(subTypeList.get(position).getVtImage()).into(itemViewHolder.imageView);
            //itemViewHolder.imageView.setImageResource(R.mipmap.ic_launcher_round);
            itemViewHolder.textView.setText(subTypeList.get(position).getVtName());
        }
        super.onBindViewHolder(holder, position);
    }
    private class ItemViewHolder extends ClickableViewHolder{
        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = $ (R.id.item_sub_categories_icon);
            textView = $(R.id.item_sub_categories_title);
        }
    }
}
