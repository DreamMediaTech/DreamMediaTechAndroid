package com.example.admin.dreammediatechapp.UI.CategoriesPage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.dreammediatechapp.Adapter.AbsRecyclerViewAdapter;
import com.example.admin.dreammediatechapp.Adapter.HomeShortCutAdapter;
import com.example.admin.dreammediatechapp.Adapter.SubCategoriesAdapter;
import com.example.admin.dreammediatechapp.Entities.VideoType;
import com.example.admin.dreammediatechapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubCategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubCategoriesFragment extends Fragment {

    private List<VideoType> subTypeList = new ArrayList<>();
    private RecyclerView subTypeRecyclerView ;
    private String vtName;

    private OnFragmentInteractionListener mListener;

    public SubCategoriesFragment() {
        // Required empty public constructor
    }

    public static SubCategoriesFragment newInstance(List<VideoType> llist,String vtName ) {
        SubCategoriesFragment fragment = new SubCategoriesFragment();
        Bundle args = new Bundle();
        ArrayList list = new ArrayList();
        list.add(llist);
        args.putParcelableArrayList("list",list);
        args.putString("vtName",vtName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList list = getArguments().getParcelableArrayList("list");
           vtName= getArguments().getString("vtName");

            subTypeList = (List<VideoType>)list.get(0);
            for (VideoType videoType:subTypeList){
                Log.d("Cate","Sub"+videoType.getVtName());
                Log.d("Cate","Sub"+vtName);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sub_categories, container, false);
        subTypeRecyclerView =view.findViewById(R.id.sub_categories_recyclerView);
        subTypeRecyclerView.setHasFixedSize(true);
        subTypeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        SubCategoriesAdapter subCategoriesAdapter = new SubCategoriesAdapter(subTypeRecyclerView,subTypeList);
        subTypeRecyclerView.setAdapter(subCategoriesAdapter);
        subCategoriesAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {

                        Intent intent= new Intent(getContext(),CategoriesDetailActivity.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("vtName",subTypeList.get(position).getVtName());
                        bundle.putInt("vtId",subTypeList.get(position).getVtId());
                        intent.putExtras(bundle);
                        startActivity(intent);



            }
        });
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
