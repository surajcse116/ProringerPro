package com.android.llc.proringer.pro.proringerpro.viewsmod;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProBoldTextView;


/**
 * Created by bodhidipta on 09/06/17.
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

public class NavigationHandler {
    private View inflatedView = null;
    private OnHandleInput listener = null;
    private static NavigationHandler instance = null;

    private LinearLayout find_local_pros_cont, support_cont, about_cont;
    private ImageView find_local_pros_img, support_img, about_img;
    private ProBoldTextView find_local_pros_text, support_text, about_text;

    private RelativeLayout notifications, invite_a_friend, quick_reply, request_review, RLAvailability,log_out_pro_cont;

    public static final String
            REQUEST_REVIEW = "request_review",
            QUICK_REPLY = "find_local_pros",
            NOTIFICATION = "noti",
            INVITE_FRIEND = "invite_fr",
            LOGOUT = "log_out",
            AvailableTimeSlot = "available_time_slot";


    public static NavigationHandler getInstance() {
        if (instance == null)
            instance = new NavigationHandler();
        return instance;
    }

    public void init(View view, final OnHandleInput listener) {
        inflatedView = view;
        /**
         * Id's need to be bound with xml, xml id changes are necessary
         */
//        find_local_pros_cont = (LinearLayout) view.findViewById(R.id.find_local_pros_cont);
//        find_local_pros_img = (ImageView) view.findViewById(R.id.find_local_pros_img);
//        find_local_pros_text = (ProBoldTextView) view.findViewById(R.id.find_local_pros_text);
//
//        support_cont = (LinearLayout) view.findViewById(R.id.support_cont);
//        support_img = (ImageView) view.findViewById(R.id.support_img);
//        support_text = (ProBoldTextView) view.findViewById(R.id.support_text);
//
//        about_cont = (LinearLayout) view.findViewById(R.id.about_cont);
//        about_img = (ImageView) view.findViewById(R.id.about_img);
//        about_text = (ProBoldTextView) view.findViewById(R.id.about_text);
//
//        userInformation = (RelativeLayout) view.findViewById(R.id.userInformation);
//        login_settings = (RelativeLayout) view.findViewById(R.id.login_settings);
//        notification = (RelativeLayout) view.findViewById(R.id.notification);
        notifications = (RelativeLayout) view.findViewById(R.id.notifications);
        invite_a_friend = (RelativeLayout) view.findViewById(R.id.invite_a_friend);
        quick_reply = (RelativeLayout) view.findViewById(R.id.quick_reply);
        request_review = (RelativeLayout) view.findViewById(R.id.request_review);
        RLAvailability = (RelativeLayout) view.findViewById(R.id.RLAvailability);
        log_out_pro_cont = (RelativeLayout) view.findViewById(R.id.log_out_pro_cont);


        request_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(NOTIFICATION);
                listener.onClickItem(REQUEST_REVIEW);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(NOTIFICATION);
                listener.onClickItem(NOTIFICATION);
            }
        });

        invite_a_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(INVITE_FRIEND);
                listener.onClickItem(INVITE_FRIEND);
            }
        });

        quick_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(QUICK_REPLY);
                listener.onClickItem(QUICK_REPLY);
            }
        });

        RLAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(QUICK_REPLY);
                listener.onClickItem(AvailableTimeSlot);
            }
        });

        log_out_pro_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(LOGOUT);
            }
        });

//        userInformation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                highlightTag(USER_INFORMATION);
//            }
//        });
//
//
//        login_settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                highlightTag(LOGIN_SETTINGS);
//            }
//        });
//
//        notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                highlightTag(NOTIFICATION);
//            }
//        });
//
//        home_scheduler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                highlightTag(HOME_SCHEDUL);
//            }
//        });
//        invite_friend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                highlightTag(INVITE_FRIEND);
//            }
//        });
//        log_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                highlightTag(LOGOUT);
//            }
//        });
//

        handleViewInput(listener);
    }

    private NavigationHandler() {
    }

    private void handleViewInput(OnHandleInput callback) {
        listener = callback;
    }

//    public void highlightTag(String tag) {
//        switch (tag) {
//            case FIND_LOCAL_PROS:
//
//                find_local_pros_cont.setBackgroundColor(Color.parseColor("#656565"));
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro_white);
//                find_local_pros_text.setTextColor(Color.WHITE);
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                about_cont.setBackgroundColor(Color.TRANSPARENT);
//                about_img.setBackgroundResource(R.drawable.ic_about);
//                about_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                listener.onClickItem(FIND_LOCAL_PROS);
//                break;
//            case USER_INFORMATION:
//                listener.onClickItem(USER_INFORMATION);
//                userInformation.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case LOGIN_SETTINGS:
//                listener.onClickItem(LOGIN_SETTINGS);
//                login_settings.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case NOTIFICATION:
//                listener.onClickItem(NOTIFICATION);
//                notification.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case HOME_SCHEDUL:
//                listener.onClickItem(HOME_SCHEDUL);
//                home_scheduler.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case INVITE_FRIEND:
//                listener.onClickItem(INVITE_FRIEND);
//                invite_friend.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case SUPPORT:
//                listener.onClickItem(SUPPORT);
//                support_cont.setBackgroundColor(Color.parseColor("#656565"));
//                support_img.setBackgroundResource(R.drawable.ic_support_white);
//                support_text.setTextColor(Color.WHITE);
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                about_cont.setBackgroundColor(Color.TRANSPARENT);
//                about_img.setBackgroundResource(R.drawable.ic_about);
//                about_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case ABOUT:
//                listener.onClickItem(ABOUT);
//                about_cont.setBackgroundColor(Color.parseColor("#656565"));
//                about_img.setBackgroundResource(R.drawable.ic_about_white);
//                about_text.setTextColor(Color.WHITE);
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case LOGOUT:
//                listener.onClickItem(LOGOUT);
//                log_out.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            default:
//
//        }
//    }


    public interface OnHandleInput {
        void onClickItem(String tag);
    }
}
