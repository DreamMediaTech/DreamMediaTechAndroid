package com.example.admin.dreammediatechapp.UI.LoginPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.TimeCount;

public class RegisterActivity extends AppCompatActivity {
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

        phoneNum=findViewById(R.id.register_phone_number);
        code=findViewById(R.id.register_input_code);
        password=findViewById(R.id.register_input_password);
        comfirmPass=findViewById(R.id.register_comfirm_password);

        Button confirmButtom = (Button)findViewById(R.id.register_comfirm_button);
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

}
