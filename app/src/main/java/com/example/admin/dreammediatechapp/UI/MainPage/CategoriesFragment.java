package com.example.admin.dreammediatechapp.UI.MainPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TableLayout;

import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.oneFragment;
import com.example.admin.dreammediatechapp.threeFragment;
import com.example.admin.dreammediatechapp.twoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private TabLayout mTab;
    private ViewPager mViewPager;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private Fragment one,two,three;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_categories, container, false);

        mViewPager=(ViewPager)view.findViewById(R.id.categories_content);
        mTab=(TabLayout)view.findViewById(R.id.categories_title);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTab.setTabTextColors(ContextCompat.getColor(getContext(), R.color.gray), ContextCompat.getColor(getContext(), R.color.gray));
        mTab.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.white));
        mTab.setupWithViewPager(mViewPager);
        tabIndicators = new ArrayList<>();
        tabFragments = new ArrayList<>();
        for (int i =0;i<9;i++){
            tabIndicators.add("视频分类");
            tabFragments.add(new oneFragment());

        }
        contentAdapter = new ContentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(contentAdapter);

        for(int i=0;i<tabIndicators.size();i++){
            TabLayout.Tab itemTab = mTab.getTabAt(i);
            if (itemTab!=null){
                itemTab.setText("视频分类"+i);
            }
            mTab.getTabAt(0).getText();
        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ContentPagerAdapter extends FragmentPagerAdapter{
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
