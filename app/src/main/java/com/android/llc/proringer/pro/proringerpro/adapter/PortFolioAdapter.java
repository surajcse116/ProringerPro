package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LicenceAddActivity;
import com.android.llc.proringer.pro.proringerpro.activities.PortFolioActivity;
import com.android.llc.proringer.pro.proringerpro.activities.PortfolioEditActivity;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetShowPortFolio;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 8/17/17.
 */

public class PortFolioAdapter extends RecyclerView.Adapter<PortFolioAdapter.MyViewHolder> {
    Context mContext;
    private ArrayList<SetGetShowPortFolio> arrlist;
    public PortFolioAdapter(Context mContext,ArrayList<SetGetShowPortFolio> arrlist){
        this.mContext=mContext;
        this.arrlist=arrlist;
    }
    @Override
    public PortFolioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.portfoliophoto, parent, false));

    }

    @Override
    public void onBindViewHolder(PortFolioAdapter.MyViewHolder holder, final int position) {
        Picasso.with(mContext).load(arrlist.get(position).getGallery_image()).into(holder.img);
        holder.tv_name.setText(arrlist.get(position).getCategory_name());
        holder.tv_date.setText(arrlist.get(position).getProject_month()+arrlist.get(position).getProject_year());
        holder.tv_number.setText(arrlist.get(position).getCount_images());


        holder.RLMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,PortfolioEditActivity.class);
                intent.putExtra("id",arrlist.get(position).getId());
                intent.putExtra("cat_id",arrlist.get(position).getCategory_id());
                intent.putExtra("category_name",arrlist.get(position).getCategory_name());
                intent.putExtra("month",arrlist.get(position).getProject_month());
                intent.putExtra("year",arrlist.get(position).getProject_year());
                intent.putExtra("multiple_gallery_image",""+arrlist.get(position).getMultiple_gallery_image());
                intent.putExtra("categoryJsonArray",""+((PortFolioActivity)mContext).categoryJsonArray);

                ((PortFolioActivity)mContext).startActivityForResult(intent,111);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        RelativeLayout RLMain;
        ProSemiBoldTextView tv_name,tv_date,tv_number;
        public MyViewHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.img);
            tv_name=(ProSemiBoldTextView)itemView.findViewById(R.id.tv_name);
            tv_date=(ProSemiBoldTextView)itemView.findViewById(R.id.tv_date);
            tv_number=(ProSemiBoldTextView)itemView.findViewById(R.id.tv_number);
            RLMain=(RelativeLayout) itemView.findViewById(R.id.RLMain);
        }
    }
}
