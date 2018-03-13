package com.android.llc.proringer.pro.proringerpro.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;


import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AvailabilityTimeSlotAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetBusinessHour;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProLightTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by su on 8/12/17.
 */

public class BusinessHourActivity extends AppCompatActivity {
    AvailabilityTimeSlotAdapter availabilityTimeSlotAdapter;
    private RecyclerView rcv_;
    ProLightTextView tv_save;
    RadioButton rb_openselected,rb_alwayopen;
    ArrayList<SetGetBusinessHour> businessHourArrayList=null;
    ArrayList<SetGetAPIPostData> arrayList;
    String Business_hr_type;
    MyLoader myLoader;

    ArrayList<SetGetBusinessHour> selectedbusoinessArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_hour);
        rb_openselected=(RadioButton)findViewById(R.id.rb_openselected);
        rb_alwayopen=(RadioButton)findViewById(R.id.rb_alwayopen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_save=findViewById(R.id.tv_save);
        businessHourArrayList=new ArrayList<>();
        selectedbusoinessArrayList=new ArrayList<>();
        rcv_ = (RecyclerView) findViewById(R.id.rcv_);
        myLoader=new MyLoader(BusinessHourActivity.this);
        rcv_.setLayoutManager(new LinearLayoutManager(BusinessHourActivity.this));

        callapp_pro_businesshours();

        rb_openselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcv_.setVisibility(View.VISIBLE);
                Business_hr_type="0";
            }
        });
        rb_alwayopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcv_.setVisibility(View.GONE);
                Business_hr_type="1";
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callapp_pro_businesshour_save();
            }
        });

    }

    private void callapp_pro_businesshour_save() {

        HashMap<String, String> Params = new HashMap<>();
        HashMap<String, String> Params1 = new HashMap<>();
        HashMap<String, String> Params2 = new HashMap<>();
        HashMap<String, String> Params3 = new HashMap<>();

        Params.put("user_id",ProApplication.getInstance().getUserId());
        Params.put("Business_hr_type",Business_hr_type);
       /// SetGetBusinessHour  setGetBusinessHour=new SetGetBusinessHour();
        for (int index=0;index<businessHourArrayList.size();index++)
        {
            if (businessHourArrayList.get(index).getDay_status().equalsIgnoreCase("Y"))
            {
                if (businessHourArrayList.get(index).getDay_name().equalsIgnoreCase("Monday")){
                    Params1.put("day"+"["+ index+"]","1");
                    Params2.put("start_time"+"["+ index+"]",businessHourArrayList.get(index).getStartTime());
                    Params3.put("end_time"+"["+index+"]",businessHourArrayList.get(index).getEndTime());

                }else  if (businessHourArrayList.get(index).getDay_name().equalsIgnoreCase("Tuesday")){
                    Params1.put("day"+"["+ index+"]","2");
                    Params2.put("start_time"+"["+ index+"]",businessHourArrayList.get(index).getStartTime());
                    Params3.put("end_time"+"["+index+"]",businessHourArrayList.get(index).getEndTime());
                } else if (businessHourArrayList.get(index).getDay_name().equalsIgnoreCase("Wednesday")){
                    Params1.put("day"+"["+ index+"]","3");
                    Params2.put("start_time"+"["+ index+"]",businessHourArrayList.get(index).getStartTime());
                    Params3.put("end_time"+"["+index+"]",businessHourArrayList.get(index).getEndTime());
                }else if (businessHourArrayList.get(index).getDay_name().equalsIgnoreCase("Thursday")){
                    Params1.put("day"+"["+ index+"]","4");
                    Params2.put("start_time"+"["+ index+"]",businessHourArrayList.get(index).getStartTime());
                    Params3.put("end_time"+"["+index+"]",businessHourArrayList.get(index).getEndTime());
                }else if (businessHourArrayList.get(index).getDay_name().equalsIgnoreCase("Friday")){
                    Params1.put("day"+"["+ index+"]","5");
                    Params2.put("start_time"+"["+ index+"]",businessHourArrayList.get(index).getStartTime());
                    Params3.put("end_time"+"["+index+"]",businessHourArrayList.get(index).getEndTime());
                }else if (businessHourArrayList.get(index).getDay_name().equalsIgnoreCase("Saturday")){
                    Params1.put("day"+"["+ index+"]","6");
                    Params2.put("start_time"+"["+ index+"]",businessHourArrayList.get(index).getStartTime());
                    Params3.put("end_time"+"["+index+"]",businessHourArrayList.get(index).getEndTime());
                }else if (businessHourArrayList.get(index).getDay_name().equalsIgnoreCase("Sunday")){
                    Params1.put("day"+"["+ index+"]","7");
                    Params2.put("start_time"+"["+ index+"]",businessHourArrayList.get(index).getStartTime());
                    Params3.put("end_time"+"["+index+"]",businessHourArrayList.get(index).getEndTime());
                }
            }else {
                Params1.put("day"+"["+ index+"]","");
                Params2.put("start_time"+"["+ index+"]","");
                Params3.put("end_time"+"["+index+"]","");
            }
        }
        Logger.printMessage("Params-->",""+Params);


        new CustomJSONParser().fireAPIForPostMethodNormalTxtThreeArray(BusinessHourActivity.this, ProConstant.app_pro_businesshour_save, Params, Params1,Params2,Params3, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }

    private void callapp_pro_businesshours() {
        arrayList=new ArrayList<>();
        SetGetAPIPostData setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);


        new CustomJSONParser().fireAPIForGetMethod(BusinessHourActivity.this, ProConstant.app_pro_businesshours, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                try {
                    JSONObject mainObj=new JSONObject(result);
                    JSONObject main_infoarray=mainObj.getJSONObject("info_array");
                    if (main_infoarray.getString("business_hour_type").equalsIgnoreCase("")){
                        Business_hr_type="0";
                        rcv_.setVisibility(View.VISIBLE);
                    }else {
                        if (main_infoarray.getString("business_hour_type").equalsIgnoreCase("Open on selected hours")){
                            Business_hr_type="0";
                            rcv_.setVisibility(View.VISIBLE);
                        }else {
                            Business_hr_type="1";
                            rcv_.setVisibility(View.GONE);
                        }
                    }

                    JSONArray business_hr_list=main_infoarray.getJSONArray("business_hr_list");
                    for (int i=0;i<business_hr_list.length();i++)
                    {
                        JSONObject object=business_hr_list.getJSONObject(i);
                        SetGetBusinessHour setGetBusinessHour=new SetGetBusinessHour();
                        setGetBusinessHour.setId(object.getString("id"));
                        setGetBusinessHour.setStartTime(object.getString("start_time"));
                        setGetBusinessHour.setEndTime(object.getString("end_time"));
                        setGetBusinessHour.setDay_name(object.getString("day_name"));
                        setGetBusinessHour.setDay_status(object.getString("day_status"));
                        businessHourArrayList.add(setGetBusinessHour);

                    }

                    availabilityTimeSlotAdapter = new AvailabilityTimeSlotAdapter(BusinessHourActivity.this,businessHourArrayList);
                    rcv_.setAdapter(availabilityTimeSlotAdapter);
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
