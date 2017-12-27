package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
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
    ImageView img_project, img_map,img_likes;
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
        img_likes = (ImageView) findViewById(R.id.img_likes);



        RLImage.getLayoutParams().height = (int) 3*(screenWidth - 20)/4;
        RLImage.getLayoutParams().width = screenWidth-20;

        img_map.getLayoutParams().height = (int) 2*(screenWidth - 10) / 5;

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

                    ((ProRegularTextView)findViewById(R.id.tv_posted_by_value)).setText(jsonObject.getString("posted_by"));
                    ((ProRegularTextView)findViewById(R.id.tv_posted_date)).setText(jsonObject.getString("post_date"));
                    ((ProRegularTextView)findViewById(R.id.tv_address)).setText(jsonObject.getString("address"));

                    Glide.with(MyProjectDetailsActivity.this).load(jsonObject.getString("project_image")).into(img_project);
                    ((ProRegularTextView)findViewById(R.id.tv_project_title)).setText(jsonObject.getString("project_name"));
                    ((ProRegularTextView)findViewById(R.id.tv_project_title)).setText(jsonObject.getString("project_name"));
                    ((ProRegularTextView)findViewById(R.id.tv_type_of_work)).setText(jsonObject.getString("type_of_work"));
                    ((ProRegularTextView)findViewById(R.id.tv_service)).setText(jsonObject.getString("service"));
                    ((ProRegularTextView)findViewById(R.id.tv_property)).setText(jsonObject.getString("property"));
                    ((ProRegularTextView)findViewById(R.id.tv_phone)).setText(jsonObject.getString("phone"));
                    ((ProRegularTextView)findViewById(R.id.tv_describetion)).setText(jsonObject.getString("job_details"));

                    if (jsonObject.getString("add_favouite").equalsIgnoreCase("0")){
//                        img_likes.setImageResource(R.drawable.ic_unfavorite);
                        Glide.with(MyProjectDetailsActivity.this).load("").placeholder(R.drawable.ic_unfavorite).into(img_likes);
                    }else {
//                        img_likes.setImageResource(R.drawable.ic_favorite);
                        Glide.with(MyProjectDetailsActivity.this).load("").placeholder(R.drawable.ic_favorite).into(img_likes);
                    }

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
