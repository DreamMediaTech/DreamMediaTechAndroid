package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.MainPage.MainActivity;

public class SettingsActivity extends AppCompatActivity {
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);


        }
        actionBar.setTitle("设置中心");

        final SharedPreferences sp = getSharedPreferences("LoginState",MODE_PRIVATE);

        logOutButton=findViewById(R.id.logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp.edit().clear().commit();
                Intent logout = new Intent(SettingsActivity.this,MainActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logout);
                finish();
            }
        });
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
}
