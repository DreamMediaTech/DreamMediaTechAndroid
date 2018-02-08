package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoijkBean;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.CircleImageView;
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
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {
    private CircleImageView  circleImageView;
    private User user;
    private int uId;
    private TextView user_info_name,user_info_char;
    private EditText user_info_nicname_input, user_info_realname_input, user_info_phone_input, user_info_alipay_input;
    private Button user_info_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("个人信息");
        SharedPreferences sharedPreferences=this.getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        uId = sharedPreferences.getInt("uId",0);

        circleImageView = findViewById(R.id.user_image);
        user_info_name = findViewById(R.id.user_info_name);
        user_info_char = findViewById(R.id.user_info_char);
        user_info_nicname_input = findViewById(R.id.user_info_nicname_input);
        user_info_realname_input = findViewById(R.id.user_info_realname_input);
        user_info_phone_input = findViewById(R.id.user_info_phone_input);
        user_info_alipay_input = findViewById(R.id.user_info_alipay_input);

        GetUserInfo(uId);

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
    private void GetUserInfo(final int UID){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://119.29.114.73/Dream/mobileUserController/getUserInformation.action?uid="+UID;

                    Log.d("UIA",sendUrl2);
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
                            Log.d("UIA","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("UIA","获取返回信息"+je.getAsJsonObject().get("data"));
                            // JsonData(je.getAsJsonObject().get("data"));
                            //videoList=JsonData(je.getAsJsonObject().get("data"));
                            user=JsonData(je.getAsJsonObject().get("data"));
                            showResponse(user);

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
    private User JsonData(JsonElement data){
        Gson gson = new Gson();
        Type type = new TypeToken<User>(){}.getType();
        User user = gson.fromJson(data,type);
        Log.d("UIA",user.getuNickName());
        Log.d("UIA",user.getuType());
        return user ;
    }

    private void showResponse(final User user){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user_info_name.setText(user.getuNickName());
                user_info_char.setText(user.getuType());

                user_info_nicname_input.setText(user.getuNickName());
                if (user.getuName()!=null){
                    user_info_realname_input.setText(user.getuName());
                }

            }
        });

    }

}
