package com.example.admin.dreammediatechapp.UI.MainPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.admin.dreammediatechapp.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String URL;
    private  int aId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        URL ="http://119.29.114.73/Dream/mobileArticleController/getArticleToApp.action?aid="+aId;
        String url="https://www.baidu.com";
        webView = findViewById(R.id.article_webview);
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        /**在App内部打开页面  **/
        webView.setWebViewClient(new WebViewClient());
        //网页适配手机屏幕，以显示完全
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //支持手势缩放，并且隐藏丑丑的缩放按钮
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);


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
