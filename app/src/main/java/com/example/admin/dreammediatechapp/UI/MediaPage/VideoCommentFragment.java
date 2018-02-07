package com.example.admin.dreammediatechapp.UI.MediaPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.CommentAdapter;
import com.example.admin.dreammediatechapp.Adapter.VideoListAdapter;
import com.example.admin.dreammediatechapp.Entities.Comment;
import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.Utils.NetworkUtils;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
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
 * {@link VideoCommentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoCommentFragment extends Fragment {
    private LRecyclerView lRecyclerView = null;
    private List<Comment> commentList=new ArrayList<>();
    /**服务器端一共多少条数据*/
    private  int TOTAL_COUNTER=10000;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private CommentAdapter commentAdapter= null;

    private LRecyclerViewAdapter lRecyclerViewAdapter ;

    private OnFragmentInteractionListener mListener;

    private int uId,vId;

    private TextView commentInput;

    private Button sendButton;


    public VideoCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment VideoCommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoCommentFragment newInstance(int param1, int param2,int commentCount) {
        VideoCommentFragment fragment = new VideoCommentFragment();
        Bundle args = new Bundle();
        args.putInt("vId",param1);
        args.putInt("uId",param2);
        args.putInt("commentCount",commentCount);
        fragment.setArguments(args);
        return fragment;
    }

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case -1:
                    int currentSize = commentAdapter.getItemCount();


                    if (currentSize>=TOTAL_COUNTER){
                        Toast.makeText(getActivity(),"已经没有了",Toast.LENGTH_LONG).show();
                        lRecyclerView.refreshComplete(REQUEST_COUNT);
                        break;
                    }
                    addItems(commentList);
                    lRecyclerView.refreshComplete(REQUEST_COUNT);



                    break;
                case -3:
                    lRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(),"oops，没有网络了",Toast.LENGTH_LONG).show();
                    lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            requestData();

                        }
                    });

                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vId = getArguments().getInt("vId");
        uId = getArguments().getInt("uId");
        TOTAL_COUNTER = getArguments().getInt("commentCount");
        Log.d("VCF",String.valueOf(TOTAL_COUNTER));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_video_comment, container, false);
        // Inflate the layout for this fragment
        lRecyclerView = view.findViewById(R.id.video_comment_list);
        commentAdapter = new CommentAdapter(getContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(commentAdapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);

        lRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        lRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        commentInput = view.findViewById(R.id.comment_input);
        sendButton = view.findViewById(R.id.comment_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentInput.getText().toString().equals(null))
                {
                    Toast.makeText(getContext(),"请输入评论",Toast.LENGTH_LONG).show();
                }
                else {
                    sendComment(uId,vId);
                }
            }
        });

//        //add a HeaderView
////        final View header = LayoutInflater.from(getContext()).inflate(R.layout.sample_header,(ViewGroup)findViewById(android.R.id.content), false);
//         final  View header = inflater.inflate(R.layout.sample_header,container,false);
//        lRecyclerViewAdapter.addHeaderView(header);
        //LoadData();

        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentAdapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                mCurrentCounter = 0;
                getComment(uId,vId);
            }
        });
        //是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
        lRecyclerView.setLoadMoreEnabled(true);

        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                   getComment(uId,vId);
                } else {
                    //the end
                    lRecyclerView.setNoMore(true);
                }
            }
        });

        lRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });

        //设置头部加载颜色
        lRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.darker_gray ,android.R.color.white);
        //设置底部加载颜色
        lRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.darker_gray ,android.R.color.white);
        //设置底部加载文字提示
        lRecyclerView.setFooterViewHint("拼命加载中","我是有底线的","网络不给力啊，点击再试一次吧");

        lRecyclerView.refresh();

        return view ;

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

    /**
     * 检测数据变化情况
     */
    private void notifyDataSetChanged() {
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 添加数据至Adapter
     * @param list
     */
    private void addItems(List<Comment> list) {

        commentAdapter.addAll(list);
        mCurrentCounter += list.size();

    }

    /**
     * 模拟请求数据
     */
    private void requestData() {
        Log.d("VideoCommentFragment", "requestData");
        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                if(NetworkUtils.getNetworkType(getContext())==3) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);

                }
            }
        }.start();
    }

    private void getComment(final int uid,final int vid){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://119.29.114.73/Dream/mobileVideoController/getCommentToApp.action?vid="+vId+"&start="+mCurrentCounter+"&num=5";
                    Log.d("VCF",sendUrl2);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl2).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d("VCF","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("VCF","获取返回信息"+je.getAsJsonObject().get("data"));
                            commentList = JsonData(je.getAsJsonObject().get("data"));
                            Message msg=new Message();
                            msg.what=-1;
//                            msg.setData(bundle);
                            mHandler.sendMessage(msg);



                            // JsonData(je.getAsJsonObject().get("data"));
                            //videoList=JsonData(je.getAsJsonObject().get("data"));

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
    private List<Comment> JsonData(JsonElement data){
        Gson gson = new Gson();
        List<Comment> commentList = gson.fromJson(data,new TypeToken<List<Comment>>(){}.getType());
        for (Comment comment:commentList){
            Log.d("CDA",comment.getcContent());
        }
        return commentList ;
    }

    private void sendComment(final int uid,final int vid){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String commentContent=commentInput.getText().toString();
                    String sendUrl2="http://119.29.114.73/Dream/mobileVideoController/insertComment.action?vid="+vid+"&uid="+uid+"&content="+commentContent;
                    Log.d("VCF",sendUrl2);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl2).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d("VCF","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("VCF","获取返回信息"+je.getAsJsonObject().get("data"));
                            AfterSend();

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
    private void AfterSend(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =(InputMethodManager)getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(commentInput.getWindowToken(), 0);
                commentInput.setText(null);
                Toast.makeText(getContext(),"评论成功",Toast.LENGTH_LONG).show();
            }
        });
    }
}
