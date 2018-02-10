package com.example.admin.dreammediatechapp.UI.GiftPage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.IntegralPackage;
import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.R;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GiftDetailActivity extends AppCompatActivity {
    private int pId;
    private String TAG = "GDA";
    private TextView gift_detail_purchase,gift_detail_reward,gift_detail_vip,gift_detail_share;
    private Button gift_detail_buy;
    private IntegralPackage integralPackage ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);
        pId = getIntent().getIntExtra("pId",0);
        Log.d(TAG, "onCreate: "+pId);

        gift_detail_buy=findViewById(R.id.gift_detail_buy);
        gift_detail_purchase=findViewById(R.id.gift_detail_purchase);
        gift_detail_reward=findViewById(R.id.gift_detail_reward);
        gift_detail_share=findViewById(R.id.gift_detail_share);
        gift_detail_vip=findViewById(R.id.gift_detail_vip);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("购买礼包");
        }

        GetGiftList();
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
    private void GetGiftList(){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl = "http://119.29.114.73/Dream/mobilePackageController/getPackageByid.action?pid="+pId;
                    Log.d("CDA",sendUrl);
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
                            integralPackage=JsonData(je.getAsJsonObject().get("data"));
                            //UIThread(JsonData(je.getAsJsonObject().get("data")));
                            UIThread(integralPackage);
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
    private IntegralPackage JsonData(JsonElement data){
        Gson gson = new Gson();
        Type type = new TypeToken<IntegralPackage>(){}.getType();
        IntegralPackage integralPackage = gson.fromJson(data,type);
        return integralPackage;
    }
    private  void UIThread(final IntegralPackage integralPackage){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gift_detail_purchase.setText("消费积分："+String.valueOf(integralPackage.getConsumptionIntegral()));
                gift_detail_reward.setText("奖励积分："+String.valueOf(integralPackage.getBonusIntegral()));
                gift_detail_share.setText("分享积分："+String.valueOf(integralPackage.getSharingIntegral()));
                gift_detail_vip.setText("VIP天数："+String.valueOf(integralPackage.getSuperQuota()));

            }
        });
    }



}
