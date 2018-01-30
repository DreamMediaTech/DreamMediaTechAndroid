package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.MediaPage.VideoCommentFragment;
import com.example.admin.dreammediatechapp.UI.MediaPage.VideoDetailragment;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceActivity extends AppCompatActivity implements
        HitQuestionFragment.OnFragmentInteractionListener,
        IMServiceFragment.OnFragmentInteractionListener{
    private TabLayout mTab;
    private ViewPager viewPager;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        viewPager=(ViewPager)findViewById(R.id.customer_service_content);
        mTab=(TabLayout)findViewById(R.id.customer_service_title);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setTabTextColors(ContextCompat.getColor(this, R.color.gray), ContextCompat.getColor(this, R.color.gray));
        mTab.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        mTab.setupWithViewPager(viewPager);

        tabIndicators = new ArrayList<String>();
        tabFragments = new ArrayList<>();

        tabIndicators.add("热门提问");
        tabIndicators.add("人工客服");
        tabFragments.add(new HitQuestionFragment());
        tabFragments.add(new IMServiceFragment());

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(contentAdapter);

        for (int i=0;i<tabIndicators.size();i++){
            TabLayout.Tab itemTab = mTab.getTabAt(i);
            if(itemTab!=null){
                itemTab.setText(tabIndicators.get(i).toString());
            }
            mTab.getTabAt(0).getText();
        }


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);

        }
        actionBar.setTitle("热门问题");


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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ContentPagerAdapter extends FragmentPagerAdapter {
        public ContentPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }
    }
}
