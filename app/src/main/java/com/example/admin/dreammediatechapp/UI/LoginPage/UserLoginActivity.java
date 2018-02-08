package com.example.admin.dreammediatechapp.UI.LoginPage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

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

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class UserLoginActivity extends AppCompatActivity{
    private String FILE = "LoginState";
    private final static String USERNAME=null;
    private final static String USERPASS=null;
    private SharedPreferences sp= null;
    private EditText loginName,loginPassword;
    private Button loginButton,registerButton;
    private TextView forgetPassword;
    private String loginUrl="http://119.29.114.73/Dream/mobileUserController/AppLogin.action";
    private String TAG="UserLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login2);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("登录");
        }

        init();

    }
    private void init(){
        loginName=findViewById(R.id.login_name);
        loginPassword=findViewById(R.id.login_password);
        loginButton=findViewById(R.id.sign_in_button);
        registerButton=findViewById(R.id.register_button);
        forgetPassword=findViewById(R.id.forget_password);

        loginButton.setOnClickListener(onClickListener);
        registerButton.setOnClickListener(onClickListener);
        forgetPassword.setOnClickListener(onClickListener);


    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sign_in_button:
                    if(loginName.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),"请输入用户名",Toast.LENGTH_LONG).show();
                    }else if (loginPassword.getText().toString().equals("")){
                              Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_LONG).show();
                    }else {
                        LoginAction(loginUrl,loginName.getText().toString(),loginPassword.getText().toString());
                    }

                    break;
                case  R.id.register_button:
                    startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                    break;
                case R.id.forget_password:
                    break;

            }
        }
    };


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


    public void CreateLoginState(User user){
        if(sp == null){
            sp = getSharedPreferences(FILE,MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",user.getuNickName());
        editor.putString("type",user.getuType());
        editor.putInt("uId",user.getuId());
        editor.commit();
    }

    private void LoginAction(final String Registerurl, final String loginName, final String loginPassword ){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    builder.add("phone",loginName).add("password",loginPassword);
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
                                Log.d(TAG,"获取返回信息"+je.getAsJsonObject().get("data"));
                                User user= UserJsonData(je.getAsJsonObject().get("data"));

                                showResponse(je.getAsJsonObject().get("status").toString(),user);
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
    private  void showResponse(final  String response, final User user){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (response){
                    case "200":
                        Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                        CreateLoginState(user);
                        int UID=user.getuId();
                        Intent data = new Intent();
                        data.putExtra("UID",UID);
                        setResult(0,data);
                        finish();
                        break;
                    case "500":
                        Toast.makeText(getApplicationContext(),"用户密码错误",Toast.LENGTH_LONG).show();
                        break;
                    case "3":
                        Toast.makeText(getApplicationContext(),"用户不存在",Toast.LENGTH_LONG).show();
                        break;
                    case "4":
                        Toast.makeText(getApplicationContext(),"用户已被冻结",Toast.LENGTH_LONG).show();
                        break;

                }

            }
        });
    }
    private User UserJsonData(JsonElement data){
        Gson gson = new Gson();
        User user = gson.fromJson(data,User.class);
        if (user!=null){
            Log.d(TAG,"获取返回信息"+user.getuNickName());
        }

        return user;
    }

}

