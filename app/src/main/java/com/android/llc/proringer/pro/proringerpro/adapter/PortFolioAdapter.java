package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.pojo.PortFolio;

import java.util.ArrayList;

/**
 * Created by su on 8/17/17.
 */

public class PortFolioAdapter extends RecyclerView.Adapter<PortFolioAdapter.MyViewHolder> {
//    ArrayList<PortFolio> portFolioArrayList;
    Context mContext;
    public PortFolioAdapter(Context mContext){
        this.mContext=mContext;
        //this.portFolioArrayList=portFolioArrayList;
    }
    @Override
    public PortFolioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_port_folio, parent, false));

    }

    @Override
    public void onBindViewHolder(PortFolioAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
