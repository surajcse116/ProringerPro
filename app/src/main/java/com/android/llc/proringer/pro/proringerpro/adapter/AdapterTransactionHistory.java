package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetChatPojo;
import com.android.llc.proringer.pro.proringerpro.pojo.Transctiondetails;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by su on 2/20/18.
 */

public class AdapterTransactionHistory extends RecyclerView.Adapter<AdapterTransactionHistory.MyViewHolder> {
    Context context;
    private ArrayList<Transctiondetails> dataList;
    public  AdapterTransactionHistory(Context context,ArrayList<Transctiondetails> dataList)
    {
        this.context=context;
        this.dataList=dataList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_transaction_history, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_level_amount.setText(dataList.get(position).getAmount());
        holder.tv_level_date.setText(dataList.get(position).getDate());
        holder.premium_month.setText(dataList.get(position).getTransaction());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProRegularTextView tv_level_date,tv_level_amount,premium_month;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_level_amount=(ProRegularTextView)itemView.findViewById(R.id.tv_level_amount);
            tv_level_date=(ProRegularTextView)itemView.findViewById(R.id.tv_level_date);
            premium_month=(ProRegularTextView)itemView.findViewById(R.id.premium_month);

        }
    }
}
