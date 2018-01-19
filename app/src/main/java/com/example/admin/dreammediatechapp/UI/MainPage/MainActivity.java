package com.example.admin.dreammediatechapp.UI.MainPage;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.NoScrollViewPager;
import com.example.admin.dreammediatechapp.oneFragment;
import com.example.admin.dreammediatechapp.threeFragment;
import com.example.admin.dreammediatechapp.twoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        MediaFragment.OnFragmentInteractionListener,
        CategoriesFragment.OnFragmentInteractionListener,
        GiftFragment.OnFragmentInteractionListener,
        InformationFragment.OnFragmentInteractionListener,
        oneFragment.OnFragmentInteractionListener, twoFragment.OnFragmentInteractionListener,threeFragment.OnFragmentInteractionListener{


    private FragmentPagerAdapter mAdpater;//Fragement适配器
    private List<Fragment> mFragment;//Fragment集合
    private MenuItem menuItem;
    private NoScrollViewPager mViewPager;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        initData();


    }
    /**
     * 初始化
     */
    public void initData(){


        mViewPager=(NoScrollViewPager) findViewById(R.id.viewpager);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);//添加底部导航栏切换监听

        mFragment=new ArrayList<>();
        //添加Ftagment
        mFragment.add(new HomeFragment());
        mFragment.add(new CategoriesFragment());
        mFragment.add(new MediaFragment());
        mFragment.add(new GiftFragment());
        mFragment.add(new InformationFragment());


        /**
         * 初始化FragmmentPageAdapter
         */
        mAdpater=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };

        /**
         * 初始化ViewPager
         */
        mViewPager.setAdapter(mAdpater);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem!=null){
                    menuItem.setChecked(false);

                }else{
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem= navigationView.getMenu().getItem(position);
                menuItem.setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 底部导航栏切换操作
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_categories:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_media:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_gift:
                    mViewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_infomation:
                    mViewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
