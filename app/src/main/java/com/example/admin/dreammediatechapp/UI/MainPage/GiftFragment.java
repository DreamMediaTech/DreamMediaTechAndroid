package com.example.admin.dreammediatechapp.UI.MainPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.dreammediatechapp.Entities.IntegralPackage;
import com.example.admin.dreammediatechapp.Holder.GiftHolder;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.SimpleDividerDecoration;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        for (int i=1;i<5;i++){
            IntegralPackage integralPackage=new IntegralPackage();
            giftList.add(integralPackage);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gift, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.gift_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(new GiftAdapter());
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

    public class GiftHolder extends RecyclerView.ViewHolder{
        private IntegralPackage integralPackage;
        private ImageView gift_image;

        public GiftHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.gift_item_layout,parent,false));

            gift_image=(ImageView)itemView.findViewById(R.id.gift_image);

        }



    }

    private class GiftAdapter extends RecyclerView.Adapter<GiftHolder> {

        @Override
        public GiftHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new GiftHolder(inflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(GiftHolder holder, int i) {
            IntegralPackage integralPackage=giftList.get(i);
            holder.integralPackage=integralPackage;
            holder.gift_image.setImageResource(R.mipmap.adtest3);
        }

        @Override
        public int getItemCount() {
            return giftList.size();
        }
    }
}
