package com.example.admin.dreammediatechapp.Holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.dreammediatechapp.Entities.IntegralPackage;
import com.example.admin.dreammediatechapp.R;

/**
 * Created by Admin on 2018/1/19.
 */

public class GiftHolder extends RecyclerView.ViewHolder{
    private IntegralPackage integralPackage;
    private ImageView gift_image;

    @SuppressLint("ResourceType")
    public GiftHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.gift_item_layout,parent,false));

        gift_image=(ImageView)itemView.findViewById(R.color.colorPrimary);

    }



}
