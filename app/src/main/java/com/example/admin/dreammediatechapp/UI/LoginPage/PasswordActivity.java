package com.example.admin.dreammediatechapp.UI.LoginPage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.R;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
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
                Intent chagePasswordActivity = new Intent(PasswordActivity.this,ChagePasswordActivity.class);
                startActivity(chagePasswordActivity);
            }
        });

        TextView changeEmail =(TextView)findViewById(R.id.changeToEmail);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"这里有一个BUG",Toast.LENGTH_LONG).show();
                /*Intent changeToEmail = new Intent(PasswordActivity.this,PasswordEmailActivity.class);
                startActivity(changeToEmail);*/
            }
        });
        final Button getphoneGetCode = (Button)findViewById(R.id.password_get_code);
        getphoneGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getphoneGetCode.setClickable(false);
            }
        });
    }
}
