package com.example.admin.dreammediatechapp.UI.LoginPage;

import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Entities.ResponseCode;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.TimeCount;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

public class RegisterActivity extends AppCompatActivity {
    private String TAG="RegisterActivity";
    private TimeCount timeCount;
    private EditText phoneNum,code,password,comfirmPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("注册");

        SMSSDK.getInstance().initSdk(this);

        phoneNum=findViewById(R.id.register_phone_number);
        code=findViewById(R.id.register_input_code);
        password=findViewById(R.id.register_input_password);
        comfirmPass=findViewById(R.id.register_comfirm_password);

        final Button confirmButtom = (Button)findViewById(R.id.register_comfirm_button);
        confirmButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(phoneNum.getText())){
                    Toast.makeText(getApplicationContext(),"请输入手机号码",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(code.getText())){
                    Toast.makeText(getApplicationContext(),"请输入验证码",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(password.getText())){
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_LONG).show();
                }else if (!password.getText().toString().equals(comfirmPass.getText().toString())){
                    Toast.makeText(getApplicationContext(),"两次输入不一致",Toast.LENGTH_LONG).show();
                }else {
                    SMSSDK.getInstance().checkSmsCodeAsyn(phoneNum.getText().toString(), code.getText().toString(), new SmscheckListener() {
                        @Override
                        public void checkCodeSuccess(String s) {
                            String RegisterUrl="http://192.168.1.107:8080/Dream/mobileUserController/registerAppNewUser.action";
                            RegisterAction(RegisterUrl,phoneNum.getText().toString(),password.getText().toString());
                        }

                        @Override
                        public void checkCodeFail(int i, String s) {
                            Toast.makeText(getApplicationContext(),"登录失败，错误代码为"+i,Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        });

        final Button getCode = (Button)findViewById(R.id.register_get_code);
        timeCount = new TimeCount(30000,1000,getCode);

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(phoneNum.getText())){
                    Toast.makeText(getApplicationContext(),"请输入手机号码",Toast.LENGTH_LONG).show();
                }else{
                    timeCount.start();
                    SMSSDK.getInstance().getSmsCodeAsyn(phoneNum.getText().toString(), "1", new SmscodeListener() {
                        @Override
                        public void getCodeSuccess(String s) {
                            Toast.makeText(getApplicationContext(),"获取验证码成功",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void getCodeFail(int i, String s) {
                            Toast.makeText(getApplicationContext(),"获取验证码失败，错误码为"+i,Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });


    }    //监听标题栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void RegisterAction(final String Registerurl, final String phone, final String password ){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    builder.add("phone",phone).add("password",password);
                    final Request request = new Request.Builder().url(Registerurl).post(builder.build()).build();
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
                    case "3":
                        Toast.makeText(getApplicationContext(),"用户名已存在",Toast.LENGTH_LONG).show();
                        break;
                    case "200":
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }

            }
        });
    }

}
