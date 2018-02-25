package com.example.admin.dreammediatechapp.Adapter;

import android.icu.util.ValueIterator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.R;

import java.util.List;

/**
 * Created by Admin on 2018/1/31.
 */

public class VideoListAdapter extends AbsRecyclerViewAdapter {
    private  List<Video> videoList;

    public VideoListAdapter(RecyclerView recyclerView, List<Video> videoList) {
        super(recyclerView);
        this.videoList=videoList;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.video_list_layout,parent,false));
    }
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Video video = videoList.get(position);
            ((ItemViewHolder) holder).mVideo=video;
            Glide.with(getContext()).load(video.getvTopAddress()).into(((ItemViewHolder) holder).video_cover);
            //((ItemViewHolder) holder).video_cover.setImageResource(R.mipmap.banner2);
            ((ItemViewHolder) holder).video_title.setText(video.getvTitle());
            ((ItemViewHolder) holder).video_owner.setText(video.getAuthor().getuName());
            ((ItemViewHolder) holder).video_categories.setText(video.getFirstType().getVtName());
        }
        super.onBindViewHolder(holder, position);
    }

    public void addData(List<Video> list){
        this.videoList=list;

    }

    public void clearData(){
        videoList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return videoList.size();
    }
    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder{

        private Video mVideo;
        private ImageView video_cover;
        private TextView video_title,video_owner,video_categories,video_watch;
        public ItemViewHolder(View itemView) {
            super(itemView);
            video_cover=(ImageView)itemView.findViewById(R.id.video_list_cover);
            video_title=(TextView)itemView.findViewById(R.id.video_list_title);
            video_owner=(TextView)itemView.findViewById(R.id.video_list_owner);
            video_categories=(TextView)itemView.findViewById(R.id.video_list_categories);
            video_watch=(TextView)itemView.findViewById(R.id.video_list_watch);
        }
    }
}
