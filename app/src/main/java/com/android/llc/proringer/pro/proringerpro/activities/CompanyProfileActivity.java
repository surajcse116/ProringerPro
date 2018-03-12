package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialog;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by su on 8/17/17.
 */

public class CompanyProfileActivity extends AppCompatActivity {

    ProLightEditText et_name, et_email, et_companywesite, et_companyphone, et_employee, et_address, et_zip, et_city, et_state;
    String pros_contact_service;
    ProRegularTextView et_busineestype, tv_save;
    ScrollView ScrollViewMAin;
    MyLoader myLoader;
    ArrayList<SetGetAPIPostData> arrayList = null;
    CustomListAdapterDialog customListAdapterDialog = null;
    PopupWindow popupWindow;
    JSONArray btype;
    int height, width;
    String countryCod = "", latitude, longtitude = "", business_type_id = "";
    String businessdata, accname;
    int textLength = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compnay_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ScrollViewMAin = (ScrollView) findViewById(R.id.ScrollViewMAin);
        et_name = (ProLightEditText) findViewById(R.id.et_name);
        et_email = (ProLightEditText) findViewById(R.id.et_email);
        et_companywesite = (ProLightEditText) findViewById(R.id.et_companywesite);
        et_companyphone = (ProLightEditText) findViewById(R.id.et_companyphone);
        et_busineestype = (ProRegularTextView) findViewById(R.id.et_busineestype);
        et_employee = (ProLightEditText) findViewById(R.id.et_employee);
        et_address = (ProLightEditText) findViewById(R.id.et_address);
        et_zip = (ProLightEditText) findViewById(R.id.et_zip);
        et_city = (ProLightEditText) findViewById(R.id.et_city);
        et_state = (ProLightEditText) findViewById(R.id.et_state);
        tv_save = (ProRegularTextView) findViewById(R.id.tv_save);
        et_address.setFocusable(false);
        et_address.setClickable(false);

        et_zip.setFocusable(false);
        et_zip.setClickable(false);

        et_city.setFocusable(false);
        et_city.setClickable(false);

        et_state.setFocusable(false);
        et_state.setClickable(false);

        myLoader = new MyLoader(CompanyProfileActivity.this);

        et_companyphone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = et_companyphone.getText().toString();
                textLength = et_companyphone.getText().length();

                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;

