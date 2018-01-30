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
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.LicenceAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetShowLicence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by su on 8/18/17.
 */

public class LicenceListActivity extends AppCompatActivity {

    RecyclerView rcv_licence_list;
    RelativeLayout RLEmpty;
    LicenceAdapter licenceAdapter;
    MyLoader myLoader;

    ArrayList<SetGetAPIPostData> arrayList = null;
    ArrayList<SetGetShowLicence> setGetShowLicenceArrayList;
    ImageView img_add_licence;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RLEmpty = (RelativeLayout) findViewById(R.id.RLEmpty);
        img_add_licence = (ImageView) findViewById(R.id.img_add_licence);

        myLoader = new MyLoader(LicenceListActivity.this);

        arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        setGetShowLicenceArrayList = new ArrayList<>();

        rcv_licence_list = (RecyclerView) findViewById(R.id.rcv_licence_list);
        rcv_licence_list.setLayoutManager(new LinearLayoutManager(LicenceListActivity.this));

//        RLEmpty.setVisibility(View.VISIBLE);

        Logger.printMessage("arrayLicenseSize", String.valueOf(setGetShowLicenceArrayList.size()));

        img_add_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(LicenceListActivity.this,LicenceAddActivity.class);
              startActivityForResult(intent,100);
            }
        });

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

        new CustomJSONParser().fireAPIForGetMethod(LicenceListActivity.this, ProConstant.liencelist, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                myLoader.dismissLoader();
                try {
                    JSONObject job = new JSONObject(result);
                    JSONArray info_arry = job.getJSONArray("info_array");
                    Logger.printMessage("info_array", String.valueOf(info_arry));
                    for (int i = 0; i < info_arry.length(); i++) {
                        JSONObject jo = info_arry.getJSONObject(i);
                        SetGetShowLicence show = new SetGetShowLicence();
                        show.setId(jo.getString("id"));
                        show.setPros_id(jo.getString("pros_id"));
                        show.setImage_info(jo.getString("image_info"));
                        show.setLicense_issuer(jo.getString("license_issuer"));
                        show.setLicenses_no(jo.getString("licenses_no"));
                        show.setDate_expire(jo.getString("date_expire"));
                        show.setDate_upload(jo.getString("date_upload"));
                        JSONObject catagory = jo.getJSONObject("category");
                        show.setCategory_id(catagory.getString("id"));
                        show.setCategory_name(catagory.getString("name"));
                        setGetShowLicenceArrayList.add(show);

                    }
                    if (setGetShowLicenceArrayList.size()==0) {
                        RLEmpty.setVisibility(View.VISIBLE);
                        rcv_licence_list.setVisibility(View.GONE);
                    } else {
                        RLEmpty.setVisibility(View.GONE);
                        rcv_licence_list.setVisibility(View.VISIBLE);
                        licenceAdapter = new LicenceAdapter(LicenceListActivity.this, setGetShowLicenceArrayList);

                        if (setGetShowLicenceArrayList.size() >= 2) {
                            img_add_licence.setVisibility(View.GONE);
                        } else {
                            img_add_licence.setVisibility(View.VISIBLE);
                        }
                        rcv_licence_list.setAdapter(licenceAdapter);
                    }

                    Logger.printMessage("LicenseSize", String.valueOf(setGetShowLicenceArrayList.size()));
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
                customAlert.getEventFromNormalAlert(LicenceListActivity.this, "Load Error", "" + error, "Ok", "Cancel", new CustomAlert.MyCustomAlertListener() {
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
                myLoader.showLoader();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==100){
            if(resultCode == RESULT_OK) {
//                String strEditText = data.getStringExtra("TextValue");
                setGetShowLicenceArrayList.clear();
                showData();
            }
        }

    }
}
