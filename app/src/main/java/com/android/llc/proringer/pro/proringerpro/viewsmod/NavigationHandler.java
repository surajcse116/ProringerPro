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
    public static final String FIND_LOCAL_PROJECT = "find_local_projects",
            ACCOUNT = "account",
            UserInformation="User Information",
            Company="company",
            Service="Service",
            License="License",
            Portfolio="Portfolio",
            Servicarea="Servicarea",
            Loginsettings="Loginsettings",
            NOTIFICATION = "notification",
            QUICK_REPLY = "quick_reply",
            AVAILABILITY = "availability",
            SOCIAL_MEDIA = "social_media",
            SHARE_PROFILE = "share_profile",
            REQUEST_REVIEW = "request_review",
            INVITE_FRIEND = "invite_friend",
            My_Profile = "my_profile",
            Business_hours="Business_hour",
            TRANSACTION_HISTORY = "transaction_history",
            CAMPAIGNS_SUMMARY = "campaigns_summary",
            ANALYTICS = "analytics",
            LOGOUT = "log_out",

    SUPPORT = "support",
            EMAIL_SUPPORT = "email_support",
            FAQ = "faq",
            PROVIDE_FEEDBACK = "provide_feedback",

    ABOUT = "about",
            TERMS_OF_SERVICE = "terms_of_service",
            PRIVACY_POLICY = "privacy_policy";
    private static NavigationHandler instance = null;
    private View inflatedView = null;
    private OnHandleInput listener = null;
    private LinearLayout find_local_projects_cont, account_cont, support_cont, about_cont, LLAccount, LLSupport, LLAbout;
    private ImageView find_local_projects_img, account_img, support_img, about_img;
    private ProBoldTextView find_local_projects_text, account_text, support_text, about_text;
    private RelativeLayout RLuserinformation,RLcompany,RLservice,RLicense,RLPortfolio,RLservicearea,RLoginsettings,RLNotification,RLQuick_Reply, RLAvailability, RL_Social_Media, RL_Share_Profile, RL_Request_Review, RLInvite_friend, RLLog_out,
            RLEmailSupport, RLFaq, RLProvideFeedback, RLTermsOfService, RLPrivacyPolicy, RLTransactionHistory, RLCampaignSummary, RLMyProfile, RLAnalytics,RLbusinesshours;

    private NavigationHandler() {
    }

    public static NavigationHandler getInstance() {
        if (instance == null)
            instance = new NavigationHandler();
        return instance;
    }

    public void init(View view, OnHandleInput listener) {
        inflatedView = view;

        find_local_projects_cont = (LinearLayout) view.findViewById(R.id.find_local_projects_cont);
        find_local_projects_img = (ImageView) view.findViewById(R.id.find_local_projects_img);
        find_local_projects_text = (ProBoldTextView) view.findViewById(R.id.find_local_projects_text);

        account_cont = (LinearLayout) view.findViewById(R.id.account_cont);
        account_img = (ImageView) view.findViewById(R.id.account_img);
        account_text = (ProBoldTextView) view.findViewById(R.id.account_text);

        support_cont = (LinearLayout) view.findViewById(R.id.support_cont);
        support_img = (ImageView) view.findViewById(R.id.support_img);
        support_text = (ProBoldTextView) view.findViewById(R.id.support_text);

        about_cont = (LinearLayout) view.findViewById(R.id.about_cont);
        about_img = (ImageView) view.findViewById(R.id.about_img);
        about_text = (ProBoldTextView) view.findViewById(R.id.about_text);

        RLuserinformation=(RelativeLayout)view.findViewById(R.id.RLuserinformation);
        RLcompany=(RelativeLayout)view.findViewById(R.id.RLcompany);
        RLservice=(RelativeLayout)view.findViewById(R.id.RLservice);
        RLicense=(RelativeLayout)view.findViewById(R.id.RLicense);
        RLPortfolio=(RelativeLayout)view.findViewById(R.id.RLPortfolio);
        RLservicearea=(RelativeLayout)view.findViewById(R.id.RLservicearea);
        RLoginsettings=(RelativeLayout)view.findViewById(R.id.RLoginsettings);

        RLNotification = (RelativeLayout) view.findViewById(R.id.RLNotification);
        RLQuick_Reply = (RelativeLayout) view.findViewById(R.id.RLQuick_Reply);
        RLAvailability = (RelativeLayout) view.findViewById(R.id.RLAvailability);
        RLInvite_friend = (RelativeLayout) view.findViewById(R.id.RLInvite_friend);
        RL_Share_Profile = (RelativeLayout) view.findViewById(R.id.RL_Share_Profile);
        RL_Social_Media = (RelativeLayout) view.findViewById(R.id.RL_Social_Media);
        RL_Request_Review = (RelativeLayout) view.findViewById(R.id.RL_Request_Review);
        RLLog_out = (RelativeLayout) view.findViewById(R.id.RLLog_out);
        RLEmailSupport = (RelativeLayout) view.findViewById(R.id.RLEmailSupport);
        RLFaq = (RelativeLayout) view.findViewById(R.id.RLFaq);
        RLProvideFeedback = (RelativeLayout) view.findViewById(R.id.RLProvideFeedback);
        RLTermsOfService = (RelativeLayout) view.findViewById(R.id.RLTermsOfService);
        RLPrivacyPolicy = (RelativeLayout) view.findViewById(R.id.RLPrivacyPolicy);
        RLMyProfile = (RelativeLayout) view.findViewById(R.id.RLMyProfile);
        RLTransactionHistory = (RelativeLayout) view.findViewById(R.id.RLTransactionHistory);
        RLCampaignSummary = (RelativeLayout) view.findViewById(R.id.RLCampaignSummary);
        RLbusinesshours=(RelativeLayout)view.findViewById(R.id.RLbusinesshours);
        RLAnalytics = (RelativeLayout) view.findViewById(R.id.RLAnalytics);

        LLAccount = (LinearLayout) view.findViewById(R.id.LLAccount);
        LLSupport = (LinearLayout) view.findViewById(R.id.LLSupport);
        LLAbout = (LinearLayout) view.findViewById(R.id.LLAbout);


        find_local_projects_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(FIND_LOCAL_PROJECT);
            }
        });
        account_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(ACCOUNT);
            }
        });
        support_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(SUPPORT);
            }
        });
        about_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(ABOUT);
            }
        });



        RLuserinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(UserInformation);
            }
        });

        RLcompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(Company);
            }
        });
        RLservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(Service);
            }
        });
        RLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(License);

            }
        });
        RLPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               highlightTag(Portfolio);
            }
        });
        RLservicearea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              highlightTag(Servicarea);
            }
        });
        RLoginsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              highlightTag(Loginsettings);
            }
        });
        RLNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(NOTIFICATION);
            }
        });


        RLAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(AVAILABILITY);
            }
        });

        RLQuick_Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(QUICK_REPLY);
            }
        });

        RLInvite_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightTag(INVITE_FRIEND);
            }
        });

        RL_Share_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(SHARE_PROFILE);
            }
        });
        RL_Social_Media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(SOCIAL_MEDIA);
            }
        });
        RL_Request_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(REQUEST_REVIEW);
            }
        });
        RLLog_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(LOGOUT);
            }
        });

        RLEmailSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(EMAIL_SUPPORT);
            }
        });
        RLFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(FAQ);
            }
        });

        RLProvideFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(PROVIDE_FEEDBACK);
            }
        });

        RLTermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(TERMS_OF_SERVICE);
            }
        });

        RLPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(PRIVACY_POLICY);
            }
        });

        RLMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(My_Profile);
            }
        });
        RLTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(TRANSACTION_HISTORY);
            }
        });

        RLCampaignSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(CAMPAIGNS_SUMMARY);
            }
        });
        RLbusinesshours.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              highlightTag(Business_hours);
            }
        });

        RLAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightTag(ANALYTICS);
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

                LLAccount.setVisibility(View.GONE);
                LLAbout.setVisibility(View.GONE);
                LLSupport.setVisibility(View.GONE);


                find_local_projects_cont.setBackgroundColor(Color.parseColor("#656565"));
                find_local_projects_img.setBackgroundResource(R.drawable.ic_search_pro_white);
                find_local_projects_text.setTextColor(Color.WHITE);

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));


                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));
                RLuserinformation.setBackgroundColor(Color.TRANSPARENT);
                RLcompany.setBackgroundColor(Color.TRANSPARENT);
                RLservice.setBackgroundColor(Color.TRANSPARENT);
                RLicense.setBackgroundColor(Color.TRANSPARENT);
                RLPortfolio.setBackgroundColor(Color.TRANSPARENT);
                RLservicearea.setBackgroundColor(Color.TRANSPARENT);
                RLoginsettings.setBackgroundColor(Color.TRANSPARENT);
                RLNotification.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_Reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RL_Social_Media.setBackgroundColor(Color.TRANSPARENT);
                RL_Share_Profile.setBackgroundColor(Color.TRANSPARENT);
                RL_Request_Review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFaq.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;

            case ACCOUNT:
                listener.onClickItem(ACCOUNT);

                if (LLAccount.getVisibility() == View.VISIBLE) {
                    LLAccount.setVisibility(View.GONE);
                    account_cont.setBackgroundColor(Color.TRANSPARENT);
                    account_img.setBackgroundResource(R.drawable.ic_settings);
                    account_text.setTextColor(Color.parseColor("#505050"));
                } else {
                    LLAccount.setVisibility(View.VISIBLE);
                    account_cont.setBackgroundColor(Color.parseColor("#7c7c7c"));
                    account_img.setBackgroundResource(R.drawable.ic_settings_white);
                    account_text.setTextColor(Color.WHITE);
                }

                find_local_projects_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_projects_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_projects_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));

                LLSupport.setVisibility(View.GONE);
                LLAbout.setVisibility(View.GONE);

                RLuserinformation.setBackgroundColor(Color.TRANSPARENT);
                RLcompany.setBackgroundColor(Color.TRANSPARENT);
                RLservice.setBackgroundColor(Color.TRANSPARENT);
                RLicense.setBackgroundColor(Color.TRANSPARENT);
                RLPortfolio.setBackgroundColor(Color.TRANSPARENT);
                RLservicearea.setBackgroundColor(Color.TRANSPARENT);
                RLoginsettings.setBackgroundColor(Color.TRANSPARENT);
                RLNotification.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_Reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RL_Social_Media.setBackgroundColor(Color.TRANSPARENT);
                RL_Share_Profile.setBackgroundColor(Color.TRANSPARENT);
                RL_Request_Review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFaq.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
                break;

            case UserInformation:
                listener.onClickItem(UserInformation);
                RLuserinformation.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case Company:
                listener.onClickItem(Company);
                RLcompany.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case Service:
                listener.onClickItem(Service);
                RLservice.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case License:
                listener.onClickItem(License);
                RLicense.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case Portfolio:
                listener.onClickItem(Portfolio);
                RLPortfolio.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case Servicarea:
                listener.onClickItem(Servicarea);
                RLservicearea.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case Loginsettings:
                listener.onClickItem(Loginsettings);
                RLservicearea.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case NOTIFICATION:
                listener.onClickItem(NOTIFICATION);
                RLNotification.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case QUICK_REPLY:
                listener.onClickItem(QUICK_REPLY);
                RLQuick_Reply.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case AVAILABILITY:
                listener.onClickItem(AVAILABILITY);
                RLAvailability.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case SOCIAL_MEDIA:
                listener.onClickItem(SOCIAL_MEDIA);
                RL_Social_Media.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case SHARE_PROFILE:
                listener.onClickItem(SHARE_PROFILE);
                RL_Share_Profile.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case REQUEST_REVIEW:
                listener.onClickItem(REQUEST_REVIEW);
                RL_Request_Review.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case INVITE_FRIEND:
                listener.onClickItem(INVITE_FRIEND);
                RLInvite_friend.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case My_Profile:
                listener.onClickItem(My_Profile);
                RLMyProfile.setBackgroundColor(Color.parseColor("#656565"));
                break;
            case Business_hours:
                listener.onClickItem(Business_hours);
                RLbusinesshours.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case TRANSACTION_HISTORY:
                listener.onClickItem(TRANSACTION_HISTORY);
                RLTransactionHistory.setBackgroundColor(Color.parseColor("#656565"));
                break;


            case CAMPAIGNS_SUMMARY:
                listener.onClickItem(CAMPAIGNS_SUMMARY);
                RLCampaignSummary.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case ANALYTICS:
                listener.onClickItem(ANALYTICS);
                RLAnalytics.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case LOGOUT:
                listener.onClickItem(LOGOUT);
                RLLog_out.setBackgroundColor(Color.parseColor("#656565"));
                break;


            case SUPPORT:
                listener.onClickItem(SUPPORT);

                if (LLSupport.getVisibility() == View.VISIBLE) {
                    LLSupport.setVisibility(View.GONE);
                    support_cont.setBackgroundColor(Color.TRANSPARENT);
                    support_img.setBackgroundResource(R.drawable.ic_support);
                    support_text.setTextColor(Color.parseColor("#505050"));
                } else {
                    LLSupport.setVisibility(View.VISIBLE);
                    support_cont.setBackgroundColor(Color.parseColor("#7c7c7c"));
                    support_img.setBackgroundResource(R.drawable.ic_support_white);
                    support_text.setTextColor(Color.WHITE);
                }

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                find_local_projects_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_projects_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_projects_text.setTextColor(Color.parseColor("#505050"));

                about_cont.setBackgroundColor(Color.TRANSPARENT);
                about_img.setBackgroundResource(R.drawable.ic_about);
                about_text.setTextColor(Color.parseColor("#505050"));


                LLAccount.setVisibility(View.GONE);
                LLAbout.setVisibility(View.GONE);
                RLuserinformation.setBackgroundColor(Color.TRANSPARENT);
                RLcompany.setBackgroundColor(Color.TRANSPARENT);
                RLservice.setBackgroundColor(Color.TRANSPARENT);
                RLicense.setBackgroundColor(Color.TRANSPARENT);
                RLPortfolio.setBackgroundColor(Color.TRANSPARENT);
                RLservicearea.setBackgroundColor(Color.TRANSPARENT);
                RLoginsettings.setBackgroundColor(Color.TRANSPARENT);
                RLNotification.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_Reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RL_Social_Media.setBackgroundColor(Color.TRANSPARENT);
                RL_Share_Profile.setBackgroundColor(Color.TRANSPARENT);
                RL_Request_Review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFaq.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;


            case EMAIL_SUPPORT:
                listener.onClickItem(EMAIL_SUPPORT);
                RLEmailSupport.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case FAQ:
                listener.onClickItem(FAQ);
                RLFaq.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case PROVIDE_FEEDBACK:
                listener.onClickItem(PROVIDE_FEEDBACK);
                RLProvideFeedback.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case ABOUT:
                listener.onClickItem(ABOUT);

                if (LLAbout.getVisibility() == View.VISIBLE) {
                    LLAbout.setVisibility(View.GONE);
                    about_cont.setBackgroundColor(Color.TRANSPARENT);
                    about_img.setBackgroundResource(R.drawable.ic_about);
                    about_text.setTextColor(Color.parseColor("#505050"));
                } else {
                    LLAbout.setVisibility(View.VISIBLE);
                    about_cont.setBackgroundColor(Color.parseColor("#7c7c7c"));
                    about_img.setBackgroundResource(R.drawable.ic_about_white);
                    about_text.setTextColor(Color.WHITE);

                }

                account_cont.setBackgroundColor(Color.TRANSPARENT);
                account_img.setBackgroundResource(R.drawable.ic_settings);
                account_text.setTextColor(Color.parseColor("#505050"));

                support_cont.setBackgroundColor(Color.TRANSPARENT);
                support_img.setBackgroundResource(R.drawable.ic_support);
                support_text.setTextColor(Color.parseColor("#505050"));

                find_local_projects_cont.setBackgroundColor(Color.TRANSPARENT);
                find_local_projects_img.setBackgroundResource(R.drawable.ic_search_pro);
                find_local_projects_text.setTextColor(Color.parseColor("#505050"));

                LLAccount.setVisibility(View.GONE);
                LLSupport.setVisibility(View.GONE);
                RLuserinformation.setBackgroundColor(Color.TRANSPARENT);
                RLcompany.setBackgroundColor(Color.TRANSPARENT);
                RLservice.setBackgroundColor(Color.TRANSPARENT);
                RLicense.setBackgroundColor(Color.TRANSPARENT);
                RLPortfolio.setBackgroundColor(Color.TRANSPARENT);
                RLservicearea.setBackgroundColor(Color.TRANSPARENT);
                RLoginsettings.setBackgroundColor(Color.TRANSPARENT);
                RLNotification.setBackgroundColor(Color.TRANSPARENT);
                RLQuick_Reply.setBackgroundColor(Color.TRANSPARENT);
                RLAvailability.setBackgroundColor(Color.TRANSPARENT);
                RL_Social_Media.setBackgroundColor(Color.TRANSPARENT);
                RL_Share_Profile.setBackgroundColor(Color.TRANSPARENT);
                RL_Request_Review.setBackgroundColor(Color.TRANSPARENT);
                RLInvite_friend.setBackgroundColor(Color.TRANSPARENT);
                RLLog_out.setBackgroundColor(Color.TRANSPARENT);
                RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
                RLFaq.setBackgroundColor(Color.TRANSPARENT);
                RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
                RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
                RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);

                break;


            case TERMS_OF_SERVICE:
                listener.onClickItem(TERMS_OF_SERVICE);
                RLTermsOfService.setBackgroundColor(Color.parseColor("#656565"));
                break;

            case PRIVACY_POLICY:
                listener.onClickItem(PRIVACY_POLICY);
                RLPrivacyPolicy.setBackgroundColor(Color.parseColor("#656565"));
                break;

            default:

        }
    }

    public void closeAndResetSideMenuDesign() {

        find_local_projects_cont.setBackgroundColor(Color.TRANSPARENT);
        find_local_projects_img.setBackgroundResource(R.drawable.ic_search_pro);
        find_local_projects_text.setTextColor(Color.parseColor("#505050"));

        account_cont.setBackgroundColor(Color.TRANSPARENT);
        account_img.setBackgroundResource(R.drawable.ic_settings);
        account_text.setTextColor(Color.parseColor("#505050"));

        support_cont.setBackgroundColor(Color.TRANSPARENT);
        support_img.setBackgroundResource(R.drawable.ic_support);
        support_text.setTextColor(Color.parseColor("#505050"));

        about_cont.setBackgroundColor(Color.TRANSPARENT);
        about_img.setBackgroundResource(R.drawable.ic_about);
        about_text.setTextColor(Color.parseColor("#505050"));


        LLSupport.setVisibility(View.GONE);
        LLAbout.setVisibility(View.GONE);
        LLAccount.setVisibility(View.GONE);
        RLuserinformation.setBackgroundColor(Color.TRANSPARENT);
        RLcompany.setBackgroundColor(Color.TRANSPARENT);
        RLservice.setBackgroundColor(Color.TRANSPARENT);
        RLicense.setBackgroundColor(Color.TRANSPARENT);
        RLPortfolio.setBackgroundColor(Color.TRANSPARENT);
        RLservicearea.setBackgroundColor(Color.TRANSPARENT);
        RLoginsettings.setBackgroundColor(Color.TRANSPARENT);
        RLNotification.setBackgroundColor(Color.TRANSPARENT);
        RLQuick_Reply.setBackgroundColor(Color.TRANSPARENT);
        RLAvailability.setBackgroundColor(Color.TRANSPARENT);
        RL_Social_Media.setBackgroundColor(Color.TRANSPARENT);
        RL_Share_Profile.setBackgroundColor(Color.TRANSPARENT);
        RL_Request_Review.setBackgroundColor(Color.TRANSPARENT);
        RLInvite_friend.setBackgroundColor(Color.TRANSPARENT);
        RLLog_out.setBackgroundColor(Color.TRANSPARENT);
        RLEmailSupport.setBackgroundColor(Color.TRANSPARENT);
        RLFaq.setBackgroundColor(Color.TRANSPARENT);
        RLProvideFeedback.setBackgroundColor(Color.TRANSPARENT);
        RLTermsOfService.setBackgroundColor(Color.TRANSPARENT);
        RLPrivacyPolicy.setBackgroundColor(Color.TRANSPARENT);
        RLMyProfile.setBackgroundColor(Color.TRANSPARENT);
        RLbusinesshours.setBackgroundColor(Color.TRANSPARENT);
        RLTransactionHistory.setBackgroundColor(Color.TRANSPARENT);
        RLCampaignSummary.setBackgroundColor(Color.TRANSPARENT);
        RLAnalytics.setBackgroundColor(Color.TRANSPARENT);
    }


    public interface OnHandleInput {
        void onClickItem(String tag);
    }
}
