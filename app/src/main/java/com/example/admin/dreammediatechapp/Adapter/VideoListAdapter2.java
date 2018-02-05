package com.example.admin.dreammediatechapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.Comment;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.CircleImageView;

/**
 * Created by Admin on 2018/2/1.
 */

public class VideoListAdapter2 extends ListBaseAdapter<Video> {
    public VideoListAdapter2(Context context) {

        super(context);

    }
    int cover[]={R.drawable.video_cover1,R.drawable.video_cover2,R.drawable.video_cover3,R.drawable.video_cover4,R.drawable.video_cover5,R.drawable.video_cover6};
    @Override
    public int getLayoutId() {
        return R.layout.video_list_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        Video video = mDataList.get(position);

        ImageView video_cover=holder.getView(R.id.video_list_cover);
        TextView video_title=holder.getView(R.id.video_list_title);
        TextView video_owner=holder.getView(R.id.video_list_owner);
        TextView video_categories=holder.getView(R.id.video_list_categories);
        TextView video_watch=holder.getView(R.id.video_list_watch);

        video_cover.setImageResource(cover[position]);
        video_title.setText(video.getvTitle());
        video_owner.setText(video.getAuthor().getuNickName());
        video_categories.setText(video.getFirstType().getVtName());
        video_watch.setText(String.valueOf(video.getvNum()));


    }


    @Override
    public void onViewRecycled(SuperViewHolder holder) {
        super.onViewRecycled(holder);
    }
//    private class ViewHolder extends RecyclerView.ViewHolder{
//        private CircleImageView circleImageView;
//        private TextView commentUsername,commentTime,commentContent;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            circleImageView = itemView.findViewById(R.id.comment_userimage);
//            commentUsername = itemView.findViewById(R.id.comment_username);
//            commentTime = itemView.findViewById(R.id.comment_time);
//            commentContent = itemView.findViewById(R.id.comment_content);
//        }
//    }
}
