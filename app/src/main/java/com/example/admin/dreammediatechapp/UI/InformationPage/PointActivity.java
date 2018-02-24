package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.Member;
import com.example.admin.dreammediatechapp.Entities.User;
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

public class PointActivity extends AppCompatActivity {
    private TextView point_cash,point_change,buy_gift;
    private TextView purchase_point,rewar_point,share_point;
    private Member member;
    private int uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);

        }
        actionBar.setTitle("我的积分");

        SharedPreferences sharedPreferences=this.getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        uId = sharedPreferences.getInt("uId",0);
        init();
        GetUserPoint(uId);

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

    private void init(){
        point_cash=findViewById(R.id.point_cash);
        point_cash.setOnClickListener(onClickListener);
        point_change=findViewById(R.id.point_change);
        point_change.setOnClickListener(onClickListener);
        buy_gift=findViewById(R.id.buy_gift);
        buy_gift.setOnClickListener(onClickListener);

        purchase_point = findViewById(R.id.purchase_point);
        rewar_point=findViewById(R.id.rewar_point);
        share_point = findViewById(R.id.share_point);

    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.point_cash:
                    startActivity(new Intent(getApplicationContext(),CashActivity.class));
                break;
                case  R.id.point_change:
                    break;
                case R.id.buy_gift:
                    break;

            }
        }
    };
    private void GetUserPoint(final int UID){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://119.29.114.73/Dream/mobileUserController/getIntegral.action?uid="+UID;

                    Log.d("PA",sendUrl2);
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
                            Log.d("PA","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("PA","获取返回信息"+je.getAsJsonObject().get("data"));
                            // JsonData(je.getAsJsonObject().get("data"));
                            //videoList=JsonData(je.getAsJsonObject().get("data"));
                            member=JsonData(je.getAsJsonObject().get("data"));
                            showResponse(member);

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
    private Member JsonData(JsonElement data){
        Gson gson = new Gson();
        Type type = new TypeToken<Member>(){}.getType();
       Member member = gson.fromJson(data,type);
        Log.d("PA",String.valueOf(member.getConsumptionIntegral()));
        Log.d("PA",String.valueOf(member.getSharingIntegral()));
        Log.d("PA",String.valueOf(member.getBonusIntegral()));
        return member ;
    }

    private void showResponse(final Member member){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                purchase_point.setText(String.valueOf(member.getConsumptionIntegral()));
                share_point.setText(String.valueOf(member.getSharingIntegral()));
                rewar_point.setText(String.valueOf(member.getBonusIntegral()));
            }
        });

    }

}
