package com.example.admin.dreammediatechapp.UI.MediaPage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.PlayerManager;
import com.example.admin.dreammediatechapp.media.IRenderView;
import com.example.admin.dreammediatechapp.media.IjkVideoView;

public class VideoPlayActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private PlayerManager playerManager;
    private FloatingActionButton floatingActionButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("视频播放");


        init();
    }
    public void init(){
        playerManager=new PlayerManager(this);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        constraintLayout=findViewById(R.id.video_play_layout);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fade fade=new Fade();
                TransitionManager.beginDelayedTransition(constraintLayout,fade);
                floatingActionButton.setVisibility(View.GONE);
                playerManager.setScaleType(PlayerManager.SCALETYPE_FITXY);
                String url="rtmp://119.29.114.73/oflaDemo/guardians2.mp4";
                String url2="rtmp://locustec.net/applivetest/live01";
                playerManager.play(url);
            }
        });

    }

    /**
     * 监听标题栏
     * @param item
     * @return
     */

    //监听标题栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                playerManager.stop();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}

