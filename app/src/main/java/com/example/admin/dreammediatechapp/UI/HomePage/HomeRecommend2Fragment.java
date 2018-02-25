package com.example.admin.dreammediatechapp.UI.HomePage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.dreammediatechapp.Adapter.AbsRecyclerViewAdapter;
import com.example.admin.dreammediatechapp.Adapter.VideoListAdapter;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.MediaPage.HPlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeRecommend2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeRecommend2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeRecommend2Fragment extends Fragment {

    private List<Video> videoList  = new ArrayList<>();
    private RecyclerView recyclerView;


    private OnFragmentInteractionListener mListener;

    public HomeRecommend2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeRecommend2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeRecommend2Fragment newInstance(List<Video> List) {
        HomeRecommend2Fragment fragment = new HomeRecommend2Fragment();
        Bundle args = new Bundle();
        ArrayList list = new ArrayList();
        list.add(List);
        args.putParcelableArrayList("list",list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList list = getArguments().getParcelableArrayList("list");
            videoList = (List<Video>)list.get(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_recommend2, container, false);
        recyclerView = view.findViewById(R.id.recommendList2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VideoListAdapter videoListAdapter = new VideoListAdapter(recyclerView,videoList);
        recyclerView.setAdapter(videoListAdapter);
        videoListAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Intent intent = new Intent(getContext(), HPlayerActivity.class);
                Bundle b=new Bundle();
                b.putInt("vId",videoList.get(position).getvId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
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
}
