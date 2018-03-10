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
 * Created by su on 8/17/17.
 */

public class ServiceAreaAdapter extends RecyclerView.Adapter<ServiceAreaAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<String> stringArrayList;

    public ServiceAreaAdapter(Context mcontext, ArrayList<String> stringArrayList) {
        this.mcontext = mcontext;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.adapter_service_area, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_city.setText(stringArrayList.get(position));
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringArrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProRegularTextView tv_city;
        ImageView img_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_city = itemView.findViewById(R.id.tv_city);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }
}
