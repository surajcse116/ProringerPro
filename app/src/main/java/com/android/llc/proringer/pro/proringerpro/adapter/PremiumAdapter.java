package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import java.util.ArrayList;

/**
 * Created by su on 17/11/17.
 */

public class PremiumAdapter extends  RecyclerView.Adapter<PremiumAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> stringArrayList;
    public PremiumAdapter(Context context,ArrayList<String> stringArrayList){
        this.context=context;
        this.stringArrayList=stringArrayList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.primumdetails, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_data.setText(stringArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProRegularTextView tv_data;
        ImageView img_check;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_data=(ProRegularTextView)itemView.findViewById(R.id.tv_data);
            img_check=(ImageView)itemView.findViewById(R.id.img_check);
        }
    }
}
