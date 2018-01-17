package com.example.admin.dreammediatechapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.admin.dreammediatechapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2018/1/17.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private Context context;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private  Map<String,String>map = new HashMap<String,String>();

    public RecycleAdapter(Context context, List<Map<String, String>> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_list_layout,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(int postion){


        list.add(postion,map);

        notifyItemChanged(postion);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View view) {
            super(view);

        }

    }
}