                if (textLength == 1) {
                    if (!text.contains("(")) {
                        et_companyphone.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                        et_companyphone.setSelection(et_companyphone.getText().length());
                    }

                } else if (textLength == 5) {

                    if (!text.contains(")")) {
                        et_companyphone.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        et_companyphone.setSelection(et_companyphone.getText().length());
                    }

                } else if (textLength == 6) {
                    et_companyphone.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    et_companyphone.setSelection(et_companyphone.getText().length());

                } else if (textLength == 10) {
                    if (!text.contains("-")) {
                        et_companyphone.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        et_companyphone.setSelection(et_companyphone.getText().length());
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyProfileActivity.this, LocationActivity.class);
                startActivityForResult(i, 1);
            }
        });
        findViewById(R.id.et_busineestype).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogServies(view, btype);
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        dropDownData();

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
                    Logger.printMessage("lattitude", "--->" + extras.getString("lattitude"));
                    Logger.printMessage("longttitude", "--->" + extras.getString("longttitude"));
                    if (!extras.getString("selectedPlace").equals("")) {
                        et_address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }
                    et_zip.setText(extras.getString("zip"));
                    et_city.setText(extras.getString("city"));
                    et_state.setText(extras.getString("state"));

                    latitude = extras.getString("lattitude");
                    longtitude = extras.getString("longttitude");
                    getAddress(Double.valueOf(latitude), Double.valueOf(longtitude));

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

    public void getData() {
        arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPI = new SetGetAPIPostData();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);
        new CustomJSONParser().fireAPIForGetMethod(CompanyProfileActivity.this, ProConstant.companyinformation, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                try {
                    JSONObject mainResponseObj = new JSONObject(result);
                    JSONArray job = mainResponseObj.getJSONArray("info_array");
                    for (int i = 0; i < job.length(); i++) {
                        JSONObject jo = job.getJSONObject(i);

                        businessdata = jo.getString("business_desc");
                        accname = jo.getString("acc_name");
                        et_name.setText(jo.getString("company_name"));
                        et_email.setText(jo.getString("contact_email"));

                        et_companywesite.setText(jo.getString("company_website"));
                        et_companyphone.setText(jo.getString("company_phone"));
                        et_address.setText(jo.getString("str_address"));

                        et_zip.setText(jo.getString("zipcode"));
                        et_city.setText(jo.getString("city"));
                        et_state.setText(jo.getString("state"));

                        et_employee.setText(jo.getString("#employees"));
                        et_busineestype.setText(jo.getJSONObject("business_type").getString("value"));
                        business_type_id = jo.getJSONObject("business_type").getString("id");

                        countryCod = jo.getString("country_name");
                        latitude = jo.getString("latitude");
                        longtitude = jo.getString("longitude");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myLoader.dismissLoader();
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
                Toast.makeText(CompanyProfileActivity.this, "" + response, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(CompanyProfileActivity.this, "Load Error", "" + error, "retry", "abort", new CustomAlert.MyCustomAlertListener() {
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
            }
        });

    }


    public interface onOptionSelected {
        void onItemPassed(int position, JSONObject value);
    }

    public void showDialogServies(View v, JSONArray PredictionsJsonArray) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels / 2;
        width = displayMetrics.widthPixels / 2;
        popupWindow = new PopupWindow(CompanyProfileActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View dailogView = getLayoutInflater().inflate(R.layout.dialog_show_place, null);
        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(CompanyProfileActivity.this));

        customListAdapterDialog = new CustomListAdapterDialog(CompanyProfileActivity.this, PredictionsJsonArray, new onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    et_busineestype.setText(value.getString("value"));
                    pros_contact_service = value.getString("id");
                    business_type_id = pros_contact_service;
                    Logger.printMessage("value id", pros_contact_service);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rcv_.setAdapter(customListAdapterDialog);
        // some other visual settings
        popupWindow.setFocusable(false);
        popupWindow.setWidth(width - 30);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(dailogView);
        popupWindow.showAsDropDown(v, 0, 0);

    }

    public void dropDownData() {
        new CustomJSONParser().fireAPIForGetMethod(CompanyProfileActivity.this, ProConstant.companybusinessoptionapi, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                //Logger.printMessage("business",result);
                try {
                    JSONObject job = new JSONObject(result);
                    btype = job.getJSONArray("business");
                    Logger.printMessage("array", String.valueOf(btype));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getData();
            }

            @Override
            public void onError(String error, String response) {
                Toast.makeText(CompanyProfileActivity.this, "No data found" + response, Toast.LENGTH_SHORT).show();
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


    public void getAddress(Double lat, Double lng) {
        Geocoder geocoder = new Geocoder(CompanyProfileActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address obj = addresses.get(0);
        String add = obj.getAddressLine(0);
        String currentAddress = obj.getSubAdminArea() + ","
                + obj.getAdminArea();
        String latitude = String.valueOf(obj.getLatitude());
        String longitude = String.valueOf(obj.getLongitude());
        String currentCity = obj.getSubAdminArea();
        String currentState = obj.getAdminArea();
        add = add + "\n" + obj.getCountryName();
        add = add + "\n" + obj.getCountryCode();
        countryCod = obj.getCountryCode();
        Logger.printMessage("countrycod", countryCod);
        add = add + "\n" + obj.getAdminArea();
        add = add + "\n" + obj.getPostalCode();
        add = add + "\n" + obj.getSubAdminArea();
        add = add + "\n" + obj.getLocality();
        add = add + "\n" + obj.getSubThoroughfare();

        Log.v("IGA", "Address" + add);
        // Toast.makeText(this, "Address=>" + add,
        // Toast.LENGTH_SHORT).show();

        // TennisAppActivity.showDialog(add);

    }


    public void submit() {
        if (et_name.getText().toString().trim().equals("")) {
            et_name.setError("Please enter company name");
            et_name.requestFocus();
        } else {
            if (et_email.getText().toString().trim().equals("")) {
                et_email.setError("Please enter email");
                et_email.requestFocus();
            } else {
                et_email.setError(null);
                et_email.clearFocus();
                if (MethodsUtils.isValidEmail(et_email.getText().toString().trim())) {

                    et_email.setError(null);
                    et_email.clearFocus();

                    if (et_companywesite.getText().toString().trim().equals("")) {
                        et_companywesite.setError("Please enter company  website");
                        et_companywesite.requestFocus();
                    } else {
                        et_companywesite.setError(null);
                        et_companywesite.clearFocus();

                        if (MethodsUtils.isValidUrl(et_companywesite.getText().toString().trim())) {

                            et_companywesite.setError(null);
                            et_companywesite.clearFocus();

                            if (et_companyphone.getText().toString().trim().equals("")) {
                                et_companyphone.setError("Enter company phone number");
                                et_companyphone.requestFocus();
                            } else {
                                et_companyphone.setError(null);
                                et_companyphone.clearFocus();

                                if (et_companyphone.getText().toString().length() < 14) {
                                    et_companyphone.setError("Enter enter correct phone number");
                                    et_companyphone.requestFocus();
                                } else {
                                    et_companyphone.setError(null);
                                    et_companyphone.clearFocus();

                                    if (et_busineestype.getText().toString().equalsIgnoreCase("")) {
                                        Toast.makeText(CompanyProfileActivity.this, "please select type of business", Toast.LENGTH_SHORT).show();
                                        et_employee.requestFocus();
                                    } else {

                                        if (et_employee.getText().toString().trim().equals("")) {
                                            et_employee.setError("Enter employee value");
                                            et_employee.requestFocus();
                                        } else {
                                            et_employee.setError(null);
                                            et_employee.clearFocus();
                                            if (et_address.getText().toString().trim().equals("")) {
                                                et_address.setError("Select your street address");
                                                et_address.requestFocus();
                                            } else {
                                                et_address.setError(null);
                                                et_address.clearFocus();
                                                if (et_zip.getText().toString().trim().equals("")) {
                                                    et_zip.setError("Select your zipcode");
                                                    et_zip.requestFocus();
                                                } else {
                                                    et_zip.setError(null);
                                                    et_zip.clearFocus();
                                                    if (et_city.getText().toString().trim().equals("")) {
                                                        et_city.setError("Select your city");
                                                        et_city.requestFocus();
                                                    } else {
                                                        et_city.setError(null);
                                                        et_city.clearFocus();
                                                        if (et_state.getText().toString().trim().equals("")) {
                                                            et_state.setError("select your state");
                                                            et_state.requestFocus();
                                                        } else {
                                                            et_state.setError(null);
                                                            et_state.clearFocus();
//                                                    final String locale = CompanyProfileActivity.this.getResources().getConfiguration().locale.getCountry();
//                                                    ProConstant.country1 = locale;
//                                                    Log.d("sbdsdsh",locale);


                                                            Logger.printMessage("userid", ProApplication.getInstance().getUserId());
                                                            Logger.printMessage("b_desc", businessdata);
                                                            Logger.printMessage("comp_name", et_name.getText().toString().trim());
                                                            Logger.printMessage("comp_email", et_email.getText().toString().trim());
                                                            Logger.printMessage("website", et_companywesite.getText().toString().trim());
                                                            Logger.printMessage("alt_phone", et_companyphone.getText().toString().trim());
                                                            Logger.printMessage("bus_type", business_type_id);
                                                            Logger.printMessage("num_emp", et_employee.getText().toString().trim());
                                                            Logger.printMessage("com_address", et_address.getText().toString().trim());
                                                            Logger.printMessage("city", et_city.getText().toString().trim());
                                                            Logger.printMessage("state", et_state.getText().toString().trim());
                                                            Logger.printMessage("zipcode", et_zip.getText().toString().trim());
                                                            Logger.printMessage("acc_name", accname);

                                                            Logger.printMessage("lat", latitude);
                                                            Logger.printMessage("long", longtitude);
                                                            Logger.printMessage("country", countryCod);


                                                            CustomAlert customAlert = new CustomAlert();
                                                            customAlert.getEventFromNormalAlert(CompanyProfileActivity.this, "", "Confirm company profile update", "Ok", "Cancel", new CustomAlert.MyCustomAlertListener() {
                                                                @Override
                                                                public void callBackOk() {

                                                                    HashMap<String, String> Params = new HashMap<>();
                                                                    Params.put("user_id", ProApplication.getInstance().getUserId());
                                                                    Params.put("desc", businessdata);
                                                                    Params.put("comp_name", et_name.getText().toString().trim());
                                                                    Params.put("comp_email", et_email.getText().toString().trim());
                                                                    Params.put("website", et_companywesite.getText().toString().trim());
                                                                    Params.put("alt_phone", et_companyphone.getText().toString().trim());
                                                                    Params.put("bus_type", business_type_id);
                                                                    Params.put("num_emp", et_employee.getText().toString().trim());
                                                                    Params.put("com_address", et_address.getText().toString().trim());
                                                                    Params.put("city", et_city.getText().toString().trim());
                                                                    Params.put("state", et_state.getText().toString().trim());
                                                                    Params.put("zipcode", et_zip.getText().toString().trim());
                                                                    Params.put("acc_name", accname);
                                                                    Params.put("latitude", latitude);
                                                                    Params.put("longitude", longtitude);
                                                                    Params.put("country", countryCod);

                                                                    Logger.printMessage("PARAMS", String.valueOf(Params));

                                                                    new CustomJSONParser().fireAPIForPostMethod(CompanyProfileActivity.this, ProConstant.copanyinfosave, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                                                        @Override
                                                                        public void onSuccess(String result) {
                                                                            myLoader.dismissLoader();
                                                                            Logger.printMessage("result", result);
                                                                            try {
                                                                                JSONObject job = new JSONObject(result);
                                                                                String msg = job.getString("message");
                                                                                getData();
                                                                                Toast.makeText(CompanyProfileActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onError(String error, String response) {
                                                                            myLoader.dismissLoader();
                                                                            try {
                                                                                JSONObject job = new JSONObject(response);
                                                                                String message = job.getString("message");
                                                                                Toast.makeText(CompanyProfileActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
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
                                                                public void callBackCancel() {
                                                                }
                                                            });
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        } else {
                            et_companywesite.setError("Please enter correct website");
                            et_companywesite.requestFocus();
                        }
                    }
                } else {
                    et_email.setError("Please enter valid email");
                    et_email.requestFocus();
                }
            }
        }
    }
}
