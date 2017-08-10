package com.android.llc.proringer.pro.proringerpro.viewsmod;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;


/**
 * Created by bodhidipta on 12/06/17.
 * <!-- * Copyright (c) 2017, Proringer-->
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
 * -->
 */

public class BottomNav {
    private static BottomNav ourInstance = null;
    private Context mContext;
    private onSelectListener listener;
    private View inflatedView;
    private ImageView dashboard_image, my_projects_image, messages_image, fav_pro_image;
    private ProRegularTextView dashboard_text, my_projects_text, messages_text, fav_pro_text;
    private LinearLayout dashboard_cont, my_projects_cont, messages_cont, fav_pro_cont,log_out_pro_cont;

    public static final String DASHBOARD = "dashboard", MY_PROJECTS = "my_project", MESSAGES = "messages", WATCH_LIST = "watch_list", CREATE_PROJECT = "create_project";

    public static BottomNav getInstance(Context context, View view) {
        return new BottomNav(context, view);
    }

    private BottomNav(Context context, View view) {
        mContext = context;
        inflatedView = view;

        dashboard_image = (ImageView) inflatedView.findViewById(R.id.dashboard_image);
        my_projects_image = (ImageView) inflatedView.findViewById(R.id.my_projects_image);
        messages_image = (ImageView) inflatedView.findViewById(R.id.messages_image);
        fav_pro_image = (ImageView) inflatedView.findViewById(R.id.fav_pro_image);

        dashboard_text = (ProRegularTextView) inflatedView.findViewById(R.id.dashboard_text);
        my_projects_text = (ProRegularTextView) inflatedView.findViewById(R.id.my_projects_text);
        messages_text = (ProRegularTextView) inflatedView.findViewById(R.id.messages_text);
        fav_pro_text = (ProRegularTextView) inflatedView.findViewById(R.id.fav_pro_text);

        dashboard_cont = (LinearLayout) inflatedView.findViewById(R.id.dashboard_cont);
        my_projects_cont = (LinearLayout) inflatedView.findViewById(R.id.my_projects_cont);
        messages_cont = (LinearLayout) inflatedView.findViewById(R.id.messages_cont);
        fav_pro_cont = (LinearLayout) inflatedView.findViewById(R.id.fav_pro_cont);

    }

    public void highLightSelected(String selected) {
        switchSelection(selected);
    }

    public void init(onSelectListener callback) {
        listener = callback;


        dashboard_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSelection(DASHBOARD);
                listener.onClick(DASHBOARD);
            }
        });
        my_projects_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSelection(MY_PROJECTS);
                listener.onClick(MY_PROJECTS);
            }
        });
        messages_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSelection(MESSAGES);
                listener.onClick(MESSAGES);
            }
        });
        fav_pro_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSelection(WATCH_LIST);
                listener.onClick(WATCH_LIST);
            }
        });


    }

    public interface onSelectListener {
        void onClick(String selected_tag);
    }

    private void switchSelection(String selcted) {
        switch (selcted) {
            case DASHBOARD:
                dashboard_image.setBackgroundResource(R.drawable.ic_dashboard_selected);
                dashboard_text.setTextColor(Color.parseColor("#f1592a"));

                my_projects_image.setBackgroundResource(R.drawable.ic_my_project);
                my_projects_text.setTextColor(Color.parseColor("#505050"));

                messages_image.setBackgroundResource(R.drawable.ic_message);
                messages_text.setTextColor(Color.parseColor("#505050"));

                fav_pro_image.setBackgroundResource(R.drawable.ic_fav_pro);
                fav_pro_text.setTextColor(Color.parseColor("#505050"));
                break;
            case MY_PROJECTS:
                dashboard_image.setBackgroundResource(R.drawable.ic_dashboard);
                dashboard_text.setTextColor(Color.parseColor("#505050"));

                my_projects_image.setBackgroundResource(R.drawable.ic_my_project_selected);
                my_projects_text.setTextColor(Color.parseColor("#f1592a"));

                messages_image.setBackgroundResource(R.drawable.ic_message);
                messages_text.setTextColor(Color.parseColor("#505050"));

                fav_pro_image.setBackgroundResource(R.drawable.ic_fav_pro);
                fav_pro_text.setTextColor(Color.parseColor("#505050"));
                break;
            case MESSAGES:
                dashboard_image.setBackgroundResource(R.drawable.ic_dashboard);
                dashboard_text.setTextColor(Color.parseColor("#505050"));

                my_projects_image.setBackgroundResource(R.drawable.ic_my_project);
                my_projects_text.setTextColor(Color.parseColor("#505050"));

                messages_image.setBackgroundResource(R.drawable.ic_message_selected);
                messages_text.setTextColor(Color.parseColor("#f1592a"));

                fav_pro_image.setBackgroundResource(R.drawable.ic_fav_pro);
                fav_pro_text.setTextColor(Color.parseColor("#505050"));
                break;
            case WATCH_LIST:
                dashboard_image.setBackgroundResource(R.drawable.ic_dashboard);
                dashboard_text.setTextColor(Color.parseColor("#505050"));

                my_projects_image.setBackgroundResource(R.drawable.ic_my_project);
                my_projects_text.setTextColor(Color.parseColor("#505050"));

                messages_image.setBackgroundResource(R.drawable.ic_message);
                messages_text.setTextColor(Color.parseColor("#505050"));

                fav_pro_image.setBackgroundResource(R.drawable.ic_fav_pro_selected);
                fav_pro_text.setTextColor(Color.parseColor("#f1592a"));
                break;
            case CREATE_PROJECT:
                dashboard_image.setBackgroundResource(R.drawable.ic_dashboard);
                dashboard_text.setTextColor(Color.parseColor("#505050"));

                my_projects_image.setBackgroundResource(R.drawable.ic_my_project);
                my_projects_text.setTextColor(Color.parseColor("#505050"));

                messages_image.setBackgroundResource(R.drawable.ic_message);
                messages_text.setTextColor(Color.parseColor("#505050"));

                fav_pro_image.setBackgroundResource(R.drawable.ic_fav_pro);
                fav_pro_text.setTextColor(Color.parseColor("#505050"));
                break;
            default:

        }

    }

}
