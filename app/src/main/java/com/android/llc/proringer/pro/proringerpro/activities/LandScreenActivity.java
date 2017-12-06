package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.DashBoardFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.MessageFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.MyProjectsFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.WatchListFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.AvailabilityTimeSlotFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.InviteAfriendFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.NotificationsFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.QuickReplyFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.RequestReviewFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.SocialMediaFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.main_content.ProjectMessagingFragment;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.BottomNav;
import com.android.llc.proringer.pro.proringerpro.viewsmod.NavigationHandler;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LandScreenActivity extends AppCompatActivity {
    public BottomNav bottomNavInstance = null;
    ImageView nav_toggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar = null;
    private Toolbar back_toolbar = null;
    private ActionBarDrawerToggle toggle = null;
    private FragmentManager fragmentManager = null;
    ProRegularTextView tv_title;
    ImageView iv_pro_logo,search_local_pro_header,img_back;
    LinearLayout linear_buttombar;
    private ImageView dashboard_image, my_projects_image, messages_image, fav_pro_image;
    private ProRegularTextView dashboard_text, my_projects_text, messages_text, fav_pro_text;
    ArrayList<SetGetAPI> arrayList=null;
    public MyLoader myLoader=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);

        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        tv_title=(ProRegularTextView)findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        linear_buttombar=(LinearLayout)findViewById(R.id.linear_buttombar);

        dashboard_image = (ImageView)findViewById(R.id.dashboard_image);
        my_projects_image = (ImageView)findViewById(R.id.my_projects_image);
        messages_image = (ImageView)findViewById(R.id.messages_image);
        fav_pro_image = (ImageView) findViewById(R.id.fav_pro_image);
        dashboard_text = (ProRegularTextView)findViewById(R.id.dashboard_text);
        my_projects_text = (ProRegularTextView)findViewById(R.id.my_projects_text);
        messages_text = (ProRegularTextView)findViewById(R.id.messages_text);
        fav_pro_text = (ProRegularTextView)findViewById(R.id.fav_pro_text);
        myLoader=new MyLoader(LandScreenActivity.this);
        iv_pro_logo=(ImageView)findViewById(R.id.iv_pro_logo);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        search_local_pro_header=(ImageView) findViewById(R.id.search_local_pro_header);
        search_local_pro_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent i= new Intent( LandScreenActivity.this,SearchNearActivity.class);
                startActivity(i);
            }
        });
        toggle.syncState();


        /**
         * Bottom nav bar handling
         */
        RelativeLayout cont_bottom = (RelativeLayout) findViewById(R.id.cont_bottom);
        bottomNavInstance = BottomNav.getInstance(LandScreenActivity.this, cont_bottom);

        bottomNavInstance.init(new BottomNav.onSelectListener() {
            @Override
            public void onClick(String selected_tag) {
                Logger.printMessage("Fragment_stack", "" + getSupportFragmentManager().getBackStackEntryCount());

                switch (selected_tag) {
                    case BottomNav.DASHBOARD:
                        closeDrawer();
                        transactDashBoard();
                        toolbar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);

                        break;
                    case BottomNav.MY_PROJECTS:
                        toggleProMapSearch(false);
                        transactMyProjects();
                        toolbar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);
                        closeDrawer();
                        break;
                    case BottomNav.MESSAGES:
                        transactMessages();
                        toolbar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);
                        closeDrawer();
                        break;
                    case BottomNav.WATCH_LIST:
                        transactWatchList();
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);

                        toolbar.setVisibility(View.VISIBLE);

                        closeDrawer();
                        break;
                }
            }
        });


        /**
         * Handles view click for drawer layout
         */
        NavigationHandler.getInstance().init(mDrawer, new NavigationHandler.OnHandleInput() {
            @Override
            public void onClickItem(String tag) {

                dashboard_image.setBackgroundResource(R.drawable.ic_dashboard);
                dashboard_text.setTextColor(Color.parseColor("#505050"));

                my_projects_image.setBackgroundResource(R.drawable.ic_my_project);
                my_projects_text.setTextColor(Color.parseColor("#505050"));

                messages_image.setBackgroundResource(R.drawable.ic_message);
                messages_text.setTextColor(Color.parseColor("#505050"));

                fav_pro_image.setBackgroundResource(R.drawable.ic_fav_pro);
                fav_pro_text.setTextColor(Color.parseColor("#505050"));
                linear_buttombar.setVisibility(View.VISIBLE);
                switch (tag) {

                    case NavigationHandler.FIND_LOCAL_PROJECT:
                        break;

                    case NavigationHandler.ACCOUNT:
                        break;

                    case NavigationHandler.NOTIFICATION:
                        closeDrawer();
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("Notifications");
                        transactNotification();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        break;

                    case NavigationHandler.QUICK_REPLY:
                        closeDrawer();
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("QUICK REPLY MESSAGE");
                       // linear_buttombar.setVisibility(View.GONE);
                        Intent i= new Intent(LandScreenActivity.this,QuickReplyActivity.class);
                        startActivity(i);
                        break;

                    case NavigationHandler.AVAILABILITY:
                        closeDrawer();
                        transactTimeAvailability();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("AVAILABILITY");
                        break;

                    case NavigationHandler.SOCIAL_MEDIA:
                        closeDrawer();
                        transactSocialMedia();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("SOCIAL MEDIA");
                        break;

                    case NavigationHandler.SHARE_PROFILE:
                        closeDrawer();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        break;

                    case NavigationHandler.REQUEST_REVIEW:
                        closeDrawer();
                        transactRequestReview();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("REQUEST REVIEW");
                        break;

                    case NavigationHandler.INVITE_FRIEND:
                        closeDrawer();
                        transactInviteFriend();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("INVITE FRIEND");
                        break;

                    case NavigationHandler.LOGOUT:
                        closeDrawer();
                        linear_buttombar.setVisibility(View.VISIBLE);

                        LayoutInflater factory = LayoutInflater.from(LandScreenActivity.this);
                        final View deleteDialogView = factory.inflate(R.layout.dialog, null);
                        final AlertDialog deleteDialog = new AlertDialog.Builder(LandScreenActivity.this).create();
                        deleteDialog.setView(deleteDialogView);
                        deleteDialogView.findViewById(R.id.proSemi_logout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myLoader.showLoader();
                                arrayList=new ArrayList<SetGetAPI>();
                                SetGetAPI setGetAPI =new SetGetAPI();
                                setGetAPI.setPARAMS("user_id");
                                setGetAPI.setValues(ProApplication.getInstance().getUserId());
                                arrayList.add(setGetAPI);

                                setGetAPI =new SetGetAPI();
                                setGetAPI.setPARAMS("anorid_status");
                                setGetAPI.setValues("1");
                                arrayList.add(setGetAPI);

                                setGetAPI =new SetGetAPI();
                                setGetAPI.setPARAMS("ios_status");
                                setGetAPI.setValues("1");
                                arrayList.add(setGetAPI);

                                new CustomJSONParser().fireAPIForGetMethod(LandScreenActivity.this, ProConstant.Logout, arrayList, new CustomJSONParser.CustomJSONResponse() {
                                    @Override
                                    public void onSuccess(String result) {
                                        try {
                                            JSONObject mainResponseObj =  new JSONObject(result);
                                            String message=mainResponseObj.getString("message");
                                            ProApplication.getInstance().logOut();
                                              startActivity(new Intent(LandScreenActivity.this,GetStartedActivity.class));
                                             finish();
                                             deleteDialog.dismiss();
                                            Toast.makeText(LandScreenActivity.this, "Successfully logout", Toast.LENGTH_SHORT).show();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                    @Override
                                    public void onError(String error, String response) {
                                        myLoader.dismissLoader();
                                        deleteDialog.dismiss();
                                        Toast.makeText(LandScreenActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(String error) {

                                        myLoader.dismissLoader();
                                        Toast.makeText(LandScreenActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onStart() {
                                        deleteDialog.dismiss();
                                    }
                                });

                            }
                        });
                        deleteDialogView.findViewById(R.id.prosemi_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                            }
                        });

                        deleteDialog.show();
                        break;

                    case NavigationHandler.SUPPORT:
                        break;

                    case NavigationHandler.EMAIL_SUPPORT:
                        closeDrawer();
                        String[] TOSuppory = {"support@proringer.com"};
                        Uri uriSupport = Uri.parse("mailto:support@proringer.com")
                                .buildUpon()
                                .appendQueryParameter("subject", "Support")
                                .appendQueryParameter("body", "I think \n \n \n Proringer mobile app v1.0.1")
                                .build();
                        Intent emailSupportIntent = new Intent(Intent.ACTION_SENDTO, uriSupport);
                        emailSupportIntent.putExtra(Intent.EXTRA_EMAIL, TOSuppory);
                        startActivity(Intent.createChooser(emailSupportIntent, "Send mail..."));
                        break;
                    case NavigationHandler.FAQ:
                        closeDrawer();
                        startActivity(new Intent(LandScreenActivity.this, FaqActivity.class));
                        break;
                    case NavigationHandler.PROVIDE_FEEDBACK:
                        closeDrawer();
                        String[] TOFeedback = {"feedback@proringer.com"};
                        Uri uriFeedback = Uri.parse("mailto:feedback@proringer.com")
                                .buildUpon()
                                .appendQueryParameter("subject", "Leave Feedback")
                                .appendQueryParameter("body", "I think \n \n \n Proringer mobile app v1.0.1")
                                .build();
                        Intent emailFeedbackIntent = new Intent(Intent.ACTION_SENDTO, uriFeedback);
                        emailFeedbackIntent.putExtra(Intent.EXTRA_EMAIL, TOFeedback);
                        startActivity(Intent.createChooser(emailFeedbackIntent, "Send mail..."));
                        break;


                    case NavigationHandler.ABOUT:
                        break;

                    case NavigationHandler.TERMS_OF_SERVICE:
                        closeDrawer();
                        Intent intentTerms = new Intent(LandScreenActivity.this, TermsPrivacyActivity.class);
                        intentTerms.putExtra("value", "term");
                        startActivity(intentTerms);
                        break;
                    case NavigationHandler.PRIVACY_POLICY:
                        closeDrawer();
                        Intent intentPolicy = new Intent(LandScreenActivity.this, TermsPrivacyActivity.class);
                        intentPolicy.putExtra("value", "policy");
                        startActivity(intentPolicy);
                        break;
                }
            }
        });

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.nav_toggle_icon, null));

        redirectToDashBoard();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(LandScreenActivity.this)
                    .setMessage("Do you want to exit application?")
                    .setPositiveButton("Abort", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }).show();
        }
    }

    private void closeDrawer() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (mDrawer != null)
                    mDrawer.closeDrawer(GravityCompat.START);
            }
        });
    }


    private void toggleProMapSearch(boolean state) {
        if (!state) {
            findViewById(R.id.search_local_pro_header_map).setVisibility(View.GONE);
            findViewById(R.id.search_local_pro_header).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.search_local_pro_header_map).setVisibility(View.VISIBLE);
            findViewById(R.id.search_local_pro_header).setVisibility(View.GONE);
        }
    }

    /**
     * \
     * Fragment transaction of DashBoardFragment
     */
    private void transactDashBoard() {
        toggleToolBar(false);
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + DashBoardFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + DashBoardFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + DashBoardFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + DashBoardFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new DashBoardFragment(), "" + DashBoardFragment.class.getCanonicalName());
        transaction.addToBackStack("" + DashBoardFragment.class.getCanonicalName());
        transaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }


    private void transactInviteFriend() {

        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + InviteAfriendFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + InviteAfriendFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + InviteAfriendFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + InviteAfriendFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new InviteAfriendFragment(), "" + InviteAfriendFragment.class.getCanonicalName());
        transaction.addToBackStack("" + InviteAfriendFragment.class.getCanonicalName());
        transaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }

