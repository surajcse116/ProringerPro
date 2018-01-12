package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.pojo.SetgetmenuItem;

import java.util.ArrayList;

/**
 * Created by su on 11/1/18.
 */

public class ResponseDialogMenuAdapter extends RecyclerView.Adapter<ResponseDialogMenuAdapter.ViewHolder>{
    Context context;
    ArrayList<SetgetmenuItem> arrayList;

    public ResponseDialogMenuAdapter(Context context, ArrayList<SetgetmenuItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_dialog_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SetgetmenuItem setgetmenuItem= (SetgetmenuItem) arrayList.get(position);

        holder.text.setText(setgetmenuItem.getText());
       // Picasso.with(context).load(setgetmenuItem.getImage()).into(holder.imageView);
      /*  Glide.with(context).load(((SetgetmenuItem) arrayList.get(position)).getImage()).fitCenter()
                .into(holder.imageView);*/
      holder.imageView.setImageResource(setgetmenuItem.getImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
