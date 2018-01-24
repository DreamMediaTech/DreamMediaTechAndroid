package com.example.admin.dreammediatechapp.UI.MainPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.MediaPage.HPlayerActivity;
import com.example.admin.dreammediatechapp.common.SimpleDividerDecoration;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RollPagerView mRollPageViewPager;
    private RecyclerView mRecyclerView;

   private List<Video> videoList = new ArrayList<>();



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 1;i<10;i++){
            Video video=new Video();

            User user=new User();
            user.setuName("第"+i+"个作者");
            video.setAuthor(user);
            video.setvTitle("第"+i+"个视频");
            video.setvNum(100);
            VideoType videoType=new VideoType();
            videoType.setVtName("分类一");
            video.setFirstType(videoType);
            video.setvNum(111*i);
            videoList.add(video);
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        mRollPageViewPager=view.findViewById(R.id.adRoller);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.recommendList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(getActivity()));
        mRecyclerView.setAdapter(new VideoAdapter());
        RollPager();
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

    private void RollPager(){
        //设置播放时间间隔
        mRollPageViewPager.setPlayDelay(3000);
        //设置透明度
        mRollPageViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollPageViewPager.setAdapter(new TestNormalAdapter());
        //设置圆点指示器颜色
        mRollPageViewPager.setHintView(new ColorPointHintView(getContext(), Color.BLACK,Color.BLUE));
        mRollPageViewPager.setHintAlpha(1);


    }


    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] img={
                R.mipmap.banner1,
                R.mipmap.banner2,
                R.mipmap.banner3,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView=new ImageView(container.getContext());
            imageView.setImageResource(img[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return img.length;
        }
    }



    private class VideoHolder extends RecyclerView.ViewHolder {
        private Video mVideo;
        private ConstraintLayout item_layout;
        private ImageView video_cover;
        private TextView video_title,video_owner,video_categories,video_watch;



        public VideoHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.video_list_layout,parent,false));


        video_cover=(ImageView)itemView.findViewById(R.id.video_list_cover);
        video_title=(TextView)itemView.findViewById(R.id.video_list_title);
        video_owner=(TextView)itemView.findViewById(R.id.video_list_owner);
        video_categories=(TextView)itemView.findViewById(R.id.video_list_categories);
        video_watch=(TextView)itemView.findViewById(R.id.video_list_watch);

        video_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent play = new Intent(getActivity().getApplicationContext(), HPlayerActivity.class);
                Bundle bundle = new Bundle();
                startActivity(play);

            }
        });
    }
}



    private class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {
        @Override
        public VideoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new VideoHolder(inflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int i) {
            Video video = videoList.get(i);
            holder.mVideo=video;
            holder.video_cover.setImageResource(R.mipmap.banner2);
            holder.video_title.setText(video.getvTitle());
            holder.video_owner.setText(video.getAuthor().getuName());
            holder.video_categories.setText(video.getFirstType().getVtName());

        }

        @Override
        public int getItemCount() {
            return videoList.size();
        }
    }
}
