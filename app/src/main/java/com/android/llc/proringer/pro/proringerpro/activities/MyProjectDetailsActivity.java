package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ProjectListingAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.MyProjectsFragment;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 8/18/17.
 */

public class MyProjectDetailsActivity extends AppCompatActivity {
    int screenHeight;
    int screenWidth;
    String project_id="";
    ImageView img_project, img_map;
    RelativeLayout RLImage;

    ArrayList<SetGetAPI> arrayList = null;
    MyLoader myload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myload = new MyLoader(MyProjectDetailsActivity.this);

        project_id=getIntent().getStringExtra("project_id");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight =displaymetrics.heightPixels;
        screenWidth =displaymetrics.widthPixels;

        RLImage = (RelativeLayout) findViewById(R.id.RLImage);
        img_project = (ImageView) findViewById(R.id.img_project);
        img_map = (ImageView) findViewById(R.id.img_map);



        RLImage.getLayoutParams().height = (int) 3*(screenWidth - 20)/4;
        RLImage.getLayoutParams().width = screenWidth-20;

        img_map.getLayoutParams().height = (screenWidth - 10) / 2;

        showData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void showData() {
        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);

        setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("project_id");
        setGetAPI.setValues(project_id);
        arrayList.add(setGetAPI);

        new CustomJSONParser().fireAPIForGetMethod(MyProjectDetailsActivity.this, ProConstant.app_pro_myproject_details, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("Result", result);

                try {
                    JSONArray info_array=new JSONObject(result).getJSONArray("info_array");
                    JSONObject jsonObject=info_array.getJSONObject(0);

                    Glide.with(MyProjectDetailsActivity.this).load(jsonObject.getString("project_image")).into(img_project);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();

//                CustomAlert customAlert = new CustomAlert();
//                customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
//                    @Override
//                    public void callBackOk() {
//
//                    }
//
//                    @Override
//                    public void callBackCancel() {
//
//                    }
//                });
            }

            @Override
            public void onError(String error) {
                myload.dismissLoader();
                if (error.equalsIgnoreCase("No internet connection found. Please check your internet connection.")) {
//                    LLNetworkDisconnection.setVisibility(View.VISIBLE);
//                    LL_Main.setVisibility(View.GONE);
                }
            }

            @Override
            public void onStart() {
                myload.showLoader();
            }
        });
    }


}
