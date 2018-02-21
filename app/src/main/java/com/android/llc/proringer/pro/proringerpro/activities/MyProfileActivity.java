package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ProDetailsServiceAreaDialogAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.ProDetailsServiceDialogAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.ProsDetailsBusinessHourAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.ProsDetailsServiceAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 2/19/18.
 */

public class MyProfileActivity extends AppCompatActivity {
    ArrayList<SetGetAPIPostData> arrayList = null;
    MyLoader myload;
    JSONObject infoArrayJsonObject = null;
    JSONObject infoJsonObject = null;
    ImageView img_top, img_profile;
    ProsDetailsServiceAdapter proDetailsService;
    ProsDetailsBusinessHourAdapter prosDetailsBusinessHourAdapter;
    RecyclerView rcv_service,rcv_business_hour,rcv_service_area,rcv_license;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        img_top = (ImageView) findViewById(R.id.img_top);
        img_profile = (ImageView)findViewById(R.id.img_profile);

        rcv_service = (RecyclerView) findViewById(R.id.rcv_service);
        rcv_service.setLayoutManager(new GridLayoutManager(MyProfileActivity.this, 2));

        rcv_business_hour = (RecyclerView) findViewById(R.id.rcv_business_hour);
        rcv_business_hour.setLayoutManager(new LinearLayoutManager(MyProfileActivity.this));

        rcv_service_area = (RecyclerView) findViewById(R.id.rcv_service_area);
        rcv_service_area.setLayoutManager(new GridLayoutManager(MyProfileActivity.this, 2));

