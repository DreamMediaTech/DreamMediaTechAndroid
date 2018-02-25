package com.example.admin.dreammediatechapp.UI.CategoriesPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.CommentAdapter;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesListFragment extends Fragment {
    private LRecyclerView lRecyclerView = null;
    ArrayList<Video> videoList=new ArrayList<>();
    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 34;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private CommentAdapter commentAdapter= null;

    private LRecyclerViewAdapter lRecyclerViewAdapter ;

    private int vtId;

    private OnFragmentInteractionListener mListener;

    public CategoriesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoriesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesListFragment newInstance(int vtId) {
        CategoriesListFragment fragment = new CategoriesListFragment();
        Bundle args = new Bundle();
        args.putInt("vtId",vtId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vtId= getArguments().getInt("vtId");
            GetVideoList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_categories_list, container, false);
        // Inflate the layout for this fragment

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void GetVideoList(){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl = "http://192.168.1.104:8080/Dream/mobileVideoController/getAllVideoByType.action?type="+vtId+"&start=0&num=10";
                    Log.d("Cl",sendUrl);
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
                            Log.d("CL","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("CL","获取返回信息"+je.getAsJsonObject().get("data"));
                            //JsonData(je.getAsJsonObject().get("data"));
                            //videoTypeList=JsonData(je.getAsJsonObject().get("data"));
                            //UIThread(JsonData(je.getAsJsonObject().get("data")));
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
    private List<Video> JsonData(JsonElement data){
        Gson gson = new Gson();
        List<Video> videoList = gson.fromJson(data,new TypeToken<List<VideoType>>(){}.getType());
        for (Video video:videoList){
            Log.d("Cate",video.getvTitle());
        }
        return videoList ;
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



            }
        });
    }
}
