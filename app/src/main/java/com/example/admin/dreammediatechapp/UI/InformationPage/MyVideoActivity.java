package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.VideoAdapter;
import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyVideoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Video> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
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
        recyclerView=(RecyclerView)findViewById(R.id.my_video_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String zz=videoList.get(0).getvTitle().toString();
        Toast.makeText(this,zz,Toast.LENGTH_LONG).show();
        VideoAdapter videoAdapter = new VideoAdapter(this,videoList);
        recyclerView.setAdapter(videoAdapter);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("上传历史");
    }
    //监听标题栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
