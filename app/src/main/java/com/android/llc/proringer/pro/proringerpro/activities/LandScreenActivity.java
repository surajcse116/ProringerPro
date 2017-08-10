package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.FragmentAvailabilityTimeSlot;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.MessageFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.InviteAfriend;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.Notifications;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.QuickReply;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.RequestReview;
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.BottomNav;
import com.android.llc.proringer.pro.proringerpro.viewsmod.NavigationHandler;


public class LandScreenActivity extends AppCompatActivity {
    ImageView nav_toggle;
    private DrawerLayout mDrawer;
    public BottomNav bottomNavInstance = null;

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
                        break;
                    case BottomNav.MY_PROJECTS:
                        closeDrawer();
                        break;
                    case BottomNav.MESSAGES:
                        transactMessages();
                        closeDrawer();
                        break;
                    case BottomNav.FAV_PROS:
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

                    case NavigationHandler.REQUEST_REVIEW:
                        closeDrawer();
                        transactRequestReview();
                        break;
                    case NavigationHandler.QUICK_REPLY:
                        closeDrawer();
                        transactQuickReply();
                        break;
                    case NavigationHandler.NOTIFICATION:
                        closeDrawer();
                        transactNotification();
                        break;

                    case NavigationHandler.INVITE_FRIEND:
                        closeDrawer();
                        transactInviteFriend();
                        break;

                    case NavigationHandler.AvailableTimeSlot:
                        closeDrawer();
                        transactTimeAvailibility();
                        break;
                    case NavigationHandler.LOGOUT:
                        closeDrawer();
                        Intent intent=new Intent(LandScreenActivity.this,GetStarted.class);
                        startActivity(intent);
                        finish();
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

    private void transactInviteFriend() {

        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + InviteAfriend.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + InviteAfriend.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + InviteAfriend.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + InviteAfriend.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new InviteAfriend(), "" + InviteAfriend.class.getCanonicalName());
        trasaction.addToBackStack("" + InviteAfriend.class.getCanonicalName());
        trasaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }

    private void transactQuickReply() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + QuickReply.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + QuickReply.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + QuickReply.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + QuickReply.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new QuickReply(), "" + QuickReply.class.getCanonicalName());
        trasaction.addToBackStack("" + QuickReply.class.getCanonicalName());
        trasaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }

    private void transactRequestReview() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + RequestReview.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + QuickReply.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + RequestReview.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + RequestReview.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new RequestReview(), "" + RequestReview.class.getCanonicalName());
        trasaction.addToBackStack("" + RequestReview.class.getCanonicalName());
        trasaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }

    private void transactNotification() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + Notifications.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + Notifications.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + Notifications.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + Notifications.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new Notifications(), "" + Notifications.class.getCanonicalName());
        trasaction.addToBackStack("" + Notifications.class.getCanonicalName());
        trasaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }


    private void transactTimeAvailibility() {

        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + FragmentAvailabilityTimeSlot.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + FragmentAvailabilityTimeSlot.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + FragmentAvailabilityTimeSlot.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + FragmentAvailabilityTimeSlot.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new FragmentAvailabilityTimeSlot(), "" + FragmentAvailabilityTimeSlot.class.getCanonicalName());
        trasaction.addToBackStack("" + FragmentAvailabilityTimeSlot.class.getCanonicalName());
        trasaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());
    }


    /**
     * Fragment transaction for Messages
     */
    private void transactMessages() {
//        toggleToolBar(false);

        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + MessageFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + MessageFragment.class.getCanonicalName());
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + MessageFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + MessageFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new MessageFragment(), "" + MessageFragment.class.getCanonicalName());
        trasaction.addToBackStack("" + MessageFragment.class.getCanonicalName());
        trasaction.commit();

        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());

        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            Logger.printMessage("back_stack", "" + getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
    }

}
