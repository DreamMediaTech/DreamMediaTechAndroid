package com.example.admin.dreammediatechapp.UI.MainPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.ArticleListAdapter;
import com.example.admin.dreammediatechapp.Entities.Article;
import com.example.admin.dreammediatechapp.Entities.IntegralPackage;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.GiftPage.GiftDetailActivity;
import com.example.admin.dreammediatechapp.common.SimpleDividerDecoration;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GiftFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GiftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GiftFragment extends Fragment {
    private String TAG="GF";


    /**服务器端一共多少条数据*/
    private int TOTAL_COUNTER =10000;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private List<Article> articleList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<IntegralPackage> giftList=new ArrayList<>();


    public GiftFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GiftFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GiftFragment newInstance(String param1, String param2) {
        GiftFragment fragment = new GiftFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        for (int i=1;i<10;i++){
//            IntegralPackage integralPackage=new IntegralPackage();
//            giftList.add(integralPackage);
//
//        }
        GetGiftList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gift, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.gift_list);

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


    private void GetGiftList(){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl = "http://119.29.114.73/Dream/mobilePackageController/getAllPackage.action";
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
                            giftList = JsonData(je.getAsJsonObject().get("data"));
                            UIThread(giftList);

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
    private List<IntegralPackage> JsonData(JsonElement data){
        Gson gson = new Gson();
        List<IntegralPackage> integralPackageList = gson.fromJson(data,new TypeToken<List<IntegralPackage>>(){}.getType());
        return integralPackageList ;
    }
    private  void UIThread(final List<IntegralPackage> giftList){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                mRecyclerView.setAdapter(new GiftAdapter());
            }
             class GiftHolder extends RecyclerView.ViewHolder{
                private ImageView gift_image;

                public GiftHolder(LayoutInflater inflater, ViewGroup parent) {
                    super(inflater.inflate(R.layout.gift_item_layout,parent,false));
                    gift_image=(ImageView)itemView.findViewById(R.id.gift_image);


                }
            }

            class GiftAdapter extends RecyclerView.Adapter<GiftHolder> {

                @Override
                public GiftHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    return new GiftHolder(inflater,viewGroup);
                }

                @Override
                public void onBindViewHolder(GiftHolder holder, int i) {
                    final IntegralPackage integralPackage=giftList.get(i);
                    holder.gift_image.setImageResource(R.mipmap.banner3);
                    holder.gift_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), GiftDetailActivity.class).putExtra("pId",integralPackage.getpId());
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return giftList.size();
                }
            }
        });
    }

}
