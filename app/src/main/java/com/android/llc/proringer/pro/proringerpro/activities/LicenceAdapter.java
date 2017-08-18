package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;

/**
 * Created by su on 8/18/17.
 */

public class LicenceAdapter extends RecyclerView.Adapter<LicenceAdapter.MyViewHolder> {
    //    ArrayList<PortFolio> portFolioArrayList;
    Context mContext;

    public LicenceAdapter(Context mContext) {
        this.mContext = mContext;
        //this.portFolioArrayList=portFolioArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_port_folio, parent, false));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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
