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
import java.util.ArrayList;
import java.util.List;

import com.example.admin.dreammediatechapp.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
    private String loginUrl="http://192.168.1.103:8080/Dream/MobileUserController/AppLogin.action";
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
                    LoginAction(loginUrl,loginName.getText().toString(),loginPassword.getText().toString());
                    Toast.makeText(getApplicationContext(),"登录",Toast.LENGTH_LONG).show();
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


    public void CreateLoginState(String username,String password){
        if(sp == null){
            sp = getSharedPreferences(FILE,MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",loginName.getText().toString());
        editor.putString("pass",loginPassword.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"执行方法",Toast.LENGTH_LONG).show();
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

                }

            }
        });
    }

}

