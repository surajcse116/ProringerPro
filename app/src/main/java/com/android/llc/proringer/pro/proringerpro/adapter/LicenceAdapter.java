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
import com.android.llc.proringer.pro.proringerpro.activities.LicenceEditActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LicenceListActivity;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetShowLicence;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 8/18/17.
 */

public class LicenceAdapter extends RecyclerView.Adapter<LicenceAdapter.MyViewHolder> {
    //    ArrayList<PortFolio> portFolioArrayList;
    Context mContext;

    private ArrayList<SetGetShowLicence> arrlist;

    public LicenceAdapter(Context mContext, ArrayList<SetGetShowLicence> arrlist) {
        this.mContext = mContext;
        this.arrlist = arrlist;
        //this.portFolioArrayList=portFolioArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_port_folio, parent, false));

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Logger.printMessage("Position-->", "" + position);
        Picasso.with(mContext).load(arrlist.get(position).getImage_info()).into(holder.img);
        holder.tv_name.setText(arrlist.get(position).getCategory_name());
        holder.tv_date.setText(arrlist.get(position).getLicense_issuer());
        holder.tv_expire.setText(arrlist.get(position).getLicenses_no());
        holder.tv_number.setText(arrlist.get(position).getDate_expire());
        holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = position;
                Logger.printMessage("position-->", String.valueOf(pos));
                Intent intent = new Intent(mContext, LicenceEditActivity.class);
                String id = arrlist.get(position).getId();
                String catgoryname = arrlist.get(position).getCategory_name();
                String issuer = arrlist.get(position).getLicense_issuer();
                String licenseno = arrlist.get(position).getLicenses_no();
                String expiredate = arrlist.get(position).getDate_expire();
                String photo = arrlist.get(position).getImage_info();
                String catid = arrlist.get(position).getCategory_id();
                intent.putExtra("category", catgoryname);
                intent.putExtra("issuer", issuer);
                intent.putExtra("licenseno", licenseno);
                intent.putExtra("expiredate", expiredate);
                intent.putExtra("id", id);
                intent.putExtra("photo", photo);
                intent.putExtra("catid", catid);
                Logger.printMessage("categoryName", catgoryname);
                ((LicenceListActivity)mContext).startActivityForResult(intent,100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        RelativeLayout rl_main;
        ProSemiBoldTextView tv_name, tv_date, tv_number, tv_expire;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tv_name = (ProSemiBoldTextView) itemView.findViewById(R.id.tv_name);
            tv_date = (ProSemiBoldTextView) itemView.findViewById(R.id.tv_date);
            tv_number = (ProSemiBoldTextView) itemView.findViewById(R.id.tv_number);
            tv_expire = (ProSemiBoldTextView) itemView.findViewById(R.id.tv_expire);
            rl_main = (RelativeLayout) itemView.findViewById(R.id.rl_main);
        }
    }
}
