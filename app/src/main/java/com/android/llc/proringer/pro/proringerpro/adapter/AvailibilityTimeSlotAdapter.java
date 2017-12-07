package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProLightTextView;

/**
 * Created by su on 7/26/17.
 */

public class AvailibilityTimeSlotAdapter extends RecyclerView.Adapter<AvailibilityTimeSlotAdapter.MyViewHolder> {

    Context context;

    public AvailibilityTimeSlotAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AvailibilityTimeSlotAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_avilibility_time_slot, parent, false));

    }

    @Override
    public void onBindViewHolder(AvailibilityTimeSlotAdapter.MyViewHolder holder, int position) {
        if (position == 0) {
            holder.days_week.setText("Mon");
        } else if (position == 1) {
            holder.days_week.setText("Tue");
        } else if (position == 2) {
            holder.days_week.setText("Wed");
        } else if (position == 3) {
            holder.days_week.setText("Thu");
        } else if (position == 4) {
            holder.days_week.setText("Fri");
        } else if (position == 5) {
            holder.days_week.setText("Sat");
        } else if (position == 6) {
            holder.days_week.setText("Sun");
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProLightTextView days_week;

        public MyViewHolder(View itemView) {
            super(itemView);
            days_week = (ProLightTextView) itemView.findViewById(R.id.days_week);
        }
    }
}
