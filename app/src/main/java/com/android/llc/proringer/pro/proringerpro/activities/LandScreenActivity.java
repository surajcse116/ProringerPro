package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.ProjectListFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.QuickReplyFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.RequestReviewFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.SocialMediaFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.main_content.ProjectMessagingFragment;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
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
    ImageView iv_pro_logo, search_local_pro_header,search_local_pro_header_backTool;
    LinearLayout linear_buttombar;
    private ImageView dashboard_image, my_projects_image, messages_image, fav_pro_image;
    private ProRegularTextView dashboard_text, my_projects_text, messages_text, fav_pro_text;
    ArrayList<SetGetAPIPostData> arrayList = null;
    public MyLoader myLoader = null;
    public String local_project_search_zip="";

    NavigationHandler navigationHandler = null;

    private InputMethodManager keyboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);

        fragmentManager = getSupportFragmentManager();

        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                Logger.printMessage("close-->","yes");
                drawerReset();
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                Logger.printMessage("open-->","yes");
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawer.addDrawerListener(toggle);

        tv_title = (ProRegularTextView) findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        linear_buttombar = (LinearLayout) findViewById(R.id.linear_buttombar);

        dashboard_image = (ImageView) findViewById(R.id.dashboard_image);
        my_projects_image = (ImageView) findViewById(R.id.my_projects_image);
        messages_image = (ImageView) findViewById(R.id.messages_image);
        fav_pro_image = (ImageView) findViewById(R.id.fav_pro_image);
        dashboard_text = (ProRegularTextView) findViewById(R.id.dashboard_text);
        my_projects_text = (ProRegularTextView) findViewById(R.id.my_projects_text);
        messages_text = (ProRegularTextView) findViewById(R.id.messages_text);
        fav_pro_text = (ProRegularTextView) findViewById(R.id.fav_pro_text);
        myLoader = new MyLoader(LandScreenActivity.this);
        iv_pro_logo = (ImageView) findViewById(R.id.iv_pro_logo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        search_local_pro_header = (ImageView) findViewById(R.id.search_local_pro_header);
        search_local_pro_header_backTool = (ImageView) findViewById(R.id.search_local_pro_header_backTool);

        search_local_pro_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavigationHandler.getInstance().highlightTag(NavigationHandler.FIND_LOCAL_PROJECT);
                navigationHandler.highlightTag(NavigationHandler.FIND_LOCAL_PROJECT);
                projectTransactAndSetView();
            }
        });

        search_local_pro_header_backTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavigationHandler.getInstance().highlightTag(NavigationHandler.FIND_LOCAL_PROJECT);
                navigationHandler.highlightTag(NavigationHandler.FIND_LOCAL_PROJECT);
                projectTransactAndSetView();
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
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactDashBoard();
                        toolbar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);

                        break;
                    case BottomNav.MY_PROJECTS:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactMyProjects();
                        toolbar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);

                        break;
                    case BottomNav.MESSAGES:
                        toggleProMapSearch(false);
                        transactMessages();
                        toolbar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);
                        closeDrawer();
                        break;
                    case BottomNav.WATCH_LIST:
                        toggleProMapSearch(false);
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

        navigationHandler = NavigationHandler.getInstance();

        navigationHandler.init(mDrawer, new NavigationHandler.OnHandleInput() {
            @Override
            public void onClickItem(String tag) {

                String manufacturer = Build.MANUFACTURER;
                String model = Build.MODEL;
                int version = Build.VERSION.SDK_INT;
                String versionRelease = Build.VERSION.RELEASE;

                Logger.printMessage("LandScreenActivity", "manufacturer " + manufacturer
                        + " \n model " + model
                        + " \n version " + version
                        + " \n versionRelease " + versionRelease
                );

                TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                final String carrierName = manager.getNetworkOperatorName();

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

                        toggleProMapSearch(false);
                        projectTransactAndSetView();
                        closeDrawer();

                        toolbar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);

                        break;

                    case NavigationHandler.ACCOUNT:
                        break;

                    case NavigationHandler.NOTIFICATION:
                        toggleProMapSearch(false);
                        closeDrawer();
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("Notifications");
                        transactNotification();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        break;

                    case NavigationHandler.QUICK_REPLY:
                        toggleProMapSearch(false);
                        closeDrawer();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("QUICK REPLY MESSAGE");
                        transactquickReply();
                        break;

                    case NavigationHandler.AVAILABILITY:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactTimeAvailability();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("AVAILABILITY");
                        break;

                    case NavigationHandler.SOCIAL_MEDIA:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactSocialMedia();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("SOCIAL MEDIA");
                        break;

                    case NavigationHandler.SHARE_PROFILE:
                        toggleProMapSearch(false);
                        closeDrawer();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        break;

                    case NavigationHandler.REQUEST_REVIEW:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactRequestReview();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("REQUEST REVIEW");
                        break;

                    case NavigationHandler.INVITE_FRIEND:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactInviteFriend();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("INVITE FRIEND");
                        break;

                    case NavigationHandler.My_Profile:
                        toggleProMapSearch(false);
                        closeDrawer();

                        Intent intent=new Intent(LandScreenActivity.this,MyProfileActivity.class);
                        startActivity(intent);

                        break;

                        case NavigationHandler.TRANSACTION_HISTORY:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactSocialMedia();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("TRANSACTION HISTORY");
                        break;

                    case NavigationHandler.CAMPAIGNS_SUMMARY:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactSocialMedia();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("CAMPAIGNS SUMMARY");
                        break;

                        case NavigationHandler.ANALYTICS:
                        toggleProMapSearch(false);
                        closeDrawer();
                        transactSocialMedia();
                        linear_buttombar.setVisibility(View.VISIBLE);
                        iv_pro_logo.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("ANALYTICS");
                        break;

                    case NavigationHandler.LOGOUT:
                        toggleProMapSearch(false);
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
                                arrayList = new ArrayList<SetGetAPIPostData>();
                                SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
                                setGetAPIPostData.setPARAMS("user_id");
                                setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
                                arrayList.add(setGetAPIPostData);

                                setGetAPIPostData = new SetGetAPIPostData();
                                setGetAPIPostData.setPARAMS("anorid_status");
                                setGetAPIPostData.setValues("1");
                                arrayList.add(setGetAPIPostData);

                                setGetAPIPostData = new SetGetAPIPostData();
                                setGetAPIPostData.setPARAMS("ios_status");
                                setGetAPIPostData.setValues("1");
                                arrayList.add(setGetAPIPostData);

                                new CustomJSONParser().fireAPIForGetMethod(LandScreenActivity.this, ProConstant.Logout, arrayList, new CustomJSONParser.CustomJSONResponse() {
                                    @Override
                                    public void onSuccess(String result) {
                                        try {
                                            JSONObject mainResponseObj = new JSONObject(result);
                                            String message = mainResponseObj.getString("message");
                                            ProApplication.getInstance().logOut();
                                            startActivity(new Intent(LandScreenActivity.this, GetStartedActivity.class));
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
                                        Toast.makeText(LandScreenActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(String error) {

                                        myLoader.dismissLoader();
                                        Toast.makeText(LandScreenActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
                        toggleProMapSearch(false);
                        closeDrawer();

                        String[] TOSuppory = {"support@proringer.com"};
                        Uri uriSupport = Uri.parse("mailto:support@proringer.com")
                                .buildUpon()
                                .appendQueryParameter("subject", "Support")
                                .appendQueryParameter("body", "  \n \n \n ProRinger Pro App v1.0.1 \n" +
                                        "Device: " + model + ", " + versionRelease + "\n" +
                                        "Carrier:" + " " + carrierName)
                                .build();
                        Intent emailSupportIntent = new Intent(Intent.ACTION_SENDTO, uriSupport);
                        emailSupportIntent.putExtra(Intent.EXTRA_EMAIL, TOSuppory);
                        startActivity(Intent.createChooser(emailSupportIntent, "Send mail..."));
                        break;
                    case NavigationHandler.FAQ:
                        toggleProMapSearch(false);
                        closeDrawer();
                        startActivity(new Intent(LandScreenActivity.this, FaqActivity.class));
                        break;
                    case NavigationHandler.PROVIDE_FEEDBACK:
                        toggleProMapSearch(false);
                        closeDrawer();

                        String[] TOFeedback = {"feedback@proringer.com"};
                        Uri uriFeedback = Uri.parse("mailto:feedback@proringer.com")
                                .buildUpon()
                                .appendQueryParameter("subject", "Leave Feedback")
                                .appendQueryParameter("body", "  \n \n \n ProRinger Pro App v1.0.1\n" +
                                        "Device: " + model + ", " + versionRelease + "\n" +
                                        "Carrier:" + " " + carrierName)
                                .build();
                        Intent emailFeedbackIntent = new Intent(Intent.ACTION_SENDTO, uriFeedback);
                        emailFeedbackIntent.putExtra(Intent.EXTRA_EMAIL, TOFeedback);
                        startActivity(Intent.createChooser(emailFeedbackIntent, "Send mail..."));
                        break;


                    case NavigationHandler.ABOUT:
                        break;

                    case NavigationHandler.TERMS_OF_SERVICE:
                        toggleProMapSearch(false);
                        closeDrawer();
                        Intent intentTerms = new Intent(LandScreenActivity.this, TermsPrivacyActivity.class);
                        intentTerms.putExtra("value", "term");
                        startActivity(intentTerms);
                        break;
                    case NavigationHandler.PRIVACY_POLICY:
                        toggleProMapSearch(false);
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


        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Logger.printMessage("Refresh_token", "-->" + refreshedToken);


//        HashMap<String, String> Params = new HashMap<>();
//        Params.put("user_id",  ProApplication.getInstance().getUserId());
//        Params.put("device_token", refreshedToken);

//        new CustomJSONParser().fireAPIForPostMethod(getApplication(), ProConstant.BASEURL + "users_device_update", Params, null, new CustomJSONParser.CustomJSONResponse() {
//            @Override
//            public void onSuccess(String result) {
//
//            }
//
//            @Override
//            public void onError(String error, String response) {
//
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        });
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

    private void drawerReset() {
        closeKeypad();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                navigationHandler.closeAndResetSideMenuDesign();
            }
        });
    }


    private void toggleProMapSearch(boolean state) {
        if (!state) {
            findViewById(R.id.search_local_pro_header_map).setVisibility(View.GONE);
            findViewById(R.id.search_local_pro_header).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.search_local_pro_header_map).setVisibility(View.VISIBLE);
            findViewById(R.id.search_local_pro_header_map).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent searchIntent = new Intent(LandScreenActivity.this, SearchNearActivity.class);
                    startActivityForResult(searchIntent, 41);

                }
            });
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



    /**
     * \
     * Fragment transaction of DashBoardFragment
     */
    private void transactquickReply() {
        toggleToolBar(false);
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + QuickReplyFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + QuickReplyFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + QuickReplyFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + QuickReplyFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new QuickReplyFragment(), "" + QuickReplyFragment.class.getCanonicalName());
        transaction.addToBackStack("" + QuickReplyFragment.class.getCanonicalName());
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
            Logger.printMessage("back_stack", "Removed *****" + RequestReviewFragment.class.getCanonicalName());

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
     * Fragment transaction for Watch List
     */
    public void transactProjectList() {
        local_project_search_zip = "";
        toggleToolBar(false);
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + ProjectListFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + ProjectListFragment.class.getCanonicalName());
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + ProjectListFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + ProjectListFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new ProjectListFragment(), "" + ProjectListFragment.class.getCanonicalName());
        transaction.addToBackStack("" + ProjectListFragment.class.getCanonicalName());
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
        Logger.printMessage("home_click", "-->yes");
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
            toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    drawerReset();
                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            mDrawer.addDrawerListener(toggle);
            toggle.syncState();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.nav_toggle_icon, null));
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


