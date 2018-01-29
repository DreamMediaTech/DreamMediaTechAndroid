package com.example.admin.dreammediatechapp.common;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

import com.example.admin.dreammediatechapp.R;

/**
 * 倒计时
 */

public class TimeCount extends CountDownTimer {
    private Button button;
    public TimeCount(long millisInFuture, long countDownInterval,Button button) {
        super(millisInFuture, countDownInterval);
        this.button=button;
    }

    @Override
    public void onTick(long l) {
        button.setBackgroundColor(Color.parseColor("#B6B6D8"));
        button.setClickable(false);
        button.setText("("+l / 1000 +") S");

    }

    @Override
    public void onFinish() {
        button.setText("获取验证码");
        button.setClickable(true);
        button.setBackgroundColor(Color.parseColor("#FF009688"));

    }
}
