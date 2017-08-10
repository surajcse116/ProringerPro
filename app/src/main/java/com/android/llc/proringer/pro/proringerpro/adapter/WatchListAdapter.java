package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.WatchListFragment;

/**
 * Created by su on 8/10/17.
 */

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.MyViewHolder> {
    WatchListFragment.onOptionSelected callback;
    Context mcontext;

    public WatchListAdapter(Context mcontext,WatchListFragment.onOptionSelected callback){
        this.mcontext=mcontext;
        this.callback=callback;
    }

    @Override
    public WatchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.search_watch_list_rowitem, parent, false));
    }

    @Override
    public void onBindViewHolder(WatchListAdapter.MyViewHolder holder, int position) {
//        callback.onItemPassed(position, jsonInfoArray.getJSONObject(position).getString("pros_id"));
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
