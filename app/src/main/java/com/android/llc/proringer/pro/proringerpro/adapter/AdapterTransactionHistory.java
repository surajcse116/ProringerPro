package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;

/**
 * Created by su on 2/20/18.
 */

public class AdapterTransactionHistory extends RecyclerView.Adapter<AdapterTransactionHistory.MyViewHolder> {
    Context context;
    public  AdapterTransactionHistory(Context context){
        this.context=context;
    }
    @Override
    public AdapterTransactionHistory.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_transaction_history, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterTransactionHistory.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
