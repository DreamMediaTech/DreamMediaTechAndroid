package com.example.admin.dreammediatechapp.UI;

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
import android.widget.Toolbar;

import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.PlayerManager;
import com.example.admin.dreammediatechapp.media.IRenderView;
import com.example.admin.dreammediatechapp.media.IjkVideoView;

public class VideoPlayActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private IjkVideoView videoView;
    private PlayerManager playerManager;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        init();
    }
    public void init(){
        playerManager=new PlayerManager(this);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        constraintLayout=(ConstraintLayout)findViewById(R.id.video_play_layout);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fade fade=new Fade();
                TransitionManager.beginDelayedTransition(constraintLayout,fade);
                floatingActionButton.setVisibility(View.GONE);
                playerManager.setScaleType(PlayerManager.SCALETYPE_FITPARENT);
                playerManager.play("rtmp://119.29.114.73/oflaDemo/guardians2.mp4");
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    /**
     * 监听返回按钮
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}

