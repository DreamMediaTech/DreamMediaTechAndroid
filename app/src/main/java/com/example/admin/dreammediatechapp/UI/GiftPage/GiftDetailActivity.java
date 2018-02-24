package com.example.admin.dreammediatechapp.UI.GiftPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.admin.dreammediatechapp.ALIPAY.PayResult;
import com.example.admin.dreammediatechapp.Entities.AlipayOrder;
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
import java.util.Map;

public class GiftDetailActivity extends AppCompatActivity {
    private int pId,uId;
    private String TAG = "GDA";
    private TextView gift_detail_purchase,gift_detail_reward,gift_detail_vip,gift_detail_share;
    private Button gift_detail_buy;
    private IntegralPackage integralPackage ;
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);
        SharedPreferences sharedPreferences=this.getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        uId = sharedPreferences.getInt("uId",0);
        pId = getIntent().getIntExtra("pId",0);
        Log.d(TAG, "onCreate: "+pId);

        gift_detail_buy=findViewById(R.id.gift_detail_buy);
        gift_detail_purchase=findViewById(R.id.gift_detail_purchase);
        gift_detail_reward=findViewById(R.id.gift_detail_reward);
        gift_detail_share=findViewById(R.id.gift_detail_share);
        gift_detail_vip=findViewById(R.id.gift_detail_vip);

        gift_detail_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetOrderInfo();
            }
        });


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

    private void GetOrderInfo() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String sendUrl = "http://119.29.114.73/Dream/mobilePackageController/CreateAliPayOrder.action?uid=" + uId + "&pid=" + pId;
                    Log.d(TAG, sendUrl);
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
                            Log.d(TAG, "获取返回码" + je.getAsJsonObject().get("status"));
                            Log.d(TAG, "获取返回信息" + je.getAsJsonObject().get("data"));
                            AlipayOrder alipayOrder=JsonData2(je.getAsJsonObject().get("data"));
                            PayTask alipay = new PayTask(GiftDetailActivity.this);
                            Map<String,String> result2 = alipay.payV2(alipayOrder.getOrderInfo(),true);
                                             Log.i(TAG, result2.toString());

                               Message msg = new Message();
                               msg.what = SDK_PAY_FLAG;
                                 msg.obj = result2;
                                     mHandler.sendMessage(msg);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread() {
            public void run() {
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }.start();

    }
    private AlipayOrder JsonData2(JsonElement data){
        Gson gson = new Gson();
        Type type = new TypeToken<AlipayOrder>(){}.getType();
        AlipayOrder alipayOrder = gson.fromJson(data,type);
        return alipayOrder ;
    }

}