//    private void transactQuickReply() {
//        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + QuickReplyFragment.class.getCanonicalName()) != null) {
//            Logger.printMessage("back_stack", "Removed *****" + QuickReplyFragment.class.getCanonicalName());
//
//            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + QuickReplyFragment.class.getCanonicalName())).commit();
//            fragmentManager.popBackStack("" + QuickReplyFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment_container, new QuickReplyFragment(), "" + QuickReplyFragment.class.getCanonicalName());
//        transaction.addToBackStack("" + QuickReplyFragment.class.getCanonicalName());
//        transaction.commit();
//        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
//    }

    private void transactRequestReview() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + RequestReviewFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + QuickReplyFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + RequestReviewFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + RequestReviewFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new RequestReviewFragment(), "" + RequestReviewFragment.class.getCanonicalName());
        transaction.addToBackStack("" + RequestReviewFragment.class.getCanonicalName());
        transaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }

    private void transactNotification() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + NotificationsFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + NotificationsFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + NotificationsFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + NotificationsFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new NotificationsFragment(), "" + NotificationsFragment.class.getCanonicalName());
        transaction.addToBackStack("" + NotificationsFragment.class.getCanonicalName());
        transaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }

    private void transactSocialMedia() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + SocialMediaFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + SocialMediaFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + SocialMediaFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + SocialMediaFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new SocialMediaFragment(), "" + SocialMediaFragment.class.getCanonicalName());
        transaction.addToBackStack("" + SocialMediaFragment.class.getCanonicalName());
        transaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }


    private void transactTimeAvailability() {

        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + AvailabilityTimeSlotFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + AvailabilityTimeSlotFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + AvailabilityTimeSlotFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + AvailabilityTimeSlotFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new AvailabilityTimeSlotFragment(), "" + AvailabilityTimeSlotFragment.class.getCanonicalName());
        transaction.addToBackStack("" + AvailabilityTimeSlotFragment.class.getCanonicalName());
        transaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }


    /**
     * Fragment transaction for Messages
     */
    private void transactMessages() {
        toggleToolBar(false);
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + MessageFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + MessageFragment.class.getCanonicalName());
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + MessageFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + MessageFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new MessageFragment(), "" + MessageFragment.class.getCanonicalName());
        transaction.addToBackStack("" + MessageFragment.class.getCanonicalName());
        transaction.commit();

        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());

        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            Logger.printMessage("back_stack", "" + getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
    }

    /**
     * Transact individual message from details message
     * flow is MessageFragment(main sectional fragment)->detailedMessage(fragment)
     */
    public void transactProjectMessaging() {
        toggleToolBar(true);
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + ProjectMessagingFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + ProjectMessagingFragment.class.getCanonicalName());
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + ProjectMessagingFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + ProjectMessagingFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ProjectMessagingFragment(), "" + ProjectMessagingFragment.class.getCanonicalName());
        transaction.addToBackStack("" + ProjectMessagingFragment.class.getCanonicalName());
        transaction.commit();

        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());

        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            Logger.printMessage("back_stack", "" + getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
    }


    /**
     * Fragment transaction for Watch List
     */
    private void transactWatchList() {
        toggleToolBar(false);
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + WatchListFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + WatchListFragment.class.getCanonicalName());
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + WatchListFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + WatchListFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new WatchListFragment(), "" + WatchListFragment.class.getCanonicalName());
        transaction.addToBackStack("" + WatchListFragment.class.getCanonicalName());
        transaction.commit();

        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            Logger.printMessage("back_stack", "" + getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
    }



    /**
     * Fragment transaction for MyProject
     */
    public void transactMyProjects() {
        toggleToolBar(false);

        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + MyProjectsFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + MyProjectsFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + MyProjectsFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + MyProjectsFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new MyProjectsFragment(), "" + MyProjectsFragment.class.getCanonicalName());
        transaction.addToBackStack("" + MyProjectsFragment.class.getCanonicalName());
        transaction.commit();

        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            Logger.printMessage("back_stack", "" + getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (fragmentManager.findFragmentByTag(ProjectMessagingFragment.class.getCanonicalName()) != null && fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName().equals(ProjectMessagingFragment.class.getCanonicalName())) {
                toggleToolBar(false);
                transactMessages();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Toggle toolbar header for message details
     *
     * @param state
     */
    public void toggleToolBar(boolean state) {
        if (state) {
            back_toolbar.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
            setSupportActionBar(back_toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        } else {
            back_toolbar.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawer.addDrawerListener(toggle);
            toggle.syncState();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.nav_toggle_icon, null));
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        }
    }

    /**
     * Redirection to Dashboard fragment transaction
     */
    public void redirectToDashBoard() {
        bottomNavInstance.highLightSelected(BottomNav.DASHBOARD);
        transactDashBoard();
    };
}