package com.example.admin.dreammediatechapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.admin.dreammediatechapp.Entities.IntegralPackage;
import com.example.admin.dreammediatechapp.Holder.GiftHolder;
import com.example.admin.dreammediatechapp.UI.MainPage.GiftFragment;
import com.example.admin.dreammediatechapp.UI.MainPage.MainActivity;

/**
 * Created by Admin on 2018/1/19.
 */

public class GIftAdapter extends RecyclerView.Adapter<GiftHolder> {
    private Context context;
    private IntegralPackage integralPackage;


    @Override
    public GiftHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(this.context);
        return new GiftHolder(inflater,viewGroup);
        /*
        bindContext(viewGroup.getContext());
        return new GiftHolder(LayoutInflater.from(this.context),viewGroup);
        */
    }

    @Override
    public void onBindViewHolder(GiftHolder holder, int i) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void bindContext(Context context) {
        this.context = context;
    }
}
