package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.ProHelperClass;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.SwitchHelper;

import java.util.ArrayList;

/**
 * Created by bodhidipta on 22/06/17.
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

public class NotificationsFragment extends Fragment {
    private SwitchHelper email_newsletter, email_chat_msg, email_tips_artcl, email_project_replies, mobile_newsletter, email_newreviw, account_achivement, mobile_chat_msg, mobile_tips_artcl, mobile_project_replies, mobileaccountachivement, mobilereview;
    ArrayList<SetGetAPIPostData> arrayList = null;
    String set_email_newsletter, set_email_chat_msg, set_email_tips_artcl, set_email_prjct_rspnse, set_mobile_newsletter, set_email_newreviw, set_account_achivement, set_mobile_chat_msg, set_mobile_tips_artcl, set_mobile_prjct_rspnse, set_mobileaccountachivement, set_mobilereview;
    MyLoader myLoader;
    ScrollView ScrollViewMAin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ScrollViewMAin = (ScrollView) view.findViewById(R.id.ScrollViewMAin);

        email_newsletter = (SwitchHelper) view.findViewById(R.id.email_newsletter);
        email_chat_msg = (SwitchHelper) view.findViewById(R.id.email_chat_msg);
        email_tips_artcl = (SwitchHelper) view.findViewById(R.id.email_tips_artcl);
        email_project_replies = (SwitchHelper) view.findViewById(R.id.email_project_replies);
        email_newreviw = (SwitchHelper) view.findViewById(R.id.email_newreviw);
        account_achivement = (SwitchHelper) view.findViewById(R.id.account_achivement);
        mobile_newsletter = (SwitchHelper) view.findViewById(R.id.mobile_newsletter);
        mobile_chat_msg = (SwitchHelper) view.findViewById(R.id.mobile_chat_msg);
        mobile_tips_artcl = (SwitchHelper) view.findViewById(R.id.mobile_tips_artcl);
        mobile_project_replies = (SwitchHelper) view.findViewById(R.id.mobile_project_replies);
        mobileaccountachivement = (SwitchHelper) view.findViewById(R.id.mobileaccountachivement);
        mobilereview = (SwitchHelper) view.findViewById(R.id.mobilereview);
        arrayList = new ArrayList<SetGetAPIPostData>();

        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);


        myLoader = new MyLoader(getActivity());
        email_newsletter.setState(ProApplication.getInstance().getUserNotification().isEmail_newsletter());
        email_chat_msg.setState(ProApplication.getInstance().getUserNotification().isEmail_chat_msg());
        email_tips_artcl.setState(ProApplication.getInstance().getUserNotification().isEmail_tips_article());
        email_project_replies.setState(ProApplication.getInstance().getUserNotification().isEmail_project_replies());
        email_newreviw.setState(ProApplication.getInstance().getUserNotification().isEmail_newreviw());
        account_achivement.setState(ProApplication.getInstance().getUserNotification().isaccount_achivement());
        mobile_newsletter.setState(ProApplication.getInstance().getUserNotification().isMobile_newsletter());
        mobile_chat_msg.setState(ProApplication.getInstance().getUserNotification().isMobile_chat_msg());
        mobile_tips_artcl.setState(ProApplication.getInstance().getUserNotification().isMobile_tips_article());
        mobile_project_replies.setState(ProApplication.getInstance().getUserNotification().isMobile_project_replies());
        mobileaccountachivement.setState(ProApplication.getInstance().getUserNotification().ismobileaccountachivement());
        mobilereview.setState(ProApplication.getInstance().getUserNotification().ismobilereview());
        email_newsletter.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "email_newsletter : " + state);
                setChangeNotification();
            }
        });
        email_chat_msg.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "email_chat_msg : " + state);
                setChangeNotification();
            }
        });
        email_tips_artcl.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "email_tips_article : " + state);
                setChangeNotification();
            }
        });

        email_project_replies.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "email_job_post : " + state);
                setChangeNotification();
            }
        });
        email_newreviw.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "email_new_reviews : " + state);
                setChangeNotification();
            }
        });
        account_achivement.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "email_acc_achieve : " + state);
                setChangeNotification();
            }
        });
        mobile_newsletter.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "mobile_newsletter : " + state);
                setChangeNotification();
            }
        });
        mobile_chat_msg.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "mobile_chat_msg : " + state);
                setChangeNotification();
            }
        });
        mobile_tips_artcl.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "mobile_article : " + state);
                setChangeNotification();
            }
        });
        mobile_project_replies.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "mobile_job_post : " + state);
                setChangeNotification();
            }
        });

        mobilereview.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "mobile_new_reviews : " + state);
                setChangeNotification();
            }
        });
        mobileaccountachivement.setOnclickCallback(new SwitchHelper.onClickListener() {
            @Override
            public void onClick(boolean state) {
                Logger.printMessage("customswitch", "mobile_acc_achieve : " + state);
                setChangeNotification();
            }
        });


        getNotificationState();

    }

    private void getNotificationState() {

        ScrollViewMAin.setVisibility(View.VISIBLE);

        ProHelperClass.getInstance(getActivity()).getUserNotification(new ProHelperClass.getApiProcessCallback() {
            @Override
            public void onStart() {
                myLoader.showLoader();
            }

            @Override
            public void onComplete(String message) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                email_newsletter.setState(ProApplication.getInstance().getUserNotification().isEmail_newsletter());
                email_chat_msg.setState(ProApplication.getInstance().getUserNotification().isEmail_chat_msg());
                email_tips_artcl.setState(ProApplication.getInstance().getUserNotification().isEmail_tips_article());
                email_project_replies.setState(ProApplication.getInstance().getUserNotification().isEmail_project_replies());
                email_newreviw.setState(ProApplication.getInstance().getUserNotification().isEmail_newreviw());
                account_achivement.setState(ProApplication.getInstance().getUserNotification().isaccount_achivement());
                mobile_newsletter.setState(ProApplication.getInstance().getUserNotification().isMobile_newsletter());
                mobile_chat_msg.setState(ProApplication.getInstance().getUserNotification().isMobile_chat_msg());
                mobile_tips_artcl.setState(ProApplication.getInstance().getUserNotification().isMobile_tips_article());
                mobile_project_replies.setState(ProApplication.getInstance().getUserNotification().isMobile_project_replies());
                mobileaccountachivement.setState(ProApplication.getInstance().getUserNotification().ismobileaccountachivement());
                mobilereview.setState(ProApplication.getInstance().getUserNotification().ismobilereview());
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();

                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        getNotificationState();
                    }

                    @Override
                    public void callBackCancel() {

                    }
                });
            }
        });


    }

    private void setChangeNotification() {
        ProHelperClass.getInstance(getActivity()).updateUserNotification(new ProHelperClass.getApiProcessCallback() {
                                                                          @Override
                                                                          public void onStart() {


                                                                          }

                                                                          @Override
                                                                          public void onComplete(String message) {

                                                                          }

                                                                          @Override
                                                                          public void onError(String error) {

                                                                          }
                                                                      }, email_newsletter.getState(),
                email_chat_msg.getState(),
                email_tips_artcl.getState(),
                email_project_replies.getState(),
                email_newreviw.getState(),
                account_achivement.getState(),
                mobile_newsletter.getState(),
                mobile_chat_msg.getState(),
                mobile_tips_artcl.getState(),
                mobile_project_replies.getState(),
                mobilereview.getState(),
                mobileaccountachivement.getState()
        );

    }
}
