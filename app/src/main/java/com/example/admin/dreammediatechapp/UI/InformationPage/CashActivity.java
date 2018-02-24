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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Entities.Member;
import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

public class CashActivity extends AppCompatActivity {
    private String TAG="CA";

    private  Member member;
    private  int uId;
    private TextView cash_point_avilable;
    private RadioGroup cash_radio_group;
    private RadioButton alipay_radio,wechat_radio;
    private Button cash_button;
    private EditText cash_point_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        SharedPreferences sharedPreferences=this.getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        uId = sharedPreferences.getInt("uId",0);

        cash_button = findViewById(R.id.cash_button);
        cash_point_avilable =findViewById(R.id.cash_point_avilable);
        cash_radio_group = findViewById(R.id.cash_radio_group);
        alipay_radio = findViewById(R.id.alipay_radio);
        wechat_radio = findViewById(R.id.wechat_radio);
        cash_point_input = findViewById(R.id.cash_point_input);

        cash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cashMoney = cash_point_input.getText().toString();

                if (cashMoney!=null){
                    Toast.makeText(getApplicationContext(),cashMoney,Toast.LENGTH_LONG).show();
                    CashAction(String.valueOf(uId),cashMoney);
                }
            }
        });

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("积分提现");
        }

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
                            Log.d(TAG,"获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d(TAG,"获取返回信息"+je.getAsJsonObject().get("data"));
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
        Log.d(TAG,String.valueOf(member.getConsumptionIntegral()));
        Log.d(TAG,String.valueOf(member.getSharingIntegral()));
        Log.d(TAG,String.valueOf(member.getBonusIntegral()));
        return member ;
    }

    private void showResponse(final Member member){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cash_point_avilable.setText("可提现积分  "+String.valueOf(member.getBonusIntegral()));
            }
        });
    }
    private void CashAction(final String uId,final String money ){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    String url2="http://119.29.114.73/Dream/mobileUserController/userWithDrawalsByAlipay.action";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    builder.add("uid", uId).add("money",money);
                    final Request request = new Request.Builder().url(url2).post(builder.build()).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            if (response.isSuccessful()){
                                String result = response.body().string();
                                JsonElement je = new JsonParser().parse(result);
                                Log.d(TAG,"获取返回码"+je.getAsJsonObject().get("status"));
                                Log.d(TAG,"获取返回信息"+je.getAsJsonObject().get("data"));

                                showResponse(je.getAsJsonObject().get("status").toString());
                            }

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
    private  void showResponse(final  String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (response){
                    case "200":
                        Toast.makeText(getApplicationContext(),"提现成功",Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case "404":
                        Toast.makeText(getApplicationContext(),"尚未绑定支付宝账号",Toast.LENGTH_LONG).show();
                        break;
                    case "403":
                        Toast.makeText(getApplicationContext(),"积分不足，请重新输入",Toast.LENGTH_LONG).show();
                        break;
                    case "500":
                        Toast.makeText(getApplicationContext(),"提现失败，发生未知错误",Toast.LENGTH_LONG).show();
                        break;

                }

            }
        });
    }
}
