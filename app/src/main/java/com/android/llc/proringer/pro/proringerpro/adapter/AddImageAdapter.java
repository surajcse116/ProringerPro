package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.PortFolioActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;

/**
 * Created by su on 8/17/17.
 */

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> portPolioImageGalleryArrayList;
    int screenHeight;
    int screenWidth;

    public AddImageAdapter(Context mContext, ArrayList<String> portPolioImageGalleryArrayList) {
        this.mContext=mContext;
        this.portPolioImageGalleryArrayList = portPolioImageGalleryArrayList;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((PortFolioActivity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }
    @Override
    public AddImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_add_image, parent, false));

    }

    @Override
    public void onBindViewHolder(AddImageAdapter.MyViewHolder holder, int position) {

        holder.img.getLayoutParams().width=screenWidth/5;
        holder.img.getLayoutParams().height=screenWidth/5;

        Glide.with(mContext).load("file://" + portPolioImageGalleryArrayList.get(position)).fitCenter().into(new GlideDrawableImageViewTarget(holder.img) {
            /**
             * {@inheritDoc}
             * If no {@link GlideAnimation} is given or if the animation does not set the
             * {@link Drawable} on the view, the drawable is set using
             * {@link ImageView#setImageDrawable(Drawable)}.
             *
             * @param resource  {@inheritDoc}
             * @param animation {@inheritDoc}
             */
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return portPolioImageGalleryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.img);
        }
    }
}
