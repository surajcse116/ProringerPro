package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.Constant.AppConstant;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.pojo.APIGetData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 7/19/17.
 */

public class FaqActivity extends AppCompatActivity {
    LinearLayout linear_main_container;
    MyLoader myLoader = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ProRegularTextView) findViewById(R.id.tv_title)).setText("Faq");


        myLoader = new MyLoader(FaqActivity.this);

        linear_main_container = (LinearLayout) findViewById(R.id.linear_main_container);

        new CustomJSONParser().fireAPIForGetMethod(FaqActivity.this, AppConstant.BASEURL + "app_faq", new ArrayList<APIGetData>(), new CustomJSONParser.CustomJSONResponse() {

            @Override
            public void onSuccess(String result) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    if (jsonObject.has("faq")) {

                        JSONArray faqArray = jsonObject.getJSONArray("faq");
                        Logger.printMessage("faq", "" + faqArray);

                        for (int i = 0; i < faqArray.length(); i++) {
                            LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lparams1.setMargins(0, 20, 0, 0);
                            ProRegularTextView tv1 = new ProRegularTextView(FaqActivity.this);
                            tv1.setLayoutParams(lparams1);
                            tv1.setText(faqArray.getJSONObject(i).getString("question"));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                tv1.setTextColor(getResources().getColor(R.color.colorAccent, null));
                            } else {
                                tv1.setTextColor(getResources().getColor(R.color.colorAccent));
                            }


                            LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lparams2.setMargins(0, 10, 0, 0);
                            ProRegularTextView tv2 = new ProRegularTextView(FaqActivity.this);
                            tv2.setLayoutParams(lparams2);
                            tv2.setText(faqArray.getJSONObject(i).getString("answer"));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                tv2.setTextColor(getResources().getColor(R.color.colorTextBlack, null));
                            } else {
                                tv1.setTextColor(getResources().getColor(R.color.colorTextBlack));
                            }

                            linear_main_container.addView(tv1);
                            linear_main_container.addView(tv2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                new CustomAlert().getOkEventFromNormalAlert(FaqActivity.this, "Faq", "" + error, new CustomAlert.MyCustomAlertListener() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
