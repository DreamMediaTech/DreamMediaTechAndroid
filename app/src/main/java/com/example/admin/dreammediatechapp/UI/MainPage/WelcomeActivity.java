package com.example.admin.dreammediatechapp.UI.MainPage;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.dreammediatechapp.R;

public class WelcomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        timer.start();
        this.finish();
    }
    private CountDownTimer timer = new CountDownTimer(1000,1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            startActivity(new Intent(getApplication(),MainActivity.class));
        }
    };
}
