package com.android.llc.proringer.pro.proringerpro.activities;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.llc.proringer.pro.proringerpro.Constant.AppConstant;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.pojo.APIGetData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 7/27/17.
 */

public class TermsPrivacyActivity extends AppCompatActivity {
    MyLoader myLoader = null;
    ProRegularTextView tv_title;
    LinearLayout main_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_privacy);

        String HeaderString = getIntent().getStringExtra("value");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myLoader = new MyLoader(TermsPrivacyActivity.this);

        main_container = (LinearLayout) findViewById(R.id.main_container);

        tv_title = (ProRegularTextView) findViewById(R.id.tv_title);

        if (HeaderString.equalsIgnoreCase("term")) {
            tv_title.setText("Terms of Use");
            loadDataTermsOfUse();
        } else {
            tv_title.setText("Privacy Policy");
            loadPrivacyPolicy();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadDataTermsOfUse() {

        new CustomJSONParser().fireAPIForGetMethod(TermsPrivacyActivity.this, AppConstant.BASEURL + "app_term", new ArrayList<APIGetData>(), new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                Logger.printMessage("message", "" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv = new TextView(TermsPrivacyActivity.this);
                    tv.setLayoutParams(lparams);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tv.setText(Html.fromHtml(jsonObject.getString("page_content"), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        tv.setText(Html.fromHtml(jsonObject.getString("page_content")));
                    }
                    main_container.addView(tv);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                new CustomAlert().getOkEventFromNormalAlert(TermsPrivacyActivity.this, "Terms Of Use", "" + error, new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }

                    @Override
                    public void callBackCancel() {

                    }
                });

                if (error.equalsIgnoreCase(getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection))) {
                    findViewById(R.id.ScrollViewMAin).setVisibility(View.GONE);
                    findViewById(R.id.LLNetworkDisconnection).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }

    public void loadPrivacyPolicy() {

        new CustomJSONParser().fireAPIForGetMethod(TermsPrivacyActivity.this, AppConstant.BASEURL + "app_privacy_policy", new ArrayList<APIGetData>(), new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                Logger.printMessage("message", "" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv = new TextView(TermsPrivacyActivity.this);
                    tv.setLayoutParams(lparams);

                    if (Build.VERSION.SDK_INT >= 24) {
                        tv.setText(Html.fromHtml(jsonObject.getString("page_content"), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        tv.setText(Html.fromHtml(jsonObject.getString("page_content")));
                    }

                    main_container.addView(tv);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                new CustomAlert().getOkEventFromNormalAlert(TermsPrivacyActivity.this, "Privacy Policy", "" + error, new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }

                    @Override
                    public void callBackCancel() {

                    }
                });

                if (error.equalsIgnoreCase(getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection))) {
                    findViewById(R.id.ScrollViewMAin).setVisibility(View.GONE);
                    findViewById(R.id.LLNetworkDisconnection).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }
}
