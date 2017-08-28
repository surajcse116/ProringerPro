package com.android.llc.proringer.pro.proringerpro.viewsmod;

import android.graphics.Color;
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
            FIND_LOCAL_PROJECT = "find_local_project",
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
    private LinearLayout find_local_project_cont, support_cont, about_cont, account_cont,
            LLAccount, LLSupport, LLAbout;
    private ImageView find_local_project_img, support_img, about_img, account_img;
    private ProBoldTextView find_local_project_text, support_text, about_text, account_text;
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
        find_local_project_cont = (LinearLayout) view.findViewById(R.id.find_local_project_cont);
        account_cont = (LinearLayout) view.findViewById(R.id.account_cont);
        support_cont = (LinearLayout) view.findViewById(R.id.support_cont);
        about_cont = (LinearLayout) view.findViewById(R.id.about_cont);


        LLAccount = (LinearLayout) view.findViewById(R.id.LLAccount);
        LLSupport = (LinearLayout) view.findViewById(R.id.LLSupport);
        LLAbout = (LinearLayout) view.findViewById(R.id.LLAbout);

        find_local_project_img = (ImageView) view.findViewById(R.id.find_local_project_img);
        account_img = (ImageView) view.findViewById(R.id.account_img);
        support_img = (ImageView) view.findViewById(R.id.support_img);
        about_img = (ImageView) view.findViewById(R.id.about_img);


        find_local_project_text = (ProBoldTextView) view.findViewById(R.id.find_local_project_text);
        account_text = (ProBoldTextView) view.findViewById(R.id.account_text);
        support_text = (ProBoldTextView) view.findViewById(R.id.support_text);
        about_text = (ProBoldTextView) view.findViewById(R.id.about_text);

        find_local_project_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(FIND_LOCAL_PROJECT);
            }
        });

        account_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(ACCOUNT);

                LLSupport.setVisibility(View.GONE);
                LLAbout.setVisibility(View.GONE);

                if (LLAccount.getVisibility() == View.VISIBLE) {
                    LLAccount.setVisibility(View.GONE);
                } else {
                    LLAccount.setVisibility(View.VISIBLE);
                }
            }
        });

        RLQuick_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(QUICK_REPLY);
            }
        });
        RLNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(NOTIFICATION);
            }
        });
        RLAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(AVAILABLE_TIME_SLOT);
            }
        });
        RLSocialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(SOCIAL_MEDIA);
            }
        });
        RLShare_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(SHARE_PROFILE);
            }
        });

        RLRequest_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(REQUEST_REVIEW);
            }
        });
        RLInvite_a_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(INVITE_FRIEND);
            }
        });

        RLLog_out_pro_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(LOGOUT);
            }
        });


        support_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(SUPPORT);
                LLAccount.setVisibility(View.GONE);
                LLAbout.setVisibility(View.GONE);

                if (LLSupport.getVisibility() == View.VISIBLE) {
                    LLSupport.setVisibility(View.GONE);
                } else {
                    LLSupport.setVisibility(View.VISIBLE);
                }
            }
        });
        RLEmailSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(EMAIL_SUPPORT);
            }
        });

        RLFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(FAQ);
            }
        });
        RLProvideFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(PROVIDE_FEEDBACK);
            }
        });


        about_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(ABOUT);
                LLAccount.setVisibility(View.GONE);
                LLSupport.setVisibility(View.GONE);

                if (LLAbout.getVisibility() == View.VISIBLE) {
                    LLAbout.setVisibility(View.GONE);
                } else {
                    LLAbout.setVisibility(View.VISIBLE);
                }
            }
        });
        RLTermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(TERMS_OF_SERVICE);
            }
        });

        RLPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(PRIVACY_POLICY);
            }
        });


        handleViewInput(listener);
    }

    private void handleViewInput(OnHandleInput callback) {
        listener = callback;
    }


    public void highlightTag(String tag) {
        switch (tag) {
            case FIND_LOCAL_PROJECT:

                listener.onClickItem(FIND_LOCAL_PROJECT);

                find_local_project_cont.setBackgroundColor(Color.parseColor("#656565"));
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro_white);
                find_local_project_text.setTextColor(Color.WHITE);

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));


                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));


                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;

            case ACCOUNT:
                listener.onClickItem(ACCOUNT);

                //userInformation.setBackgroundColor(Color.parseColor("#656565"));

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.parseColor("#656565"));
                account_img.setBackgroundResource(R.drawable.ic_settings_white);
                account_text.setTextColor(Color.WHITE);

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_search_pro);
                about_text.setTextColor(Color.parseColor("#505050"));


                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

            case NOTIFICATION:
                listener.onClickItem(NOTIFICATION);

                RLNotifications.setBackgroundColor(Color.parseColor("#656565"));

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;

            case QUICK_REPLY:

                listener.onClickItem(QUICK_REPLY);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.parseColor("#656565"));
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;

            case AVAILABLE_TIME_SLOT:
                listener.onClickItem(AVAILABLE_TIME_SLOT);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.parseColor("#656565"));
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;

            case SOCIAL_MEDIA:
                listener.onClickItem(SOCIAL_MEDIA);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.parseColor("#656565"));
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;


            case SHARE_PROFILE:
                listener.onClickItem(SHARE_PROFILE);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.parseColor("#656565"));
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;

            case REQUEST_REVIEW:
                listener.onClickItem(REQUEST_REVIEW);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.parseColor("#656565"));
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;

            case INVITE_FRIEND:
                listener.onClickItem(INVITE_FRIEND);
                RLInvite_a_friend.setBackgroundColor(Color.parseColor("#656565"));

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.parseColor("#656565"));

                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;


            case LOGOUT:
                listener.onClickItem(LOGOUT);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.parseColor("#656565"));
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;

            case SUPPORT:
                listener.onClickItem(SUPPORT);

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.parseColor("#656565"));
                support_img.setBackgroundResource(R.drawable.ic_support_white);
                support_text.setTextColor(Color.WHITE);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));


                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;


            case EMAIL_SUPPORT:

                listener.onClickItem(EMAIL_SUPPORT);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.parseColor("#656565"));
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;

            case FAQ:
                listener.onClickItem(FAQ);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.parseColor("#656565"));
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;

            case PROVIDE_FEEDBACK:

                listener.onClickItem(PROVIDE_FEEDBACK);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.parseColor("#656565"));
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);


                break;

            case TERMS_OF_SERVICE:
                listener.onClickItem(TERMS_OF_SERVICE);

                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));


                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.parseColor("#656565"));
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;

            case PRIVACY_POLICY:
                listener.onClickItem(PRIVACY_POLICY);


                find_local_project_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_project_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_project_text.setTextColor(Color.parseColor("#505050"));

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));

                RLNotifications.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RLSocialMedia.setBackgroundColor(Color.TRANSPARENT);
                RLShare_profile.setBackgroundColor(Color.TRANSPARENT);
                RLRequest_review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_a_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out_pro_cont.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFAQ.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.parseColor("#656565"));

                break;


            default:

        }
    }


    public interface OnHandleInput {
        void onClickItem(String tag);
    }
}
