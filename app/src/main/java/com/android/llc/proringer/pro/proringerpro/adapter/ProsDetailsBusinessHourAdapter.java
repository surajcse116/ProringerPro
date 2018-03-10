package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by su on 2/20/18.
 */

public class ProsDetailsBusinessHourAdapter extends RecyclerView.Adapter<ProsDetailsBusinessHourAdapter.MyViewHolder> {
    JSONArray businessHoursJsonArray;
    Context context;

    public ProsDetailsBusinessHourAdapter(Context context, JSONArray businessHoursJsonArray) {
        this.context = context;
        this.businessHoursJsonArray = businessHoursJsonArray;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pro_details_business_hours, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            holder.tv_day.setText(businessHoursJsonArray.getJSONObject(position).getString("day"));
            holder.tv_time.setText(businessHoursJsonArray.getJSONObject(position).getString("hours"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return businessHoursJsonArray.length();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProRegularTextView tv_day, tv_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_day = (ProRegularTextView) itemView.findViewById(R.id.tv_day);
            tv_time = (ProRegularTextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
