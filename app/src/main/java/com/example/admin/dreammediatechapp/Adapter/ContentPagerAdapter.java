package com.example.admin.dreammediatechapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Admin on 2018/1/27.
 */


    public class ContentPagerAdapter extends FragmentPagerAdapter {
       private List<Fragment> fragment;
        private List<String> tabIndicators;
        public ContentPagerAdapter(FragmentManager fm,List<Fragment> tabFragments,List<String > tabIndicators){
            super(fm);
            this.fragment=tabFragments;
            this.tabIndicators=tabIndicators;

        }
        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }
    }
