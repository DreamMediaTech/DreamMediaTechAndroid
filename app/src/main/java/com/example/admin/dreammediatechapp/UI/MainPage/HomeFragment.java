package com.example.admin.dreammediatechapp.UI.MainPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.admin.dreammediatechapp.Adapter.AbsRecyclerViewAdapter;
import com.example.admin.dreammediatechapp.Adapter.ContentPagerAdapter;
import com.example.admin.dreammediatechapp.Adapter.HomeShortCutAdapter;
import com.example.admin.dreammediatechapp.Adapter.ShoppingAdapter;
import com.example.admin.dreammediatechapp.Adapter.VideoAdapter;
import com.example.admin.dreammediatechapp.Adapter.VideoListAdapter;
import com.example.admin.dreammediatechapp.Entities.Display;
import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.DreamMediaPage.ArticleDetailActivity;
import com.example.admin.dreammediatechapp.UI.HomePage.HomeRecommendFragment;
import com.example.admin.dreammediatechapp.UI.MediaPage.HPlayerActivity;
import com.example.admin.dreammediatechapp.UI.MediaPage.PlayerActivity;
import com.example.admin.dreammediatechapp.common.SimpleDividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String TAG="HF";
    private RollPagerView mRollPageViewPager;
    private NestedScrollView nestedScrollView;
    private List<Video> videoList = new ArrayList<>();
    private RecyclerView shortcutRecyclerView;
    private LRecyclerView mRecyclerView;
    private TabLayout mTab;
    private ViewPager mViewPager;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private List<Display> bannerList= new ArrayList<>();
    private ViewFlipper viewFlipper;
    private List hitList;
    private int count;
    private String title[]={
            "热门推荐","精品课程","考题模拟","直播课程","名师讲解","更多推荐"
    };


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetBannerList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        mRollPageViewPager=view.findViewById(R.id.adRoller);


        nestedScrollView= view.findViewById(R.id.home_recyclerview);
        nestedScrollView.setFocusable(true);
        nestedScrollView.setFocusableInTouchMode(true);
        nestedScrollView.requestFocus();
        nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);

        shortcutRecyclerView=(RecyclerView) view.findViewById(R.id.home_shortcut);
        shortcutInit();

        mTab = view.findViewById(R.id.home_tab);
        mViewPager = view.findViewById(R.id.home_content);

        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTab.setTabTextColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mTab.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mTab.setupWithViewPager(mViewPager);
        tabIndicators = new ArrayList<>();
        tabFragments = new ArrayList<>();
        for(int i=0;i<title.length;i++){
            tabIndicators.add(title[i]);
            tabFragments.add(new HomeRecommendFragment().newInstance());
        }
        contentAdapter = new ContentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(contentAdapter);


        for(int i=0;i<tabIndicators.size();i++){
            TabLayout.Tab itemTab = mTab.getTabAt(i);
            if (itemTab!=null){
                itemTab.setText(title[i]);
            }
        }


        viewFlipper = view.findViewById(R.id.hit_flipper);
        hitList = new ArrayList();
        hitList.add("孩子30天提高30分的秘诀？");
        hitList.add("这些经典例题你一定不能错过");
        hitList.add("震惊！原来高分学生都是这么学习的");
        hitList.add("为什么他的孩子经常在玩，成绩却这么好？");

        for (int i=0;i<hitList.size();i++){
            final  View ll_content = View.inflate(getActivity(),R.layout.item_hit_article,null);
            TextView tvContent = (TextView) ll_content.findViewById(R.id.tv_content);
            tvContent.setText(hitList.get(i).toString());
            viewFlipper.addView(ll_content);
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

    public void shortcutInit(){
        shortcutRecyclerView.setHasFixedSize(true);
        shortcutRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));

        HomeShortCutAdapter homeShortCutAdapter = new HomeShortCutAdapter(shortcutRecyclerView);
       shortcutRecyclerView.setAdapter(homeShortCutAdapter);
        homeShortCutAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                switch (position){

                }
            }
        });
    }
    private void RollPager(){
        //设置播放时间间隔
        mRollPageViewPager.setPlayDelay(3000);
        //设置透明度
        mRollPageViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollPageViewPager.setAdapter(new TestNormalAdapter());
        //设置圆点指示器颜色
        mRollPageViewPager.setHintView(new ColorPointHintView(getContext(), R.color.black,R.color.colorPrimary));
        mRollPageViewPager.setHintAlpha(1);

    }
    private class TestNormalAdapter extends StaticPagerAdapter {
//        private String[] img={
//                "http://f.hiphotos.baidu.com/image/pic/item/503d269759ee3d6db032f61b48166d224e4ade6e.jpg",
//                "http://a.hiphotos.baidu.com/image/pic/item/500fd9f9d72a6059f550a1832334349b023bbae3.jpg",
//                "http://h.hiphotos.baidu.com/image/pic/item/1ad5ad6eddc451da9eed97a0bdfd5266d0163265.jpg"
//        };

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView imageView=new ImageView(container.getContext());
            Glide.with(getContext()).load(bannerList.get(position).getImageAddress()).into(imageView);

//            imageView.setImageResource(img[position]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle url = new Bundle();
                    url.putString("bannerUrl",bannerList.get(position).getLinkAddress());
                    Log.d(TAG,bannerList.get(position).getLinkAddress());
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtras(url);
                    startActivity(intent);

                }
            });
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return bannerList.size();
        }
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
    private void GetBannerList(){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl ="http://119.29.114.73/Dream/displayController/getAllDisplayToApp.action";
                    Log.d(TAG,sendUrl);
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
                            Log.d(TAG,"获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d(TAG,"获取返回信息"+je.getAsJsonObject().get("data"));
                            //JsonData(je.getAsJsonObject().get("data"));
                            //videoList=JsonData(je.getAsJsonObject().get("data"));
                            //TOTAL_COUNTER = videoList.size();
                            // Log.d(TAG,"获取返回信息"+String.valueOf(TOTAL_COUNTER));
                            bannerList = JsonData(je.getAsJsonObject().get("data"));
                            UIThread(bannerList);

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
    private List<Display> JsonData(JsonElement data){
        Gson gson = new Gson();
        List<Display> bannerList = gson.fromJson(data,new TypeToken<List<Display>>(){}.getType());
        for (Display display:bannerList){
            Log.d(TAG,display.getImageAddress());
        }
        return bannerList ;
    }
    private void UIThread(final List<Display> List) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RollPager();
            }
        });
    }


}
