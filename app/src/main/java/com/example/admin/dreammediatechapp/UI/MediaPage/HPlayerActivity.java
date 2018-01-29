package com.example.admin.dreammediatechapp.UI.MediaPage;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.ContentPagerAdapter;
import com.example.admin.dreammediatechapp.Entities.VideoijkBean;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.Utils.MediaUtils;
import com.example.admin.dreammediatechapp.media.PlayStateParams;
import com.example.admin.dreammediatechapp.media.PlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * ========================================
 * <p/>
 * 版 权：深圳市晶网科技控股有限公司 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2015/11/18 9:40
 * <p/>
 * 描 述：半屏界面
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class HPlayerActivity extends AppCompatActivity {

    private PlayerView player;
    private Context mContext;
    private List<VideoijkBean> list;
    private PowerManager.WakeLock wakeLock;
    View rootView;
    private String url,url2,url3,url4;
    private TabLayout tab;
    private ViewPager viewPager;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_h, null);
        setContentView(rootView);
        url="http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        url2="http://192.168.1.103:10088/EasyTrans/Data/30a0b2f0ffde11e7813defa4ae4d6b2f/video.m3u8";
        url3="http://player.alicdn.com/video/aliyunmedia.mp4";


        viewPager=findViewById(R.id.video_viewPager);
        tab=(TabLayout)findViewById(R.id.video_detail);
        tab.setTabMode(TabLayout.MODE_FIXED);

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

        }


        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();
       // list = new ArrayList<VideoijkBean>();
        //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
        //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
//        String url1 = getLocalVideoPath("my_video.mp4");
//        if (!new File(url1).exists()) {
//            url1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//        }
//        String url2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
//        VideoijkBean m1 = new VideoijkBean();
//        m1.setStream("标清");
//        m1.setUrl(url1);
//        VideoijkBean m2 = new VideoijkBean();
//        m2.setStream("高清");
//        m2.setUrl(url2);
//        list.add(m1);
//        list.add(m2);


        player = new PlayerView(this, rootView) {
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
                .setTitle("测试播放")
                .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                .setScaleType(PlayStateParams.fillparent)
                .forbidTouch(false)
                .hideSteam(true)
                .hideCenterPlayer(true)
                .setPlaySource(url3)
                .startPlay();




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

    /**
     * 播放本地视频
     */

    private String getLocalVideoPath(String name) {
        String sdCard = Environment.getExternalStorageDirectory().getPath();
        String uri = sdCard + File.separator + name;
        return uri;
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
    }

//    private  void playMenthod(){
//
//    }
//    private void CleanComment(){
//        final Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    String sendComment = comment.getText().toString();
//                    String sendUrl = "http://119.29.114.73/MessageDemo/MessageServlet?method=clean";
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    final Request request = new Request.Builder().url(sendUrl).build();
//                    Call call = okHttpClient.newCall(request);
//                    AfterSend();
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Response response) throws IOException {
//
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(){
//            public void run(){
//                new Handler(Looper.getMainLooper()).post(runnable);
//            }
//        }.start();
//    }
//    private void sendComment(){
//        final Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    String sendComment = comment.getText().toString();
//                    String sendUrl = "http://119.29.114.73/MessageDemo/MessageServlet?method=send&message="+sendComment;
//                    Toast.makeText(getApplicationContext(),"发送成功", Toast.LENGTH_LONG).show();
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    final Request request = new Request.Builder().url(sendUrl).build();
//                    Call call = okHttpClient.newCall(request);
//                    AfterSend();
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Response response) throws IOException {
//
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(){
//            public void run(){
//                new Handler(Looper.getMainLooper()).post(runnable);
//            }
//        }.start();
//    }
//    private void AfterSend(){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                InputMethodManager imm =(InputMethodManager)getSystemService(
//                        Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(comment.getWindowToken(), 0);
//                comment.setText("");
//            }
//        });
//    }
//
//    private void getData() {
//        final Runnable runnable2=new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(1000);
//                    String getUrl="http://119.29.114.73/MessageDemo/MessageServlet?method=getMessage";
//                    //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
//
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    final Request request = new Request.Builder().url(getUrl).get().build();
//                    //得到服务器返回的具体内容
//                    Call call =okHttpClient.newCall(request);
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//
//                        }
//
//
//
//                        @Override
//                        public void onResponse(Response response) throws IOException {
//
//                            String responseData = response.body().string();
//                            parseJSONWithGSON(responseData);
//                            Log.d("VodActivity","jsonData"+responseData);
//                            // showResponse(responseData.toString());
//
//                        }
//                    });
//
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(){
//            public void run(){
//                new Handler(Looper.getMainLooper()).post(runnable2);
//            }
//        }.start();
//
//    }
//    private void parseJSONWithGSON(String jsonData) throws IOException {
//        //使用轻量级的Gson解析得到的json
//        Gson gson = new Gson();
//        appList = gson.fromJson(jsonData, new TypeToken<List<String >>(){}.getType());
//
//        // appList = gson.fromJson(jsonData, new TypeToken<List<CommentEntites>>() {}.getType());
//
//    }
//    private void showResponse(final String response) {
//        //在子线程中更新UI
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // 在这里进行UI操作，将结果显示到界面上
//
//                for (int i =1 ; i<appList.size();i++) {
//                    //控制台输出结果，便于查看
//                    //Log.d("MainActivity", "other" + app.getContent());
//                    Log.d("VodActivity","jsonData"+response);
//                    //appList.add(0,response);
//                    //recyclerView.setAdapter(mRecyclerViewAdapter=new RecyclerViewAdapter(getApplicationContext(),appList));
//                    //mRecyclerViewAdapter.notifyItemInserted(0);
//                    String allStr = "";
//                    for (String str:appList){
//                        allStr = allStr + str;
//                    }
//                    single_text.setText(allStr);
//                    comment_seoll_view.fullScroll(ScrollView.FOCUS_DOWN);
//
//                }
//
//            }
//        });
//    }
//    private void getSingleData() {
//        final Runnable runnable2=new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(1000);
//                    String getUrl="http://119.29.114.73/MessageDemo/MessageServlet?method=getMessage";
//                    //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
//
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    final Request request = new Request.Builder().url(getUrl).get().build();
//                    //得到服务器返回的具体内容
//                    Call call =okHttpClient.newCall(request);
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//
//                        }
//                        @Override
//                        public void onResponse(Response response) throws IOException {
//
//                            String responseData = response.body().string();
//                            parseJSONWithGSON(responseData);
//                            Log.d("VodActivity","jsonData"+responseData);
//                            showResponse(responseData.toString());
//
//
//                        }
//                    });
//
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(){
//            public void run(){
//                new Handler(Looper.getMainLooper()).post(runnable2);
//            }
//        }.start();
//
//    }

}
