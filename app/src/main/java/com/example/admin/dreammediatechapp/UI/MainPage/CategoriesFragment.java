package com.example.admin.dreammediatechapp.UI.MainPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.CategoriesPage.SubCategoriesFragment;
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
import java.util.Timer;
import java.util.TimerTask;

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
    private List<VideoType> videoTypeList  ;
    private TimerTask timerTask;
    private final Timer timer = new Timer();

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(List<VideoType> videoTypeList) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        ArrayList list = new ArrayList();
        list.add(videoTypeList);
        args.putParcelableArrayList("list",list);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList list = getArguments().getParcelableArrayList("list");
            videoTypeList = (List<VideoType>)list.get(0);
            for (VideoType videoType:videoTypeList){
                Log.d("Cates",videoType.getVtName());
            }
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //GetCategories();
            }
        };
        timer.schedule(timerTask,5000,5000);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_categories, container, false);

        mViewPager=(ViewPager)view.findViewById(R.id.categories_content);
        mTab=(TabLayout)view.findViewById(R.id.categories_title);

         GetCategories();
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
    private void GetCategories(){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl = "http://119.29.114.73/Dream/mobileVideoController/getAllVideoType.action";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl).build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d("Categories","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("Categories","获取返回信息"+je.getAsJsonObject().get("data"));
                            //JsonData(je.getAsJsonObject().get("data"));
                            //videoTypeList=JsonData(je.getAsJsonObject().get("data"));
                            UIThread(JsonData(je.getAsJsonObject().get("data")));
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(){
            public void run(){
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }.start();
    }
    private List<VideoType> JsonData(JsonElement data){
        Gson gson = new Gson();
        List<VideoType> videoTypeList = gson.fromJson(data,new TypeToken<List<VideoType>>(){}.getType());
        for (VideoType videoType:videoTypeList){
            Log.d("Cate",videoType.getVtName());
        }
        return videoTypeList ;
    }

    private  void UIThread(final List<VideoType> List){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),"调用UIThread",Toast.LENGTH_LONG);
//                for (VideoType videoType:List){
//                    tabIndicators.add(videoType.getVtName());
//                    tabFragments.add(new oneFragment());
//                }

//                for(int i=0;i<tabIndicators.size();i++) {
//                    TabLayout.Tab itemTab = mTab.getTabAt(i);
//
//                    if (itemTab != null) {
////                        itemTab.setText("视频分类" + i);
//                    itemTab.setText(tabIndicators.get(i));
//                    }
//                }
                mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
                mTab.setTabTextColors(ContextCompat.getColor(getContext(), R.color.white), ContextCompat.getColor(getContext(), R.color.white));
                mTab.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.white));
                mTab.setupWithViewPager(mViewPager);
                tabIndicators = new ArrayList<>();
                tabFragments = new ArrayList<>();
                for (VideoType videoType:List){
                    Log.d("Frage",videoType.getVtName());
                    tabIndicators.add(videoType.getVtName());
                    List<VideoType> subList = videoType.getSubTypes();
                    for (VideoType videoType1:subList){
                        Log.d("Cate",videoType1.getVtName());
                    }
//                    tabFragments.add(new oneFragment().newInstance(videoType.getVtName()));

                     tabFragments.add(new SubCategoriesFragment().newInstance(subList,videoType.getVtName()));
                }

                contentAdapter = new ContentPagerAdapter(getChildFragmentManager());
                mViewPager.setAdapter(contentAdapter);
                for(int i=0;i<tabIndicators.size();i++) {
                    TabLayout.Tab itemTab = mTab.getTabAt(i);
                    if (itemTab != null) {
                      //  itemTab.setText("视频分类" + i);
                        itemTab.setText(tabIndicators.get(i));
                    }
                }
            }
        });
    }




}
