package com.example.admin.dreammediatechapp.UI.LoginPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.dreammediatechapp.R;

public class ChagePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chage_password);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("修改密码");
    }
}
