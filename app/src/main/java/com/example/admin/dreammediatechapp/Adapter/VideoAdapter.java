package com.example.admin.dreammediatechapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2018/1/26.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    private Context context;
    private List<Video> videoList = new ArrayList<Video>();

    public VideoAdapter(Context context,List<Video> list){
        this.context=context;
        this.videoList=list;
    }
    public void setData(List<Video> list){
        if (list!=null&&list.size()>0){
            videoList.addAll(list);
        }
    }
    @Override
    public VideoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new VideoHolder(inflater,viewGroup);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {



        Video video = videoList.get(position);
        holder.mVideo=video;
        holder.video_cover.setImageResource(R.mipmap.banner2);
        holder.video_title.setText(video.getvTitle());
        holder.video_owner.setText(video.getAuthor().getuName());
        holder.video_categories.setText(video.getFirstType().getVtName());

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private Video mVideo;
        private ImageView video_cover;
        private TextView video_title,video_owner,video_categories,video_watch;



        public VideoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.video_list_layout,parent,false));
            video_cover=(ImageView)itemView.findViewById(R.id.video_list_cover);
            video_title=(TextView)itemView.findViewById(R.id.video_list_title);
            video_owner=(TextView)itemView.findViewById(R.id.video_list_owner);
            video_categories=(TextView)itemView.findViewById(R.id.video_list_categories);
            video_watch=(TextView)itemView.findViewById(R.id.video_list_watch);

        }
    }
    private void initData(){
        for (int i = 1;i<10;i++){
            Video video=new Video();

            User user=new User();
            user.setuName("第"+i+"个作者");
            video.setAuthor(user);
            video.setvTitle("第"+i+"个视频");
            video.setvNum(100);
            VideoType videoType=new VideoType();
            videoType.setVtName("分类一");
            video.setFirstType(videoType);
            video.setvNum(111*i);
            videoList.add(video);
        }
    }
}
