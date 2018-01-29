package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.MessageFragment;
import com.android.llc.proringer.pro.proringerpro.helper.onItemClick;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetProjectMessage;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

/**
 * Created by bodhidipta on 12/06/17.
 * <!-- * Copyright (c) 2017, Proringer -->
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

public class ProjectMessageAdapter extends RecyclerView.Adapter<ProjectMessageAdapter.ViewHolder> {
    private Context mcontext;
    private MessageFragment.onOptionSelected listener;
    ArrayList<SetGetProjectMessage> setGetProjectMessageArrayList;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public ProjectMessageAdapter(Context mcontext, ArrayList<SetGetProjectMessage> setGetProjectMessageArrayList, MessageFragment.onOptionSelected calback) {
        this.mcontext = mcontext;
        listener = calback;
        this.setGetProjectMessageArrayList = setGetProjectMessageArrayList;
        Log.d("a_messageListSiz",""+setGetProjectMessageArrayList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.message_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (setGetProjectMessageArrayList != null && 0 <= position && position < setGetProjectMessageArrayList.size()) {

            //final String data = setGetProjectMessageArrayList.get(position).getTagName();

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
          //  binderHelper.bind(holder.swipe_layout, data);

            // Bind your data here



            SetGetProjectMessage setGetProjectMessage = setGetProjectMessageArrayList.get(position);
            holder.bind(position,setGetProjectMessage);
            holder.project_name.setText(setGetProjectMessage.getProj_name());
            holder.tv_date.setText(setGetProjectMessage.getProject_applied_date());
            Glide.with(mcontext).load(setGetProjectMessage.getProj_image())
                    .placeholder(R.drawable.plumber)
                    .into(new GlideDrawableImageViewTarget(holder.project_type_img) {
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
            if (setGetProjectMessageArrayList.get(position).getProject_status().equals("A"))
            {
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText("Active");
            }
            else
            {
                holder.status.setVisibility(View.GONE);
            }

        }


        if (position % 3 == 0) {
            holder.flag.setVisibility(View.VISIBLE);
        } else {
            holder.flag.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        if (setGetProjectMessageArrayList == null) {
            return 0;
        } else {
            Log.d("messageListSize", ""+setGetProjectMessageArrayList.size());
            return setGetProjectMessageArrayList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View flag;
        SwipeRevealLayout swipe_layout;
        RelativeLayout main_container;
        LinearLayout delete_layout;
        ProSemiBoldTextView project_name;
        ProRegularTextView tv_date;

        ImageView project_type_img;
        ProSemiBoldTextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.status);
            tv_date=itemView.findViewById(R.id.tv_date);
            project_name=itemView.findViewById(R.id.project_name);
            flag = (View) itemView.findViewById(R.id.flag);
            project_type_img=itemView.findViewById(R.id.project_type_img);
            swipe_layout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            main_container = (RelativeLayout) itemView.findViewById(R.id.main_container);
            delete_layout = (LinearLayout) itemView.findViewById(R.id.delete_layout);
        }



        public void bind(final int position, final SetGetProjectMessage setGetProjectMessage) {

            delete_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setGetProjectMessageArrayList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            //textView.setText(data);
            main_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position,setGetProjectMessage.getProj_id());
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