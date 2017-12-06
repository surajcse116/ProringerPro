package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.WatchListFragment;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

/**
 * Created by su on 8/10/17.
 */

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.MyViewHolder> {
    WatchListFragment.onOptionSelected callback;
    Context mcontext;

    public WatchListAdapter(Context mcontext, WatchListFragment.onOptionSelected callback) {
        this.mcontext = mcontext;
        this.callback = callback;
    }

    @Override
    public WatchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.search_watch_list_rowitem, parent, false));
    }

    @Override
    public void onBindViewHolder(WatchListAdapter.MyViewHolder holder, int position) {
//        callback.onItemPassed(position, jsonInfoArray.getJSONObject(position).getString("pros_id"));

        if (position==14){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            float density = mcontext.getResources().getDisplayMetrics().density;
            params.setMargins(0, 0, 0, dpToPx(mcontext,10));
            holder.LL_Main.setLayoutParams(params);
        }

        if(position==0){
            holder.find_local_pros.setVisibility(View.VISIBLE);
        }else {
            holder.find_local_pros.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout find_local_pros,LL_Main;
        public MyViewHolder(View itemView) {
            super(itemView);
            find_local_pros = (LinearLayout) itemView.findViewById(R.id.find_local_pros);
            LL_Main = (LinearLayout) itemView.findViewById(R.id.LL_Main);
            ProRegularTextView tv_description=(ProRegularTextView)itemView.findViewById(R.id.tv_description);
        }
    }

    public static int dpToPx(Context context,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
