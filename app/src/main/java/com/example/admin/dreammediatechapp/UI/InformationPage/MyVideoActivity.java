package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.VideoListAdapter2;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.MediaPage.HPlayerActivity;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyVideoActivity extends AppCompatActivity {
    String TAG="MV";
    /**服务器端一共多少条数据*/
    private int TOTAL_COUNTER =10000;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private LRecyclerView mRecyclerView = null;

    private VideoListAdapter2 videoListAdapter2 = null;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    private List<Video> videoList = new ArrayList<>();

    private int uId ;

    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case -1:

                    int currentSize = videoListAdapter2.getItemCount();


                    if (currentSize>=TOTAL_COUNTER){
                        mRecyclerView.refreshComplete(REQUEST_COUNT);
                        break;
                    }
                    addItems(videoList);

                    mRecyclerView.refreshComplete(REQUEST_COUNT);

                    break;
                case -3:
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                    mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            GetVideoList();
                        }
                    });

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        SharedPreferences sharedPreferences=this.getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        uId = sharedPreferences.getInt("uId",0);

        mRecyclerView = (LRecyclerView) findViewById(R.id.my_video_list);
        videoListAdapter2 = new VideoListAdapter2(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(videoListAdapter2);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                videoListAdapter2.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                GetVideoList();

            }
        });
        //是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
        mRecyclerView.setLoadMoreEnabled(true);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "我是有底线的", "网络不给力啊，点击再试一次吧");
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    GetVideoList();
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {

            @Override
            public void onScrollUp() {
            }

            @Override
            public void onScrollDown() {
            }


            @Override
            public void onScrolled(int distanceX, int distanceY) {
            }

            @Override
            public void onScrollStateChanged(int state) {

            }

        });
        mRecyclerView.refresh();

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (videoListAdapter2.getDataList().size() > position) {
                    Intent intent = new Intent(getApplicationContext(), HPlayerActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("vId", videoList.get(position).getvId());
                    intent.putExtras(b);

                    startActivity(intent);

                }

            }

        });

        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("上传历史");
    }
    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<Video> list) {
        videoListAdapter2.addAll(list);
        mCurrentCounter += list.size();

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
    private void GetVideoList(){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl ="http://119.29.114.73/Dream/mobileVideoController/getOwnVideoAndProfit.action?uid="+uId;
                    Log.d(TAG,sendUrl);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl).build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d(TAG,"获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d(TAG,"获取返回信息"+je.getAsJsonObject().get("data"));
                            //JsonData(je.getAsJsonObject().get("data"));
                            videoList=JsonData(je.getAsJsonObject().get("data"));
                            TOTAL_COUNTER = videoList.size();
                            Log.d(TAG,"获取返回信息"+String.valueOf(TOTAL_COUNTER));
                            Message msg=new Message();
                            msg.what=-1;
//                            msg.setData(bundle);
                            mHandler.sendMessage(msg);

                            UIThread(JsonData(je.getAsJsonObject().get("data")));
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

    private void UIThread(final List<Video> videoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (videoList.size()==0){
                    Toast.makeText(getApplicationContext(),"您还没上传任何视频",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private List<Video> JsonData(JsonElement data){
        Gson gson = new Gson();
        List<Video> videoList = gson.fromJson(data,new TypeToken<List<Video>>(){}.getType());
        return videoList ;
    }

}
