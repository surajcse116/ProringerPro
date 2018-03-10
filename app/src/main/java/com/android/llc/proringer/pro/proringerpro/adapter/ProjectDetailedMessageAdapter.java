package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.main_content.ProjectMessagingFragment;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetProjectMessageDetails;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

/**
 * Created by bodhidipta on 13/06/17.
 * <!-- * Copyright (c) 2017, The Proringer-->
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ProjectDetailedMessageAdapter extends RecyclerView.Adapter<ProjectDetailedMessageAdapter.ViewHolder> {
    Context mcontext = null;
    private ProjectMessagingFragment.onOptionSelected listener;
    ArrayList<SetGetProjectMessageDetails> setGetProjectMessageDetailsArrayList;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public ProjectDetailedMessageAdapter(Context mcontext, ArrayList<SetGetProjectMessageDetails> setGetProjectMessageDetailsArrayList,  ProjectMessagingFragment.onOptionSelected  callback) {
        this.mcontext = mcontext;
        this.setGetProjectMessageDetailsArrayList = setGetProjectMessageDetailsArrayList;
        listener = callback;
    }

    @Override
    public int getItemCount() {
        return setGetProjectMessageDetailsArrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.project_detailed_messge_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (setGetProjectMessageDetailsArrayList != null && 0 <= position && position < setGetProjectMessageDetailsArrayList.size()) {
            // final String data = setGetProjectMessageDetailsArrayList.get(position).getTagName();
            SetGetProjectMessageDetails setgetProjectMessageDetails=setGetProjectMessageDetailsArrayList.get(position);
            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            //  binderHelper.bind(holder.swipe_layout, data);

            // Bind your data here
            holder.bind(position,setgetProjectMessageDetails);
            holder.tv_proj_name.setText(setgetProjectMessageDetails.getProject_name_active());
        }



        if (position % 3 == 0) {
            holder.flag.setVisibility(View.VISIBLE);
        } else {
            holder.flag.setVisibility(View.INVISIBLE);
        }
      /*  holder.main_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position, setGetProjectMessage.getProj_id());

            }
        });*/
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View flag;
        SwipeRevealLayout swipe_layout;
        RelativeLayout main_container;
        LinearLayout LLDelete,LLMore;
        ImageView prof_img;
        ProSemiBoldTextView tv_proj_name;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_proj_name=itemView.findViewById(R.id.tv_proj_name);
            prof_img=itemView.findViewById(R.id.prof_img);
            flag = (View) itemView.findViewById(R.id.flag);
            LLMore = (LinearLayout) itemView.findViewById(R.id.LLMore);
            swipe_layout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            main_container = (RelativeLayout) itemView.findViewById(R.id.main_container);
            LLDelete = (LinearLayout) itemView.findViewById(R.id.LLDelete);
        }


        public void bind(final int position,  final SetGetProjectMessageDetails setgetProjectMessageDetails) {

            LLDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setGetProjectMessageDetailsArrayList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
            main_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position,setgetProjectMessageDetails.getProj_id_active());
                }
            });
            //textView.setText(data);
            tv_proj_name.setText(setgetProjectMessageDetails.getProject_name_active());
            Glide.with(mcontext).load(setgetProjectMessageDetails.getProj_img_active())
                    .placeholder(R.drawable.plumber)
                    .into(new GlideDrawableImageViewTarget(prof_img) {
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
    }


    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }
}