package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;

/**
 * Created by su on 8/12/17.
 */

public class AvailabilityRecyclerViewAdapter extends RecyclerView.Adapter<AvailabilityRecyclerViewAdapter.MyViewHolder> {
    Context context;

    public AvailabilityRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AvailabilityRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_avilability, parent, false));
    }

    @Override
    public void onBindViewHolder(AvailabilityRecyclerViewAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
