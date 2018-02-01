package com.example.admin.dreammediatechapp.UI.MainPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dreammediatechapp.Adapter.AbsRecyclerViewAdapter;
import com.example.admin.dreammediatechapp.Adapter.InformationAdapter;
import com.example.admin.dreammediatechapp.Adapter.ShoppingAdapter;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.UI.InformationPage.CustomerServiceActivity;
import com.example.admin.dreammediatechapp.UI.InformationPage.MyShareActivity;
import com.example.admin.dreammediatechapp.UI.InformationPage.MyVideoActivity;
import com.example.admin.dreammediatechapp.UI.InformationPage.PointActivity;
import com.example.admin.dreammediatechapp.UI.InformationPage.SettingsActivity;
import com.example.admin.dreammediatechapp.UI.InformationPage.UserInfoActivity;
import com.example.admin.dreammediatechapp.UI.LoginPage.LoginActivity;
import com.example.admin.dreammediatechapp.UI.LoginPage.UserLoginActivity;
import com.example.admin.dreammediatechapp.common.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView,mRecyclerView2;
    private CircleImageView user_image;
    private TextView user_name,userChar;
    SharedPreferences sp;

    public static InformationFragment newInstance() {
        return new InformationFragment();
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        CheckLoginState();

    }

    @Override
    public  void onStart() {

        super.onStart();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_information, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.info_recycle);
        mRecyclerView2 = (RecyclerView)view.findViewById(R.id.shopping_recycle);
        user_image = view.findViewById(R.id.user_image);
        user_name = view.findViewById(R.id.user_name);
        userChar=view.findViewById(R.id.user_char);
        sp=getActivity().getSharedPreferences("LoginState",Context.MODE_PRIVATE);


        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = sp.getString("name",null);
                if (username==null){
                    startActivity(new Intent(getActivity(), UserLoginActivity.class));
                }
                else {
                    Toast.makeText(getContext(),"欢迎您，"+username,Toast.LENGTH_LONG).show();
                }


            }
        });

        initRecyclerView();


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
    public void initRecyclerView(){
        //个人中心
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        InformationAdapter informationAdapter = new InformationAdapter(mRecyclerView);
        mRecyclerView.setAdapter(informationAdapter);

        informationAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getContext(), UserInfoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), PointActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), MyShareActivity.class ));
                        break;
                    case 5:
                        startActivity(new Intent(getContext(), MyVideoActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getContext(), SettingsActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getContext(), CustomerServiceActivity.class));
                }
            }
        });

        //商城
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(getActivity(),4));
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(mRecyclerView2);
        mRecyclerView2.setAdapter(shoppingAdapter);
        shoppingAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                switch(position){

                }
            }
        });

    }


    public void CheckLoginState(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("LoginState",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("name",null);
        String userType = sharedPreferences.getString("type",null);
        if (username!=null){
            user_name.setText(username);
            userChar.setText(userType);


        }
    }
}

