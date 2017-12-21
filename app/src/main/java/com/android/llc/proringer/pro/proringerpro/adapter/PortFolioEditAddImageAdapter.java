package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.PortfolioEditActivity;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.pojo.PortFolioImageSetgetGallery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;

/**
 * Created by su on 12/11/17.
 */

public class PortFolioEditAddImageAdapter extends RecyclerView.Adapter<PortFolioEditAddImageAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<PortFolioImageSetgetGallery> portFolioImageSetgetGalleries;
    int screenHeight;
    int screenWidth;

    public PortFolioEditAddImageAdapter(Context mContext, ArrayList<PortFolioImageSetgetGallery> portFolioImageSetgetGalleries) {
        this.mContext = mContext;
        this.portFolioImageSetgetGalleries = portFolioImageSetgetGalleries;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((PortfolioEditActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

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

        if (!portFolioImageSetgetGalleries.get(position).getImage_name().startsWith("http")) {
            Glide.with(mContext).load("file://" + portFolioImageSetgetGalleries.get(position).getImage_name()).fitCenter().into(new GlideDrawableImageViewTarget(holder.img) {
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
            Glide.with(mContext).load(portFolioImageSetgetGalleries.get(position).getImage_name()).fitCenter().into(new GlideDrawableImageViewTarget(holder.img) {
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
                customAlert.getEventFromNormalAlert(mContext, "Delete", "Are you sure to delete this image?", "YES, DELETE IT", "CANCEL", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        if (portFolioImageSetgetGalleries.get(position).getImage_name().startsWith("http")) {

                            if (((PortfolioEditActivity) mContext).imageExistCount>1){
                                ((PortfolioEditActivity) mContext).deletePortFolioImage(position, portFolioImageSetgetGalleries.get(position).getId());
                            }else {
                                Toast.makeText(mContext,"PortFolio must have one Image",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            portFolioImageSetgetGalleries.remove(position);
                            notifyDataSetChanged();
                        }
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
        return portFolioImageSetgetGalleries.size();
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
