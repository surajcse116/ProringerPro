package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ProsReviewAllAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 2/21/18.
 */

public class ProsReviewAllListActivity extends AppCompatActivity {
    public String pros_id = "", pros_company_name = "", img = "", total_avg_review = "", total_review = "";
    RecyclerView rcv_review_all;
    ProsReviewAllAdapter prosReviewAllAdapter;
    MyLoader myLoader = null;
    JSONArray jsonInfoReviewArray;
    ImageView img_profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pros_review_all_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jsonInfoReviewArray = new JSONArray();

        myLoader = new MyLoader(ProsReviewAllListActivity.this);

        img_profile = (ImageView) findViewById(R.id.img_profile);


        if (getIntent().getExtras() != null) {
            pros_id = getIntent().getExtras().getString("pros_id");
            img = getIntent().getExtras().getString("img");
            pros_company_name = getIntent().getExtras().getString("pros_company_name");
            total_avg_review = getIntent().getExtras().getString("total_avg_review");
            total_review = getIntent().getExtras().getString("total_review");
        }

        ((ProRegularTextView) findViewById(R.id.tv_toolbar)).setText(pros_company_name);


        rcv_review_all = (RecyclerView) findViewById(R.id.rcv_review_all);
        rcv_review_all.setLayoutManager(new LinearLayoutManager(ProsReviewAllListActivity.this));

        if (!img.trim().equals(""))
            Glide.with(ProsReviewAllListActivity.this).load(img).centerCrop().into(img_profile);

        ((ProRegularTextView) findViewById(R.id.tv_rate_value)).setText(total_avg_review);
        ((RatingBar) findViewById(R.id.rbar)).setRating(Float.parseFloat(total_avg_review));
        ((ProRegularTextView) findViewById(R.id.tv_review_value)).setText(total_review);


        findViewById(R.id.tv_review_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent();
        setResult(RESULT_CANCELED, backIntent);
//        setResult(GetStartedActivity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        jsonInfoReviewArray = new JSONArray();
        loadReviewList(0, 10);
    }

    public void loadReviewList(int from, int perPage) {

        findViewById(R.id.RLMain).setVisibility(View.VISIBLE);
        findViewById(R.id.LLNetworkDisconnection).setVisibility(View.GONE);

        ArrayList<SetGetAPIPostData> apiPostDataArrayList=new ArrayList<>();
        SetGetAPIPostData setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        apiPostDataArrayList.add(setGetAPIPostData);

        setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("pro_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        apiPostDataArrayList.add(setGetAPIPostData);

        setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("start_from");
        setGetAPIPostData.setValues(""+from);
        apiPostDataArrayList.add(setGetAPIPostData);

        setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("perpage");
        setGetAPIPostData.setValues(""+perPage);
        apiPostDataArrayList.add(setGetAPIPostData);


        new CustomJSONParser().fireAPIForGetMethod(ProsReviewAllListActivity.this, ProConstant.app_homeowner_allreview, apiPostDataArrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("Result", result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray info_array = jsonObject.getJSONArray("info_array");

                    for (int i = 0; i < info_array.length(); i++) {
                        jsonInfoReviewArray.put(info_array.getJSONObject(i));
                    }
                    if (prosReviewAllAdapter == null) {
                        prosReviewAllAdapter = new ProsReviewAllAdapter(ProsReviewAllListActivity.this, jsonInfoReviewArray);
                        rcv_review_all.setAdapter(prosReviewAllAdapter);
                    } else {
                        prosReviewAllAdapter.NotifyMeInLazyLoad(jsonInfoReviewArray);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();

            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(ProsReviewAllListActivity.this, "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }

                    @Override
                    public void callBackCancel() {

                    }
                });

                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                if (error.equalsIgnoreCase(getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection))) {
                    findViewById(R.id.RLMain).setVisibility(View.GONE);
                    findViewById(R.id.LLNetworkDisconnection).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }

}
