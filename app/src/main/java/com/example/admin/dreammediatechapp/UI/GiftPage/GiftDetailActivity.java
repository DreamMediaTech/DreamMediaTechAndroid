package com.example.admin.dreammediatechapp.UI.GiftPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.admin.dreammediatechapp.R;

public class GiftDetailActivity extends AppCompatActivity {
    private int pId;
    private String TAG = "GDA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);
        pId = getIntent().getIntExtra("pId",0);
        Log.d(TAG, "onCreate: "+pId);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("购买礼包");
        }

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
