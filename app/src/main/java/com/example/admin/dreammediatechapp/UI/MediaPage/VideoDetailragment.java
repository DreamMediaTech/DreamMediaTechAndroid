package com.example.admin.dreammediatechapp.UI.MediaPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.LoginPage.UserLoginActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoDetailragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoDetailragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoDetailragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String VideoTitle,VideoCategories,VideoAuthor,VideoIntro;
    private int VideoWatch,VideoPrice,vId,uId,quota;
    private Button buyVideo;
    int uIdint;
    private TextView videoTile,videoCate,videoTime,videoWatch,videoContent,videoAuthor;
    private OnFragmentInteractionListener mListener;

    public VideoDetailragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment VideoDetailragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoDetailragment newInstance(Video video,int quota,int vId,int uId) {
        VideoDetailragment fragment = new VideoDetailragment();
        Bundle args = new Bundle();
        args.putString("VideoTitle",video.getvTitle());
        args.putString("VideoCate",video.getFirstType().getVtName());
        args.putInt("VideoWatch",video.getvNum());
        args.putString("VideoAuthor",video.getAuthor().getuNickName());
        args.putString("VideoIntro",video.getvIntroduce());
        args.putInt("VideoPrice",video.getvPrice());
        args.putInt("Quota",quota);
        args.putInt("vId",vId);
        args.putInt("uId",uId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            VideoTitle = getArguments().getString("VideoTitle");
            VideoCategories = getArguments().getString("VideoCate");
            VideoWatch  = getArguments().getInt("VideoWatch");
            VideoAuthor = getArguments().getString("VideoAuthor");
            VideoIntro = getArguments().getString("VideoIntro");
            VideoPrice = getArguments().getInt("VideoPrice");
            quota = getArguments().getInt("Quota");
            vId  =getArguments().getInt("vId");
            uId = getArguments().getInt("uId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_video_detailragment, container, false);
        videoTile = view.findViewById(R.id.Video_title);
        videoAuthor = view.findViewById(R.id.video_owner);
        videoCate = view.findViewById(R.id.video_categories);
        videoWatch = view.findViewById(R.id.video_watch);
        videoContent = view.findViewById(R.id.video_intro);
        buyVideo = view.findViewById(R.id.video_buy);
        buyVideo.setClickable(false);

        videoTile.setText(VideoTitle);
        videoCate.setText(VideoCategories);
        videoContent.setText(VideoIntro);
        videoWatch.setText("阅读量："+String.valueOf(VideoWatch));
        videoAuthor.setText(VideoAuthor);
        if (quota==0){
            buyVideo.setText("花费"+VideoPrice+"积分观看视频");
//            buyVideo.setClickable(true);
        }else {
            buyVideo.setText("剩余"+quota+"次观看次数");
            buyVideo.setClickable(false);
        }
        buyVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("LoginState",Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("name",null);
                String userType = sharedPreferences.getString("type",null);
                uIdint=sharedPreferences.getInt("uId",0);
                if (username!=null&&quota==0){
                    BuyVideo(uIdint,vId);
                    mListener.PlayAble(true);
                }else if (username==null){
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(),UserLoginActivity.class);
                    startActivityForResult(intent,0);
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            int UID = data.getIntExtra("UID", 0);
            uIdint = UID;
        }
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
        void PlayAble(boolean playable);
        void onFragmentInteraction(Uri uri);
    }

    private void BuyVideo(final int uid,final int vid){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://119.29.114.73/Dream/mobileVideoController/buyVideo.action?vid="+vid+"&uid="+uid;

                    Log.d("HPA",sendUrl2);
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
                            Log.d("VDF",result);
                            BuyState(result);

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

    private  void BuyState(final String result){
       getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.equals("5")){
                    Toast.makeText(getContext(),"购买失败",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getContext(),"购买成功",Toast.LENGTH_LONG).show();
                    buyVideo.setClickable(false);
                    buyVideo.setText("剩余3次观看次数");
                }
            }
        });
    }

}
