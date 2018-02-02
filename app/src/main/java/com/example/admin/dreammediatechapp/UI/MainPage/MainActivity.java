package com.example.admin.dreammediatechapp.UI.MainPage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Entities.Comment;
import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.CategoriesPage.SubCategoriesFragment;
import com.example.admin.dreammediatechapp.common.NoScrollViewPager;
import com.example.admin.dreammediatechapp.oneFragment;
import com.example.admin.dreammediatechapp.threeFragment;
import com.example.admin.dreammediatechapp.twoFragment;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.util.WeakHandler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        MediaFragment.OnFragmentInteractionListener,
        CategoriesFragment.OnFragmentInteractionListener,
        GiftFragment.OnFragmentInteractionListener,
        InformationFragment.OnFragmentInteractionListener,
        oneFragment.OnFragmentInteractionListener,
        twoFragment.OnFragmentInteractionListener,
        threeFragment.OnFragmentInteractionListener,
        SubCategoriesFragment.OnFragmentInteractionListener{


    private FragmentPagerAdapter mAdpater;//Fragement适配器
    private List<Fragment> mFragment = new ArrayList<>();//Fragment集合
    private MenuItem menuItem;
    private NoScrollViewPager mViewPager;
    private BottomNavigationView navigationView;
    private int mNoPermissionIndex = 0;
    private final int PERMISSION_REQUEST_CODE = 1;
    private final String[] permissionManifest = {
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private final int[] noPermissionTip = {
            R.string.no_camera_permission,
            R.string.no_record_bluetooth_permission,
            R.string.no_record_audio_permission,
            R.string.no_read_phone_state_permission,
            R.string.no_write_external_storage_permission,
            R.string.no_read_external_storage_permission,
    };

    public List<VideoType> videoTypeList = new ArrayList<>() ;
    CategoriesFragment categoriesFragment=new CategoriesFragment();

//    private WeakHandler mHandler = new WeakHandler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//
//                case -1:
//                    Bundle bundle = msg.getData();
//                    ArrayList llist= new ArrayList();
//                            llist=bundle.getParcelableArrayList("llist");
//                    videoTypeList=(List<VideoType>) llist.get(0);
//                  break;
//                default:
//                    break;
//            }
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        permissionCheck();

        initData();




    }
    private boolean permissionCheck() {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        String permission;
        for (int i = 0; i < permissionManifest.length; i++) {
            permission = permissionManifest[i];
            mNoPermissionIndex = i;
            if (PermissionChecker.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = PackageManager.PERMISSION_DENIED;
            }
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 初始化
     */
    public void initData(){


        mViewPager=(NoScrollViewPager) findViewById(R.id.viewpager);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);//添加底部导航栏切换监听
        BottomNavigationViewHelper.disableShiftMode(navigationView);//去除底部导航栏动画




        if (videoTypeList.size()==0){
            Toast.makeText(this,"列表为空",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"列表不为空",Toast.LENGTH_LONG).show();
        }
        //添加Ftagment


        mFragment.add(new HomeFragment());
        mFragment.add(categoriesFragment);
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
        mAdpater.notifyDataSetChanged();

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
