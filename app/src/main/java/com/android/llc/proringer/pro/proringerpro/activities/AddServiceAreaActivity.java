package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ServiceAreaAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SavePojo;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
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

    RecyclerView rcv_service_area;
    ServiceAreaAdapter serviceAreaAdapter=null;
    ArrayList<SetGetAPI> arrayList=null;
    ProRegularTextView tv_continue_service_area;
    ProRegularTextView edt_cities;
    String pros_contact_service="";
    ImageView dropdown;
    String city=null;
    JSONObject JO= null;
    JSONObject json=null;
    RelativeLayout relative_dropdown;
    ArrayList<SavePojo> previousvalue= null;
    boolean flag=true;
    String value;
    SavePojo save;
    int count;
    String arrycity;
    MyLoader myload;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_area);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        arrayList=new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI =new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);
        myload= new MyLoader(AddServiceAreaActivity.this);
        tv_continue_service_area=(ProRegularTextView)findViewById(R.id.tv_continue_service_area);
//        dropdown=(ImageView)findViewById(R.id.dropdown);
        edt_cities=(ProRegularTextView)findViewById(R.id.edt_cities);
        relative_dropdown=(RelativeLayout)findViewById(R.id.relative_dropdown);
        relative_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddServiceAreaActivity.this, LocationActivity.class);
                startActivityForResult(i, 1);
            }
        });

        tv_continue_service_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }
        });

        rcv_service_area= (RecyclerView) findViewById(R.id.rcv_service_area);
        rcv_service_area.setLayoutManager(new GridLayoutManager(AddServiceAreaActivity.this, 2));

        save=new SavePojo();
        previousvalue= new ArrayList<SavePojo>();

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = edt_cities.getText().toString();

                if (((ProRegularTextView) findViewById(R.id.edt_cities)).getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(AddServiceAreaActivity.this, " Select service area", Toast.LENGTH_SHORT).show();
                } else {

                    createListAndAdapterSet(((ProRegularTextView) findViewById(R.id.edt_cities)).getText().toString().trim(),pros_contact_service);

                }

            }
        });

//        dropdown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialogServies(view, servicareaJsonArray);
//            }
//        });
    }
    //    public void showcity()
//    {
//        new CustomJSONParser().fireAPIForGetMethod(AddServiceAreaActivity.this, ProConstant.servicarea, arrayList, new CustomJSONParser.CustomJSONResponse() {
//            @Override
//            public void onSuccess(String result) {
//                Logger.printMessage("result",result);
//                try {
//                    JSONObject job= new JSONObject(result);
//                    servicareaJsonArray=job.getJSONArray("info_array");
//                    Logger.printMessage("infoarray", String.valueOf(servicareaJsonArray));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(String error, String response) {
//
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        });
//
//    }
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

                    if (!extras.getString("selectedPlace").equals("")) {
                        edt_cities.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }
                    pros_contact_service=ProConstant.placeid;
                    Logger.printMessage("myplaceid",ProConstant.placeid);

                }

            }
        }
    }
//    private void showDialogServies(View v, JSONArray PredictionsJsonArray) {
//
//        popupWindow = new PopupWindow(AddServiceAreaActivity.this);
//        // Closes the popup window when touch outside.
//        popupWindow.setOutsideTouchable(true);
//        // Removes default background.
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        View dailogView =AddServiceAreaActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);
//
//        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
//        rcv_.setLayoutManager(new LinearLayoutManager(AddServiceAreaActivity.this));
//
//        custom = new CustomListAdapterDialog_service(AddServiceAreaActivity.this,PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
//            @Override
//            public void onItemPassed(int position, JSONObject value) {
//                popupWindow.dismiss();
//
//                Logger.printMessage("value", "" + value);
//                try {
//                    ((ProRegularTextView) findViewById(R.id.edt_cities)).setText(value.getString("city_service"));
//                    city = value.getString("city_service");
//                    pros_contact_service = value.getString("id");
//                    anotherservice = pros_contact_service;
//                    Logger.printMessage("id", pros_contact_service);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//        rcv_.setAdapter(custom);
//        // some other visual settings
//        popupWindow.setFocusable(false);
//        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//
//        // set the list view as pop up window content
//        popupWindow.setContentView(dailogView);
//        popupWindow.showAsDropDown(v, -5, 0);
//
//    }
//    public interface onOptionSelected {
//        void onItemPassed(int position, JSONObject value);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createListAndAdapterSet(String s, String proid){

        Logger.printMessage("message",s);
        Log.d("procontact servic",proid);
//        ob.setCity(s);
//        ob.setId(pros_contact_service);
//        stringArrayList.add(ob);
        Logger.printMessage("list for fasttime", String.valueOf(previousvalue.size()));

        if (serviceAreaAdapter==null) {
            save=new SavePojo();
            save.setId(pros_contact_service);
            save.setCity(s);
            previousvalue.add(save);
            arrycity= String.valueOf(previousvalue);
            Logger.printMessage("list for fasttime", String.valueOf(previousvalue.size()));
            serviceAreaAdapter = new ServiceAreaAdapter(AddServiceAreaActivity.this,previousvalue);
            rcv_service_area.setAdapter(serviceAreaAdapter);


        }

        else {
            save=new SavePojo();
            save.setId(pros_contact_service);
            save.setCity(s);
            previousvalue.contains(save);
            arrycity= String.valueOf(previousvalue);
            Logger.printMessage("contain", String.valueOf(previousvalue.contains(save)));
            Logger.printMessage("SIZE", String.valueOf(previousvalue.size()));

            Logger.printMessage("pros_contact_service",pros_contact_service);
            Logger.printMessage("city",s);
            for (int i=0;i<previousvalue.size();i++)
            // for (SavePojo savepojo : previousvalue)

            {


                if (previousvalue.get(i).getId().equals(save.getId()))
                {
                    edt_cities.setText("");
                    if (count==0)
                    {
                        //Toast.makeText(this, "Please Select another city", Toast.LENGTH_SHORT).show();
                        count++;
                    }


                    break;
                }
                else
                {
                    if (i==previousvalue.size()-1) {


                        Log.d("jhgdgfsg", proid);
                        Log.d("dkdjfkdj", s);
//                        save=new SavePojo();
//                        save.setId(proid);
//                        save.setCity(s);
                        previousvalue.add(save);
//                        serviceAreaAdapter = new ServiceAreaAdapter(AddServiceAreaActivity.this,previousvalue );
//                        rcv_service_area.setAdapter(serviceAreaAdapter);
                        serviceAreaAdapter.notifyDataSetChanged();
                        flag = false;
                        Logger.printMessage("List size second time", String.valueOf(previousvalue.size()));
                    }
                }

            }
        }
    }
    public  void savedata()
    {
        HashMap<String,String> Params=new HashMap<>();
        Params.put("user_id",ProApplication.getInstance().getUserId());

        Object cityarray[]=new Object[previousvalue.size()];
        for (int i = 0; i < previousvalue.size(); i++) {
            cityarray[i] = save.getCity();
            Params.put("citi[ ]", save.getCity());
        }

        Logger.printMessage("cityarray", String.valueOf(cityarray));
        new CustomJSONParser().fireAPIForPostMethod(AddServiceAreaActivity.this, ProConstant.servicesave, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("result",result);
                try {
                    JSONObject jo = new JSONObject(result);
                    String message= jo .getString("message");
                    Toast.makeText(AddServiceAreaActivity.this, ""+message, Toast.LENGTH_SHORT).show();
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
                customAlert.getEventFromNormalAlert(AddServiceAreaActivity.this, "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {

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

}

