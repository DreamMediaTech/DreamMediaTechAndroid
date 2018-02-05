package com.example.admin.dreammediatechapp.UI.MediaPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.ContentPagerAdapter;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.Entities.VideoijkBean;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.InformationPage.IMServiceFragment;
import com.example.admin.dreammediatechapp.UI.LoginPage.UserLoginActivity;
import com.example.admin.dreammediatechapp.Utils.MediaUtils;
import com.example.admin.dreammediatechapp.media.PlayStateParams;
import com.example.admin.dreammediatechapp.media.PlayerView;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.util.WeakHandler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HPlayerActivity extends AppCompatActivity implements VideoCommentFragment.OnFragmentInteractionListener,VideoDetailragment.OnFragmentInteractionListener{

    private PlayerView player;
    private Context mContext;
    private List<VideoijkBean> list;
    private PowerManager.WakeLock wakeLock;
    View rootView;
    private String url,url2,url3,url4,quota;
    private TabLayout tab;
    private ViewPager viewPager;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
    private FloatingActionButton floatingActionButton;
    private  int vId,uId;
    private  Video video;
    private  boolean playAble=false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_h, null);
        setContentView(rootView);
            Bundle bundle = this.getIntent().getExtras();
            vId = bundle.getInt("vId");


        GetVideo();
        CheckLoginState();
        CheckUser(uId,vId);
        PlayVideo(uId,vId);



        /**虚拟按键的隐藏方法*/
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //比较Activity根布局与当前布局的大小
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 100) {
                    //大小超过100时，一般为显示虚拟键盘事件
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                } else {
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                }
            }
        });

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.hide();
        }


        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();







        floatingActionButton= findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (uId==0){
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),UserLoginActivity.class));
                }
                else if (playAble){

                    player.forbidTouch(false).hideCenterPlayer(false).startPlay();
                    floatingActionButton.setVisibility(View.GONE);

                }
                else {
                    Toast.makeText(getApplicationContext(),"您还未购买此视频",Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    //监听标题栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, false);
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getActionBar().hide();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {

            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void GetVideo(){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://192.168.1.100:8080/Dream/mobileVideoController/getVideoById.action?vid="+vId;

                    Log.d("HPA",sendUrl2);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl2).build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d("HDA","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("HDA","获取返回信息"+je.getAsJsonObject().get("data"));
                           // JsonData(je.getAsJsonObject().get("data"));
                            //videoList=JsonData(je.getAsJsonObject().get("data"));
                            video=JsonData(je.getAsJsonObject().get("data"));
                            List<VideoijkBean> list = PlaySetting(video);
                            UIThread(video);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(){
            public void run(){
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }.start();
    }
    private Video JsonData(JsonElement data){
        Gson gson = new Gson();
        Type type = new TypeToken<Video>(){}.getType();
        Video video = gson.fromJson(data,type);

            Log.d("HDA",video.getvTitle());
            Log.d("HDA",video.getvIntroduce());


        return video ;
    }
    private void UIThread(final Video video){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list =new ArrayList<VideoijkBean>();
                VideoijkBean m1 = new VideoijkBean();
                m1.setStream("标清");
                m1.setUrl(video.getvAddress_sd());
                Log.d("HDA","播放地址"+m1.getUrl());



                VideoijkBean m2 = new VideoijkBean();
                m2.setStream("高清");
                m2.setUrl(video.getvAddress_hd());
                Log.d("HDA","播放地址"+m2.getUrl());

                VideoijkBean m3 = new VideoijkBean();
                m3.setStream("超清");
                m3.setUrl(video.getvAddress_ud());
                Log.d("HDA","播放地址"+m3.getUrl());


                VideoijkBean m4 = new VideoijkBean();
                m4.setStream("原画");
                m4.setUrl(video.getvAddress());
                Log.d("HDA","播放地址"+m4.getUrl());

                list.add(m1);
                list.add(m2);
                list.add(m3);
                list.add(m4);



                viewPager=findViewById(R.id.video_viewPager);
                tab=(TabLayout)findViewById(R.id.video_detail);
                tab.setTabMode(TabLayout.MODE_SCROLLABLE);
                tab.setTabTextColors(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary), ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                tab.setSelectedTabIndicatorColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                tab.setupWithViewPager(viewPager);
                tabIndicators = new ArrayList<String>();
                tabFragments = new ArrayList<>();
                String commentTitle="评论";
                tabIndicators.add("视频简介");
                tabIndicators.add(commentTitle);
                tabFragments.add(new VideoDetailragment().newInstance(video,quota,vId,uId));
                tabFragments.add(new VideoCommentFragment());
                viewPager.setAdapter(contentPagerAdapter);

                for (int i=0;i<tabIndicators.size();i++){
                    TabLayout.Tab itemTab = tab.getTabAt(i);
                    if(itemTab!=null){
                        itemTab.setText(tabIndicators.get(i).toString());
                    }
                    tab.getTabAt(0).getText();
                }


                player = new PlayerView(HPlayerActivity.this, rootView) {
                    @Override
                    public PlayerView toggleProcessDurationOrientation() {
                        hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
                    }

                    @Override
                    public PlayerView setPlaySource(List<VideoijkBean> list) {
                        return super.setPlaySource(list);
                    }
                }
                        .setTitle(video.getvTitle())
                        .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                        .setScaleType(PlayStateParams.fillparent)
                        .forbidTouch(true)
                        .hideSteam(false)
                        .hideCenterPlayer(true)
                        .setPlaySource(list);

            }
        });
    }
    public void CheckLoginState(){
        SharedPreferences sharedPreferences=getApplication().getSharedPreferences("LoginState",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("name",null);
        String userType = sharedPreferences.getString("type",null);
        int uIdint=sharedPreferences.getInt("uId",0);
        if (username!=null){
            uId=uIdint;
        }
    }
    private class ContentPagerAdapter extends FragmentPagerAdapter {
        public ContentPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }
    }
    public List<VideoijkBean> PlaySetting(Video video){



        return list;
    }

    private void CheckUser(final int uid,final int vid){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://192.168.1.100:8080/Dream/mobileVideoController/getUserVideoQuota.action?vid="+vid+"&uid="+uid;
                    Log.d("HPA",sendUrl2);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl2).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d("HDA","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("HDA","获取返回信息"+je.getAsJsonObject().get("data"));
                            quota = je.getAsJsonObject().get("data").toString();
                            Log.d("HDA","获取返回信息"+quota);
                            if (!quota.equals("0")){
                                playAble=true;
                            }



                            // JsonData(je.getAsJsonObject().get("data"));
                            //videoList=JsonData(je.getAsJsonObject().get("data"));

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(){
            public void run(){
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }.start();
    }

    private void PlayVideo(final int uid,final int vid){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://192.168.1.100:8080/Dream/mobileVideoController/userStartVideo.action?vid="+vid+"&uid="+uid;

                    Log.d("HPA",sendUrl2);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl2).build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d("HDA","44"+je.getAsJsonObject().get("status"));
                            Log.d("HDA","44"+je.getAsJsonObject().get("data"));


                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(){
            public void run(){
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }.start();
    }


}
