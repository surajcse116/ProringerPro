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
 * Created by su on 8/21/17.
 */

public class ServiceOfferedAdapter extends RecyclerView.Adapter<ServiceOfferedAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<String> stringServiceList;

    public ServiceOfferedAdapter(Context mcontext, ArrayList<String> stringServiceList) {
        this.mcontext = mcontext;
        this.stringServiceList = stringServiceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.adapter_service_area, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_city.setText(stringServiceList.get(position));
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringServiceList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringServiceList.size();
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