        rcv_license = (RecyclerView) findViewById(R.id.rcv_license);
        rcv_license.setLayoutManager(new GridLayoutManager(MyProfileActivity.this, 2));

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myload = new MyLoader(MyProfileActivity.this);
        loadAndShowData();
    }

    public void loadAndShowData() {
        arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(MyProfileActivity.this, ProConstant.app_pro_myprofile, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("Result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Logger.printMessage("result", String.valueOf(jsonObject));

                    infoArrayJsonObject = jsonObject.getJSONObject("info_array");
                    infoJsonObject = infoArrayJsonObject.getJSONObject("info");

                    if (!infoJsonObject.getString("header_image").equals(""))
                        Glide.with(MyProfileActivity.this).load(infoJsonObject.getString("header_image")).centerCrop().into(img_top);

                    if (!infoJsonObject.getString("profile_image").equals(""))
                        Glide.with(MyProfileActivity.this).load(infoJsonObject.getString("profile_image")).centerCrop().into(img_profile);


                    if (infoJsonObject.getString("verified_status").trim().equalsIgnoreCase("Y")) {
                        ((ProRegularTextView) findViewById(R.id.tv_contact_pro_btn_unverified)).setVisibility(View.GONE);
                        ((LinearLayout) findViewById(R.id.LLVerified)).setVisibility(View.VISIBLE);
                    } else {
                        ((LinearLayout) findViewById(R.id.LLVerified)).setVisibility(View.GONE);
                        ((ProRegularTextView) findViewById(R.id.tv_contact_pro_btn_unverified)).setVisibility(View.VISIBLE);
                    }

                    ((ProRegularTextView) findViewById(R.id.tv_company_name)).setText(infoJsonObject.getString("company_name"));
                    ((ProRegularTextView) findViewById(R.id.tv_user_name)).setText(infoJsonObject.getString("user_name"));
                    ((ProRegularTextView) findViewById(R.id.tv_designation)).setText(infoJsonObject.getString("designation"));
                    ((ProRegularTextView) findViewById(R.id.tv_address)).setText(infoJsonObject.getString("address"));
                    ((ProRegularTextView) findViewById(R.id.tv_city_state_zipcode)).setText(infoJsonObject.getString("city") + ", " + infoJsonObject.getString("state") + " " + infoJsonObject.getString("zipcode"));


                    ((ProRegularTextView) findViewById(R.id.tv_review_value)).setText(infoArrayJsonObject.getString("total_review"));
                    ((ProRegularTextView) findViewById(R.id.tv_rate_value)).setText(infoArrayJsonObject.getString("total_avg_review"));

                    ((RatingBar) findViewById(R.id.rbar)).setRating(Float.parseFloat(infoArrayJsonObject.getString("total_avg_review")));

                    ((ProRegularTextView) findViewById(R.id.tv_about)).setText(infoArrayJsonObject.getJSONObject("about").getString("description"));

                    if (infoArrayJsonObject.getJSONArray("services").length() > 14) {
                        ((ProRegularTextView) findViewById(R.id.view_all_service)).setVisibility(View.VISIBLE);
                    } else {
                        ((ProRegularTextView) findViewById(R.id.view_all_service)).setVisibility(View.GONE);
                    }

                    findViewById(R.id.view_all_service).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (infoArrayJsonObject.getJSONArray("services").length() > 14) {
                                    showServicesDialog(infoArrayJsonObject.getJSONArray("services"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    proDetailsService = new ProsDetailsServiceAdapter(MyProfileActivity.this, infoArrayJsonObject.getJSONArray("services"), new onOptionSelected() {
                        @Override
                        public void onItemPassed(int position, String value) {
                            try {
                                if (value.equalsIgnoreCase("more")) {
                                    showServicesDialog(infoArrayJsonObject.getJSONArray("services"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    rcv_service.setAdapter(proDetailsService);


                    if (infoJsonObject.getString("business_hour").trim().equals("0")) {
                        rcv_business_hour.setVisibility(View.VISIBLE);
                        ((ProRegularTextView) findViewById(R.id.tv_business_hour)).setVisibility(View.GONE);
                        prosDetailsBusinessHourAdapter = new ProsDetailsBusinessHourAdapter(MyProfileActivity.this, infoArrayJsonObject.getJSONArray("business_hours"));
                        rcv_business_hour.setAdapter(prosDetailsBusinessHourAdapter);
                    } else {
                        rcv_business_hour.setVisibility(View.GONE);
                        ((ProRegularTextView) findViewById(R.id.tv_business_hour)).setVisibility(View.VISIBLE);
                        ((ProRegularTextView) findViewById(R.id.tv_business_hour)).setText("Always Open");
                    }

                    if (infoArrayJsonObject.getJSONArray("service_area").length() > 14) {
                        findViewById(R.id.view_all_service_area).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.view_all_service_area).setVisibility(View.GONE);
                    }

                    findViewById(R.id.view_all_service_area).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (infoArrayJsonObject.getJSONArray("service_area").length() > 14) {
                                    showServiceAreaDialog(infoArrayJsonObject.getJSONArray("service_area"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();

            }

            @Override
            public void onError(String error) {
                myload.dismissLoader();
                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(MyProfileActivity.this, "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }

                    @Override
                    public void callBackCancel() {

                    }
                });
            }

            @Override
            public void onStart() {
                myload.showLoader();
            }
        });
    }

    private void showServicesDialog(JSONArray serviceAreaJsonArray) {
        final Dialog dialog = new Dialog(MyProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_pro_details_service_area);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ((ProRegularTextView) dialog.findViewById(R.id.tv_title)).setText("Services");

        RecyclerView rcv_show_service_area = (RecyclerView) dialog.findViewById(R.id.rcv_show_service_area);
        rcv_show_service_area.setLayoutManager(new GridLayoutManager(MyProfileActivity.this, 2));

        dialog.findViewById(R.id.img_cancel_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ProDetailsServiceDialogAdapter proDetailsServiceDialogAdapter = new ProDetailsServiceDialogAdapter(MyProfileActivity.this, serviceAreaJsonArray);
        rcv_show_service_area.setAdapter(proDetailsServiceDialogAdapter);

        rcv_show_service_area.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(MyProfileActivity.this)[1] - 30);
//        rcv_show_service_area.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(ProsProjectDetailsActivity.this)[1] - 30);
        rcv_show_service_area.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
//        rcv_show_service_area.getLayoutParams().height = (height-30)/2;

        dialog.show();
    }

    private void showServiceAreaDialog(JSONArray serviceAreaJsonArray) {
        final Dialog dialog = new Dialog(MyProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_pro_details_service_area);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ((ProRegularTextView) dialog.findViewById(R.id.tv_title)).setText("Service Area");

        RecyclerView rcv_show_service_area = (RecyclerView) dialog.findViewById(R.id.rcv_show_service_area);
        rcv_show_service_area.setLayoutManager(new GridLayoutManager(MyProfileActivity.this, 2));

        dialog.findViewById(R.id.img_cancel_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ProDetailsServiceAreaDialogAdapter proDetailsServiceAreaDialogAdapter = new ProDetailsServiceAreaDialogAdapter(MyProfileActivity.this, serviceAreaJsonArray);
        rcv_show_service_area.setAdapter(proDetailsServiceAreaDialogAdapter);

        rcv_show_service_area.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(MyProfileActivity.this)[1] - 30);
//        rcv_show_service_area.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(ProsProjectDetailsActivity.this)[1] - 30);
        rcv_show_service_area.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        //        rcv_show_service_area.getLayoutParams().height = (height-30)/2;

        dialog.show();
    }


    public interface onOptionSelected {
        void onItemPassed(int position, String value);
    }
}
