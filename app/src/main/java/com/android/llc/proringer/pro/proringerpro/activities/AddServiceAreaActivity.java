package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ServiceAreaAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetCityServiceArea;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by su on 8/17/17.
 */

public class AddServiceAreaActivity extends AppCompatActivity {

    ArrayList<SetGetAPIPostData> arrayList = null;
    RecyclerView rcv_service_area;
    ServiceAreaAdapter serviceAreaAdapter = null;
    ArrayList<String> stringArrayList = null;
    ArrayList<SetGetCityServiceArea> cityServiceAreaArrayList;

    MyLoader myLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_area);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myLoader = new MyLoader(AddServiceAreaActivity.this);

        arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        cityServiceAreaArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();


        rcv_service_area = (RecyclerView) findViewById(R.id.rcv_service_area);
        rcv_service_area.setLayoutManager(new GridLayoutManager(AddServiceAreaActivity.this, 2));



        findViewById(R.id.relative_search_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddServiceAreaActivity.this, LocationActivity.class);
                startActivityForResult(i, 1);
            }
        });

        showData();


        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((ProRegularTextView)findViewById(R.id.tv_cities)).getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(AddServiceAreaActivity.this,"Please Enter City name",Toast.LENGTH_SHORT).show();
                }else {

                    for (int i=0;i<stringArrayList.size();i++){
                        Logger.printMessage("value-->",stringArrayList.get(i));
                    }

                    if (stringArrayList.indexOf(((ProRegularTextView)findViewById(R.id.tv_cities)).getText().toString().trim())==-1)
                    {
                        createListAndAdapterSet(((ProRegularTextView)findViewById(R.id.tv_cities)).getText().toString().trim());
                    }else {
                        ((ProRegularTextView)findViewById(R.id.tv_cities)).setText("");
                        //Toast.makeText(AddServiceAreaActivity.this,"Already exist this address",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.tv_continue_service_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveData();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.printMessage("data", String.valueOf(data));

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle extras = data.getBundleExtra("data");
                if (extras != null) {

                    Logger.printMessage("selectedPlace", "--->" + extras.getString("selectedPlace"));
                    Logger.printMessage("zip", "--->" + extras.getString("zip"));
                    Logger.printMessage("city", "--->" + extras.getString("city"));
                    Logger.printMessage("state", "--->" + extras.getString("state"));

                    if (!extras.getString("city").equals("") && !extras.getString("state").equals("")) {
                        ((ProRegularTextView) findViewById(R.id.tv_cities)).setText(extras.getString("city") + ", " + extras.getString("state"));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    } else if (!extras.getString("city").equals("") && extras.getString("state").equals("")) {
                        ((ProRegularTextView) findViewById(R.id.tv_cities)).setText(extras.getString("city"));

                    } else if (extras.getString("city").equals("") && !extras.getString("state").equals("")) {
                        ((ProRegularTextView) findViewById(R.id.tv_cities)).setText(extras.getString("state"));
                    } else {
                        ((ProRegularTextView) findViewById(R.id.tv_cities)).setText("");
                    }
                }

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createListAndAdapterSet(String city_service) {

        stringArrayList.add(city_service);
        Logger.printMessage("ListSize-->", "" + stringArrayList.size());

        if (serviceAreaAdapter == null) {
            serviceAreaAdapter = new ServiceAreaAdapter(AddServiceAreaActivity.this, stringArrayList);
            rcv_service_area.setAdapter(serviceAreaAdapter);
        } else {
            serviceAreaAdapter.notifyDataSetChanged();
        }
    }

    public void showData() {

        new CustomJSONParser().fireAPIForGetMethod(AddServiceAreaActivity.this, ProConstant.app_pro_servicearea, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                myLoader.dismissLoader();
                try {
                    JSONObject job = new JSONObject(result);
                    for (int i = 0; i < job.getJSONArray("info_array").length(); i++) {

                        stringArrayList.add(job.getJSONArray("info_array").getJSONObject(i).getString("city_service"));

                        SetGetCityServiceArea setGetCityServiceArea = new SetGetCityServiceArea();
                        setGetCityServiceArea.setId(job.getJSONArray("info_array").getJSONObject(i).getString("id"));
                        setGetCityServiceArea.setCity_service(job.getJSONArray("info_array").getJSONObject(i).getString("city_service"));
                        cityServiceAreaArrayList.add(setGetCityServiceArea);

                    }

                    if (stringArrayList.size()>0) {

                        if (serviceAreaAdapter == null) {
                            serviceAreaAdapter = new ServiceAreaAdapter(AddServiceAreaActivity.this, stringArrayList);
                            rcv_service_area.setAdapter(serviceAreaAdapter);
                        } else {
                            serviceAreaAdapter.notifyDataSetChanged();
                        }
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
                customAlert.getEventFromNormalAlert(AddServiceAreaActivity.this, "Load Error", "" + error, "Ok", "Cancel", new CustomAlert.MyCustomAlertListener() {
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

    public void saveData(){

        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id", ProApplication.getInstance().getUserId());

        try{
            for (int chooseArea=0;chooseArea<stringArrayList.size();chooseArea++){
                Params.put("citi"+"["+chooseArea+"]", stringArrayList.get(chooseArea));
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        Logger.printMessage("PARAMS", String.valueOf(Params));

        new CustomJSONParser().fireAPIForPostMethod(AddServiceAreaActivity.this, ProConstant.app_pro_servicearea_save, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                JSONObject mainResponseObj = null;
                try {
                    mainResponseObj = new JSONObject(result);
                    Logger.printMessage("message", mainResponseObj.getString("message"));
                    Toast.makeText(AddServiceAreaActivity.this,mainResponseObj.getString("message"),Toast.LENGTH_SHORT).show();

                    cityServiceAreaArrayList.clear();
                    stringArrayList.clear();
                    ((ProRegularTextView) findViewById(R.id.tv_cities)).setText("");
                    showData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
                new MYAlert(AddServiceAreaActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                    @Override
                    public void OnOk(boolean res) {

                    }
                });
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                new MYAlert(AddServiceAreaActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                    @Override
                    public void OnOk(boolean res) {

                    }
                });
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }

}
