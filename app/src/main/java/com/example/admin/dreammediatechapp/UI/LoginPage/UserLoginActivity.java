package com.example.admin.dreammediatechapp.UI.LoginPage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
import java.util.List;

import com.example.admin.dreammediatechapp.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class UserLoginActivity extends AppCompatActivity{
    private EditText loginName,loginPassword;
    private Button loginButton,registerButton;
    private TextView forgetPassword;

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

}

