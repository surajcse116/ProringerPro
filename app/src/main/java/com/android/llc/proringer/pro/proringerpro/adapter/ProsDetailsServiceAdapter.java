package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.MyProfileActivity;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by su on 2/20/18.
 */

public class ProsDetailsServiceAdapter extends RecyclerView.Adapter<ProsDetailsServiceAdapter.MyViewHolder> {
    JSONArray serviceAreaJsonArray;
    Context context;
    MyProfileActivity.onOptionSelected callback;

    public ProsDetailsServiceAdapter(Context context, JSONArray serviceJsonArray, MyProfileActivity.onOptionSelected callback) {
        this.context = context;
        this.serviceAreaJsonArray = serviceJsonArray;
        this.callback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pro_details_service, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
//            if (position == 13) {
//                holder.tv_name.setText("More...");
//                holder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
//            } else {
            holder.tv_name.setText(serviceAreaJsonArray.getJSONObject(position).getString("service_name"));
            //}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 13) {
                    callback.onItemPassed(position, "More");
                } else {
                    callback.onItemPassed(position, "");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (serviceAreaJsonArray.length() >= 14) {
            return 14;
        } else {
            return serviceAreaJsonArray.length();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProRegularTextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (ProRegularTextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