//            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Logger.printMessage("toggle-->", "Yes");
//                }
//            });

//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Logger.printMessage("toggle-->", "Yes");
//                }
//            });

        }
    }

    /**
     * Redirection to Dashboard fragment transaction
     */
    public void redirectToDashBoard() {
        bottomNavInstance.highLightSelected(BottomNav.DASHBOARD);
        transactDashBoard();
    }

    public void closeKeypad() {
        try {
            keyboard.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 41) {

            Bundle extras = data.getBundleExtra("data");
            if (extras != null) {
                local_project_search_zip = extras.getString("searchZip");
                Logger.printMessage("local_pros_search_zip", "--->" + local_project_search_zip);


                if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + ProjectListFragment.class.getCanonicalName()) != null) {
                    Logger.printMessage("back_stack", "Removed *****" + ProjectListFragment.class.getCanonicalName());
                    ProjectListFragment fragment = (ProjectListFragment) fragmentManager.findFragmentByTag("" + ProjectListFragment.class.getCanonicalName());
                    fragment.loadList();
                    Logger.printMessage("LoadListFromActivity", "YES");
                }

            }
        }
    }
    public void projectTransactAndSetView(){
        toggleProMapSearch(true);
        bottomNavInstance.highLightSelected(BottomNav.CREATE_PROJECT);
        transactProjectList();
    }
}