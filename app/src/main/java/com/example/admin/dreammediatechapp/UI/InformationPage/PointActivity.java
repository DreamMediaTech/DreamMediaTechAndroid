package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.R;

public class PointActivity extends AppCompatActivity {
    private TextView point_cash,point_change,buy_gift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);

        }
        actionBar.setTitle("我的积分");
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

    private void init(){
        point_cash=findViewById(R.id.point_cash);
        point_cash.setOnClickListener(onClickListener);
        point_change=findViewById(R.id.point_change);
        point_change.setOnClickListener(onClickListener);
        buy_gift=findViewById(R.id.buy_gift);
        buy_gift.setOnClickListener(onClickListener);

    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.point_cash:
                break;
                case  R.id.point_change:
                    break;
                case R.id.buy_gift:
                    break;

            }
        }
    };
}
