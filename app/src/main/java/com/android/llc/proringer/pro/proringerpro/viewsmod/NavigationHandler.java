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
    public static final String
            ACCOUNT = "account",
            SUPPORT = "support",
            ABOUT = "about",
            REQUEST_REVIEW = "request_review",
            QUICK_REPLY = "find_local_pros",
            NOTIFICATION = "notification",
            INVITE_FRIEND = "invite_friend",
            SHARE_PROFILE = "share_profile",
            LOGOUT = "log_out",
            AVAILABLE_TIME_SLOT = "available_time_slot",
            SOCIAL_MEDIA = "social_media",
            EMAIL_SUPPORT = "email_support",
            FAQ = "faq",
            PROVIDE_FEEDBACK = "provide_feedback",
            TERMS_OF_SERVICE = "terms_of_service",
            PRIVACY_POLICY = "privacy_policy";
    private static NavigationHandler instance = null;
    private View inflatedView = null;
    private OnHandleInput listener = null;
    private LinearLayout find_local_pros_cont, support_cont, about_cont;
    private ImageView find_local_pros_img, support_img, about_img;
    private ProBoldTextView find_local_pros_text, support_text, about_text;
    private RelativeLayout RLNotifications, RLInvite_a_friend, RLQuick_reply, RLRequest_review, RLAvailability,
            RLLog_out_pro_cont, RLSocialMedia, RLEmailSupport, RLShare_profile,
            RLFAQ, RLProvideFeedback, RLTermsOfService, RLPrivacyPolicy;


    private NavigationHandler() {
    }

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
        RLNotifications = (RelativeLayout) view.findViewById(R.id.RLNotifications);
        RLQuick_reply = (RelativeLayout) view.findViewById(R.id.RLQuick_reply);
        RLInvite_a_friend = (RelativeLayout) view.findViewById(R.id.RLInvite_a_friend);
        RLRequest_review = (RelativeLayout) view.findViewById(R.id.RLRequest_review);
        RLAvailability = (RelativeLayout) view.findViewById(R.id.RLAvailability);
        RLLog_out_pro_cont = (RelativeLayout) view.findViewById(R.id.RLLog_out_pro_cont);
        RLSocialMedia = (RelativeLayout) view.findViewById(R.id.RLSocialMedia);
        RLShare_profile = (RelativeLayout) view.findViewById(R.id.RLShare_profile);
        RLEmailSupport = (RelativeLayout) view.findViewById(R.id.RLEmailSupport);
        RLFAQ = (RelativeLayout) view.findViewById(R.id.RLFAQ);
        RLProvideFeedback = (RelativeLayout) view.findViewById(R.id.RLProvideFeedback);
        RLTermsOfService = (RelativeLayout) view.findViewById(R.id.RLTermsOfService);
        RLPrivacyPolicy = (RelativeLayout) view.findViewById(R.id.RLPrivacyPolicy);


        RLRequest_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(NOTIFICATION);
                listener.onClickItem(REQUEST_REVIEW);
            }
        });
        RLNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(NOTIFICATION);
                listener.onClickItem(NOTIFICATION);
            }
        });

        RLInvite_a_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  highlightTag(INVITE_FRIEND);
                listener.onClickItem(INVITE_FRIEND);
            }
        });

        RLQuick_reply.setOnClickListener(new View.OnClickListener() {
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
                listener.onClickItem(AVAILABLE_TIME_SLOT);
            }
        });

        RLSocialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(SOCIAL_MEDIA);
            }
        });

        RLLog_out_pro_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(LOGOUT);
            }
        });


        RLEmailSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(EMAIL_SUPPORT);
            }
        });

        RLFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(FAQ);
            }
        });

        RLProvideFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(PROVIDE_FEEDBACK);
            }
        });

        RLTermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(TERMS_OF_SERVICE);
            }
        });

        RLPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(PRIVACY_POLICY);
            }
        });
        RLShare_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(SHARE_PROFILE);
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

    private void handleViewInput(OnHandleInput callback) {
        listener = callback;
    }


//    public void highlightTag(String tag) {
//        switch (tag) {
//            case FIND_LOCAL_PROS:
//
//                listener.onClickItem(FIND_LOCAL_PROS);
//
//                find_local_pros_cont.setBackgroundColor(Color.parseColor("#656565"));
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro_white);
//                find_local_pros_text.setTextColor(Color.WHITE);
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
//
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//
//                break;
//
//            case ACCOUNT:
//                listener.onClickItem(ACCOUNT);
//
//                //userInformation.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                account_cont.setBackgroundColor(Color.parseColor("#656565"));
//                account_img.setBackgroundResource(R.drawable.ic_settings_white);
//                account_text.setTextColor(Color.WHITE);
//
//                support_cont.setBackgroundColor(Color.TRANSPARENT);
//                support_img.setBackgroundResource(R.drawable.ic_support);
//                support_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                about_cont.setBackgroundColor(Color.TRANSPARENT);
//                about_img.setBackgroundResource(R.drawable.ic_search_pro);
//                about_text.setTextColor(Color.parseColor("#505050"));
//
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//
//            case USER_INFORMATION:
//                listener.onClickItem(USER_INFORMATION);
//                userInformation.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
//
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
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
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
//
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
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
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
//
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
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
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
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
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                userInformation.setBackgroundColor(Color.TRANSPARENT);
//                login_settings.setBackgroundColor(Color.TRANSPARENT);
//                notification.setBackgroundColor(Color.TRANSPARENT);
//                home_scheduler.setBackgroundColor(Color.TRANSPARENT);
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case SUPPORT:
//                listener.onClickItem(SUPPORT);
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
//
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case ABOUT:
//                listener.onClickItem(ABOUT);
//
//                about_cont.setBackgroundColor(Color.parseColor("#656565"));
//                about_img.setBackgroundResource(R.drawable.ic_about_white);
//                about_text.setTextColor(Color.WHITE);
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
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
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                invite_friend.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//                break;
//
//            case Email_Support:
//
//                listener.onClickItem(Email_Support);
//
//                RLEmailSupport.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//
//
//                break;
//
//            case Faq:
//                listener.onClickItem(Faq);
//
//                RLFaq.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                log_out.setBackgroundColor(Color.TRANSPARENT);
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//                break;
//
//            case Provider_Feedback:
//
//                listener.onClickItem(Provider_Feedback);
//
//                RLProviderFeedback.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//
//                break;
//
//            case Terms_Of_Service:
//
//                listener.onClickItem(Terms_Of_Service);
//
//                RLTerms.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
//
//                break;
//
//            case Privacy_Policy:
//
//                listener.onClickItem(Privacy_Policy);
//
//                RLPrivacyPolicy.setBackgroundColor(Color.parseColor("#656565"));
//
//                find_local_pros_cont.setBackgroundColor(Color.TRANSPARENT);
//                find_local_pros_img.setBackgroundResource(R.drawable.ic_search_pro);
//                find_local_pros_text.setTextColor(Color.parseColor("#505050"));
//
//                account_cont.setBackgroundColor(Color.TRANSPARENT);
//                account_img.setBackgroundResource(R.drawable.ic_settings);
//                account_text.setTextColor(Color.parseColor("#505050"));
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
//                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
//                RLFaq.setBackgroundColor(Color.TRANSPARENT);
//                RLProviderFeedback.setBackgroundColor(Color.TRANSPARENT);
//                RLTerms.setBackgroundColor(Color.TRANSPARENT);
//
//
//                break;
//
//            default:
//
//        }
//    }


    public interface OnHandleInput {
        void onClickItem(String tag);
    }
}
