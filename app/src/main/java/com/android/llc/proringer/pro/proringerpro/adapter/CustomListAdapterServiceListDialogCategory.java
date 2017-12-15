package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.AddServicesActivity;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwo;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by su on 12/14/17.
 */

public class CustomListAdapterServiceListDialogCategory extends RecyclerView.Adapter<CustomListAdapterServiceListDialogCategory.MyViewHolder> {
    Context mContext;
    JSONArray predictionsJsonArray;
    AddServicesActivity.onOptionSelected callback;

    public CustomListAdapterServiceListDialogCategory(Context mContext, JSONArray predictionsJsonArray, AddServicesActivity.onOptionSelected callback) {
        this.mContext = mContext;
        this.predictionsJsonArray = predictionsJsonArray;
        this.callback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custompop, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            holder.tv.setText(predictionsJsonArray.getJSONObject(position).getString("category_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    callback.onItemPassed(position, predictionsJsonArray.getJSONObject(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return predictionsJsonArray.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProRegularTextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (ProRegularTextView) itemView.findViewById(R.id.tv);
        }
    }
}