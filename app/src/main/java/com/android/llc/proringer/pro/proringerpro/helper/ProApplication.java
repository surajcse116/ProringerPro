package com.android.llc.proringer.pro.proringerpro.helper;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;

import com.android.llc.proringer.pro.proringerpro.pojo.SetGetNotificationData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetProjectPostedData;

/**
 * Created by su on 22/11/17.
 */

public class ProApplication extends Application {
    private static ProApplication instance = null;
    private SharedPreferences userPreference = null;
    private SharedPreferences notificationPreference = null;
    private SetGetProjectPostedData dataSelected = null;



    public static ProApplication getInstance() {

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        userPreference = getSharedPreferences("USER_PREFERENCE", MODE_PRIVATE);
        notificationPreference = getSharedPreferences("NOTIFICATION_PREFERENCE", MODE_PRIVATE);
    }

    public void setUserPreference(String user_id,
                                  String user_type,
                                  String first_name,
                                  String last_name) {
        userPreference.edit().clear().apply();
        userPreference.edit().putString("UserId", user_id).apply();
        userPreference.edit().putString("UserId", user_id).apply();
        userPreference.edit().putString("UserType", user_type).apply();
        userPreference.edit().putString("UserFirstName", first_name).apply();
        userPreference.edit().putString("UserLastName", last_name).apply();
    }

    public void setNotificationPreference(String... params) {
        notificationPreference.edit().clear().apply();
        notificationPreference.edit()
                .putString("email_newsletter", params[0])
                .putString("email_chat_msg", params[1])
                .putString("email_tips_article", params[2])
                .putString("email_project_replies", params[3])
                .putString("email_newreviw", params[4])
                .putString("account_achivement", params[5])
                .putString("mobile_newsletter", params[6])
                .putString("mobile_chat_msg", params[7])
                .putString("mobile_tips_article", params[8])
                .putString("mobile_project_replies", params[9])
                .putString("mobileaccountachivement", params[10])
                .putString("mobilereview", params[11])
                .apply();

    }

    public SetGetNotificationData getUserNotification() {
        return new SetGetNotificationData(
                notificationPreference.getString("email_newsletter", "FALSE"),
                notificationPreference.getString("email_chat_msg", "FALSE"),
                notificationPreference.getString("email_tips_article", "FALSE"),
                notificationPreference.getString("email_project_replies", "FALSE"),
                notificationPreference.getString("email_newreviw", "FALSE"),
                notificationPreference.getString("account_achivement", "FALSE"),
                notificationPreference.getString("mobile_newsletter", "FALSE"),
                notificationPreference.getString("mobile_chat_msg", "FALSE"),
                notificationPreference.getString("mobile_tips_article", "FALSE"),
                notificationPreference.getString("mobile_project_replies", "FALSE"),
                notificationPreference.getString("mobileaccountachivement", "FALSE"),
                notificationPreference.getString("mobilereview", "FALSE")
        );
    }

    public String getUserId() {
        return userPreference.getString("UserId", "");
    }

    public String getZipCode() {
        return userPreference.getString("ZipCode", "");
    }

    public String getUserType() {
        return userPreference.getString("UserType", "");
    }

    public String getUserFirstName() {
        return userPreference.getString("UserFirstName", "");
    }

    public String getUserEmail() {
        return userPreference.getString("userEmail", "");
    }

    public void setUserEmail(String email) {
        userPreference.edit().putString("userEmail", email).apply();
    }

    public void logOut() {
        userPreference.edit().clear().apply();
    }

    public SetGetProjectPostedData getDataSelected() {
        return dataSelected;
    }

    public void setDataSelected(SetGetProjectPostedData dataSelected) {
        this.dataSelected = dataSelected;
    }


    public static void SetRatingColor(LayerDrawable stars) {
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FFf1592a"),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(Color.parseColor("#FFf1592a"),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(Color.parseColor("#FFd6d7db"),
                PorterDuff.Mode.SRC_ATOP); // for empty stars
    }

}
