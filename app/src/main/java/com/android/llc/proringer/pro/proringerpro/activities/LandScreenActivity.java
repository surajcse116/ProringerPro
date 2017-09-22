package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
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
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.BottomNav;
import com.android.llc.proringer.pro.proringerpro.viewsmod.NavigationHandler;


public class LandScreenActivity extends AppCompatActivity {
    public BottomNav bottomNavInstance = null;
    ImageView nav_toggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar = null;
    private Toolbar back_toolbar = null;
    private ActionBarDrawerToggle toggle = null;
    private FragmentManager fragmentManager = null;


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
                        break;
                    case BottomNav.MY_PROJECTS:
                        toggleProMapSearch(false);
                        transactMyProjects();
                        closeDrawer();
                        break;
                    case BottomNav.MESSAGES:
                        transactMessages();
                        closeDrawer();
                        break;
                    case BottomNav.WATCH_LIST:
                        transactWatchList();
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
                switch (tag) {

                    case NavigationHandler.FIND_LOCAL_PROJECT:
                        break;

                    case NavigationHandler.ACCOUNT:
                        break;

                    case NavigationHandler.NOTIFICATION:
                        closeDrawer();
                        transactNotification();
                        break;

                    case NavigationHandler.QUICK_REPLY:
                        closeDrawer();
                        transactQuickReply();
                        break;

                    case NavigationHandler.AVAILABILITY:
                        closeDrawer();
                        transactTimeAvailability();
                        break;

                    case NavigationHandler.SOCIAL_MEDIA:
                        closeDrawer();
                        transactSocialMedia();
                        break;

                    case NavigationHandler.SHARE_PROFILE:
                        closeDrawer();
                        break;

                    case NavigationHandler.REQUEST_REVIEW:
                        closeDrawer();
                        transactRequestReview();
                        break;

                    case NavigationHandler.INVITE_FRIEND:
                        closeDrawer();
                        transactInviteFriend();
                        break;

                    case NavigationHandler.LOGOUT:
                        closeDrawer();
                        startActivity(new Intent(LandScreenActivity.this,GetStartedActivity.class));
                        finish();
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

    private void transactQuickReply() {
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
    }


}
