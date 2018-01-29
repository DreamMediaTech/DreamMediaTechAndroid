package com.example.admin.dreammediatechapp.UI.MediaPage;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVidSource;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.Utils.ScreenStatusController;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerActivity extends AppCompatActivity {
    private AliyunVodPlayerView mAliyunVodPlayerView = null;
    private ScreenStatusController mScreenStatusController = null;
    private boolean isStrangePhone() {
        boolean strangePhone = Build.DEVICE.equalsIgnoreCase("mx5")
                || Build.DEVICE.equalsIgnoreCase("Redmi Note2")
                || Build.DEVICE.equalsIgnoreCase("Z00A_1")
                || Build.DEVICE.equalsIgnoreCase("hwH60-L02")
                || Build.DEVICE.equalsIgnoreCase("hermes")
                || (Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"));

        return strangePhone;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mAliyunVodPlayerView = (AliyunVodPlayerView)findViewById(R.id.ali_video_view);
        mAliyunVodPlayerView.setKeepScreenOn(true);

        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        mAliyunVodPlayerView.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        mAliyunVodPlayerView.setCirclePlay(false);

        mAliyunVodPlayerView.setOnPreparedListener(new MyPrepareListener(this));
        mAliyunVodPlayerView.setOnCompletionListener(new MyCompletionListener(this));
        mAliyunVodPlayerView.setOnFirstFrameStartListener(new MyFrameInfoListener(this));
        mAliyunVodPlayerView.setOnChangeQualityListener(new MyChangeQualityListener(this));
        mAliyunVodPlayerView.setOnStoppedListner(new MyStoppedListener(this));
        mAliyunVodPlayerView.enableNativeLog();

        setPlaySource();


        mScreenStatusController = new ScreenStatusController(this);
        mScreenStatusController.setScreenStatusListener(new ScreenStatusController.ScreenStatusListener() {
            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {

            }
        });
        mScreenStatusController.startListen();
    }
    private static class MyPrepareListener implements IAliyunVodPlayer.OnPreparedListener {

        private WeakReference<PlayerActivity> activityWeakReference;

        public MyPrepareListener(PlayerActivity playerActivityy) {
            activityWeakReference = new WeakReference<PlayerActivity>(playerActivityy);
        }

        @Override
        public void onPrepared() {
           PlayerActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onPrepared();
            }
        }
    }

    private void onPrepared() {

        Toast.makeText(PlayerActivity.this.getApplicationContext(), "加载成功", Toast.LENGTH_SHORT).show();
    }

    private static class MyCompletionListener implements IAliyunVodPlayer.OnCompletionListener {

        private WeakReference<PlayerActivity> activityWeakReference;

        public MyCompletionListener(PlayerActivity playerActivity) {
            activityWeakReference = new WeakReference<PlayerActivity>(playerActivity);
        }


        @Override
        public void onCompletion() {

            PlayerActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onCompletion();
            }
        }
    }

    private void onCompletion() {
        Toast.makeText(PlayerActivity.this.getApplicationContext(), "加载完成", Toast.LENGTH_SHORT).show();

    }

    private static class MyFrameInfoListener implements IAliyunVodPlayer.OnFirstFrameStartListener {

        private WeakReference<PlayerActivity> activityWeakReference;

        public MyFrameInfoListener(PlayerActivity playerActivity) {
            activityWeakReference = new WeakReference<PlayerActivity>(playerActivity);
        }

        @Override
        public void onFirstFrameStart() {

            PlayerActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onFirstFrameStart();
            }
        }
    }

    private void onFirstFrameStart() {
        Map<String, String> debugInfo = mAliyunVodPlayerView.getAllDebugInfo();
        long createPts = 0;
        if (debugInfo.get("create_player") != null) {
            String time = debugInfo.get("create_player");
            createPts = (long) Double.parseDouble(time);

        }
        if (debugInfo.get("open-url") != null) {
            String time = debugInfo.get("open-url");
            long openPts = (long) Double.parseDouble(time) + createPts;
        }
        if (debugInfo.get("find-stream") != null) {
            String time = debugInfo.get("find-stream");
            long findPts = (long) Double.parseDouble(time) + createPts;
        }
        if (debugInfo.get("open-stream") != null) {
            String time = debugInfo.get("open-stream");
            long openPts = (long) Double.parseDouble(time) + createPts;
        }
    }

    private static class MyChangeQualityListener implements IAliyunVodPlayer.OnChangeQualityListener {

        private WeakReference<PlayerActivity> activityWeakReference;

        public MyChangeQualityListener(PlayerActivity playerActivity) {
            activityWeakReference = new WeakReference<PlayerActivity>(playerActivity);
        }

        @Override
        public void onChangeQualitySuccess(String finalQuality) {

            PlayerActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onChangeQualitySuccess(finalQuality);
            }
        }

        @Override
        public void onChangeQualityFail(int code, String msg) {
            PlayerActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onChangeQualityFail(code, msg);
            }
        }
    }

    private void onChangeQualitySuccess(String finalQuality) {

       // Toast.makeText(SkinActivity.this.getApplicationContext(), getString(R.string.log_change_quality_success), Toast.LENGTH_SHORT).show();
    }

    void onChangeQualityFail(int code, String msg) {

       // Toast.makeText(SkinActivity.this.getApplicationContext(), getString(R.string.log_change_quality_fail), Toast.LENGTH_SHORT).show();
    }

    private static class MyStoppedListener implements IAliyunVodPlayer.OnStoppedListener {

        private WeakReference<PlayerActivity> activityWeakReference;

        public MyStoppedListener(PlayerActivity playerActivity) {
            activityWeakReference = new WeakReference<PlayerActivity>(playerActivity);
        }

        @Override
        public void onStopped() {

           PlayerActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onStopped();
            }
        }
    }

    private void onStopped() {
       // Toast.makeText(SkinActivity.this.getApplicationContext(), R.string.log_play_stopped, Toast.LENGTH_SHORT).show();
    }

    private void setPlaySource() {

//
//        String type = getIntent().getStringExtra("type");
//        if (TextUtils.isEmpty(type)) {
//            return;
//        }
//
//        if (type.equals("localSource")) {
//
//            AliyunLocalSource.AliyunLocalSourceBuilder alsb = new AliyunLocalSource.AliyunLocalSourceBuilder();
//            alsb.setSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
//            AliyunLocalSource localSource = alsb.build();
//            mAliyunVodPlayerView.setLocalSource(localSource);
//
//        } else if (type.equals("vidsts")) {
//
//            String vid = getIntent().getStringExtra("vid");
//            String akId = getIntent().getStringExtra("akId");
//            String akSecret = getIntent().getStringExtra("akSecret");
//            String scuToken = getIntent().getStringExtra("scuToken");
//
//            AliyunVidSts vidSts = new AliyunVidSts();
//            vidSts.setVid(vid);
//            vidSts.setAcId(akId);
//            vidSts.setAkSceret(akSecret);
//            vidSts.setSecurityToken(scuToken);
//            mAliyunVodPlayerView.setVidSts(vidSts);
//        }
        AliyunLocalSource.AliyunLocalSourceBuilder alsb=new AliyunLocalSource.AliyunLocalSourceBuilder();
        alsb.setSource("http://player.alicdn.com/video/aliyunmedia.mp4");
        AliyunLocalSource localSource = alsb.build();
        mAliyunVodPlayerView.setLocalSource(localSource);

    }

    @Override
    protected void onResume() {
        super.onResume();

        updatePlayerViewMode();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("lfj1019", " orientation = " + getResources().getConfiguration().orientation);
        updatePlayerViewMode();
    }

    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
                //显示状态栏
//                if (!isStrangePhone()) {
//                    getSupportActionBar().show();
//                }

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                if (!isStrangePhone()) {
//                    aliVcVideoViewLayoutParams.topMargin = getSupportActionBar().getHeight();
//                }

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                if (!isStrangePhone()) {
//                    aliVcVideoViewLayoutParams.topMargin = 0;
//                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }
        mScreenStatusController.stopListen();

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAliyunVodPlayerView != null) {
            boolean handler = mAliyunVodPlayerView.onKeyDown(keyCode, event);
            if (!handler) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //解决某些手机上锁屏之后会出现标题栏的问题。
        updatePlayerViewMode();
    }
}
