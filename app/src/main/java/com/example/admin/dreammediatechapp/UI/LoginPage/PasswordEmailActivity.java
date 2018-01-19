package com.example.admin.dreammediatechapp.UI.LoginPage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.dreammediatechapp.R;

public class PasswordEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_email);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("找回密码");
        Button passwordNextStep = (Button)findViewById(R.id.password_phone_button);
        passwordNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chagePasswordActivity = new Intent(PasswordEmailActivity.this,ChagePasswordActivity.class);
                startActivity(chagePasswordActivity);
            }
        });
        final Button getEmailGetCode = (Button)findViewById(R.id.email_get_code);
        getEmailGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEmailGetCode.setClickable(false);
            }
        });

    }
}
