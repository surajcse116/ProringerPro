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
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;

/**
 * Created by su on 8/17/17.
 */

public class PortFolioAddImageAdapter extends RecyclerView.Adapter<PortFolioAddImageAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> portPolioImageGalleryArrayList;
    int screenHeight;
    int screenWidth;

    public PortFolioAddImageAdapter(Context mContext, ArrayList<String> portPolioImageGalleryArrayList) {
        this.mContext = mContext;
        this.portPolioImageGalleryArrayList = portPolioImageGalleryArrayList;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((PortFolioActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_edit_add_image, parent, false));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.img.getLayoutParams().width = screenWidth / 5;
        holder.img.getLayoutParams().height = screenWidth / 5;

        if (!portPolioImageGalleryArrayList.get(position).startsWith("http")) {
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
        } else {
            Glide.with(mContext).load(portPolioImageGalleryArrayList.get(position)).fitCenter().into(new GlideDrawableImageViewTarget(holder.img) {
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
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(mContext, "Delete", "Are you sure to delete this image?", "YES,DELETE IT", "CANCEL", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        portPolioImageGalleryArrayList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void callBackCancel() {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return portPolioImageGalleryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img, img_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
        }
    }
}
