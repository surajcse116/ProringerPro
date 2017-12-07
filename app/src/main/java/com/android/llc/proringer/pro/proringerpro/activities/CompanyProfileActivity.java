package com.android.llc.proringer.pro.proringerpro.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
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
import com.android.llc.proringer.pro.proringerpro.helper.HelperClass;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by su on 8/17/17.
 */

public class CompanyProfileActivity extends AppCompatActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    ProLightEditText et_name, et_email, et_companywesite, et_companyphone, et_employee, et_address, et_zip, et_city, et_state;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String pros_contact_service;
    ProRegularTextView et_busineestype, tv_save;
    ScrollView ScrollViewMAin;
    MyLoader myLoader;
    ArrayList<SetGetAPI> arrayList = null;
    CustomListAdapterDialog customListAdapterDialog = null;
    PopupWindow popupWindow;
    JSONArray btype;
    int height, width;
    String businessdata, accname;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    android.location.Location mCurrentLocation;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    private static final String TAG = "LocationActivity";
    String mLastUpdateTime;

    JSONObject infoArrayJsonObject = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compnay_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


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
        data();
        dropdwondata();


        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyProfileActivity.this, LocationActivity.class);
                startActivityForResult(i, 1);
            }
        });
        et_busineestype.setOnClickListener(new View.OnClickListener() {
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

                    if (!extras.getString("selectedPlace").equals("")) {
                        et_address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }
                    et_zip.setText(extras.getString("zip"));
                    et_city.setText(extras.getString("city"));
                    et_state.setText(extras.getString("state"));

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

    public void data() {
        myLoader.showLoader();
        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);
        new CustomJSONParser().fireAPIForGetMethod(CompanyProfileActivity.this, ProConstant.companyinformation, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                myLoader.dismissLoader();
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
                        JSONObject joi = jo.getJSONObject("business_type");
                        et_busineestype.setText(joi.getString("value"));
                        ProConstant.id = joi.getString("id");
                        Logger.printMessage("AppDataId", ProConstant.id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                    ProConstant.id = pros_contact_service;
                    Logger.printMessage("value id", pros_contact_service);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rcv_.setAdapter(customListAdapterDialog);
        // some other visual settings
        popupWindow.setFocusable(false);
        popupWindow.setWidth(width);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(dailogView);
        popupWindow.showAsDropDown(v, -5, 0);

    }

    public void dropdwondata() {
        new CustomJSONParser().fireAPIForGetMethod(CompanyProfileActivity.this, ProConstant.companybusinessoptionapi, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                //Log.d("business",result);
                try {
                    JSONObject job = new JSONObject(result);
                    btype = job.getJSONArray("business");
                    Logger.printMessage("array", String.valueOf(btype));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                Toast.makeText(CompanyProfileActivity.this, "No data found" + response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {

            }
            @Override
            public void onStart() {

            }
        });

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(CompanyProfileActivity.this, getResources().getString(R.string.text_location_permission), "" + getResources().getString(R.string.text_location_permission), "Ok", "Cancel", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        ActivityCompat.requestPermissions(CompanyProfileActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }

                    @Override
                    public void callBackCancel() {

                    }
                });

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        ///////////////Here called location /////////////////

                        mGoogleApiClient.connect();

                        if (mGoogleApiClient.isConnected()) {
                            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                            com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "LocationActivity update started ..............: ");
                            com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "LocationActivity update resumed .....................");
                        }
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();

    }

    @Override
    public void onConnected(Bundle bundle) {
        com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                ///////////////Here called location /////////////////

                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "LocationActivity update started ..............: ");
            }
        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Logger.printMessage(TAG, "Connection failed: " + connectionResult.toString());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }

            return false;
        }
        return true;
    }

    private void updateUI() {
        com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            String lattitude = String.valueOf(mCurrentLocation.getLatitude());
            String longttitude = String.valueOf(mCurrentLocation.getLongitude());
            ProConstant.latitude = lattitude;
            ProConstant.longtitude = longttitude;
            Logger.printMessage("LATTITUIDE", lattitude);
            Logger.printMessage("Longtitude", longttitude);

            HelperClass.getInstance(CompanyProfileActivity.this).setCurrentLatLng(lattitude, longttitude);

            com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage("updateUI", "At Time: " + mLastUpdateTime + "\n" +
                    "Latitude: " + lattitude + "\n" +
                    "Longitude: " + longttitude + "\n" +
                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                    "Provider: " + mCurrentLocation.getProvider());
        } else {
            com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "location is null ...............");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                ///////////////Here called location /////////////////
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                ///////////////Here called location /////////////////
                if (mGoogleApiClient.isConnected()) {
                    PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "LocationActivity update resumed .....................");
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                ///////////////Here called location /////////////////
                if (mGoogleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                    com.android.llc.proringer.pro.proringerpro.helper.Logger.printMessage(TAG, "LocationActivity update stopped .......................");
                }
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        Logger.printMessage(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Logger.printMessage(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    public void submit() {
        final String cname = et_name.getText().toString().trim();
        final String cmail = et_email.getText().toString().trim();
        final String cwebsite = et_companywesite.getText().toString().trim();
        final String cphone = et_companyphone.getText().toString().trim();
        final String employe = et_employee.getText().toString().trim();
        final String address = et_address.getText().toString().trim();
        final String zip = et_zip.getText().toString().trim();
        final String city = et_city.getText().toString().trim();
        final String state = et_state.getText().toString().trim();
        if (cname.equals("")) {
            et_name.setError("Please enter company name");
            et_name.setFocusable(true);
        } else {
            if (cmail.equals("")) {
                et_email.setError("Please enter correct email");
                et_email.setFocusable(true);

            } else {
                if (cwebsite.equals("")) {
                    et_companywesite.setError("Please enter company  website");
                    et_companywesite.setFocusable(true);
                } else {
                    if (cphone.equals("")) {
                        et_companyphone.setError("Enter company phone number");
                        et_companyphone.setFocusable(true);
                    } else {
                        if (employe.equals("")) {
                            et_employee.setError("Enter employee value");
                            et_employee.setFocusable(true);
                        } else {
                            if (address.equals("")) {
                                et_address.setError("Select your street address");
                                et_address.setFocusable(true);
                            } else {
                                if (zip.equals("")) {
                                    et_zip.setError("Select your zipcode");
                                    et_zip.setFocusable(true);
                                } else {
                                    if (city.equals("")) {
                                        et_city.setError("Select your city");
                                        et_city.setFocusable(true);
                                    } else {
                                        if (state.equals("")) {
                                            et_state.setError("select your state");
                                            et_state.setFocusable(true);
                                        } else {
                                            final String locale = CompanyProfileActivity.this.getResources().getConfiguration().locale.getCountry();
                                            Logger.printMessage("COUNTRY CODE", locale);
                                            ProConstant.country1 = locale;
                                            Logger.printMessage("userid", ProApplication.getInstance().getUserId());
                                            Logger.printMessage("b_desc", businessdata);
                                            Logger.printMessage("comp_name", cname);
                                            Logger.printMessage("comp_email", cmail);
                                            Logger.printMessage("website", cwebsite);
                                            Logger.printMessage("alt_phone", cphone);
                                            Logger.printMessage("bus_type", ProConstant.id);
                                            Logger.printMessage("num_emp", employe);
                                            Logger.printMessage("com_address", address);
                                            Logger.printMessage("city", city);
                                            Logger.printMessage("country", locale);
                                            Logger.printMessage("state", state);
                                            Logger.printMessage("zipcode", zip);
                                            Logger.printMessage("acc_name", accname);
                                            Logger.printMessage("lat", ProConstant.latitude);
                                            Logger.printMessage("long", ProConstant.longtitude);
                                            LayoutInflater factory = LayoutInflater.from(CompanyProfileActivity.this);
                                            final View deleteDialogView = factory.inflate(R.layout.alertdialog, null);
                                            final AlertDialog deleteDialog = new AlertDialog.Builder(CompanyProfileActivity.this).create();
                                            deleteDialog.setView(deleteDialogView);
                                            deleteDialogView.findViewById(R.id.Yes).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    HashMap<String, String> Params = new HashMap<>();
                                                    Params.put("user_id", ProApplication.getInstance().getUserId());
                                                    Params.put("desc", businessdata);
                                                    Params.put("comp_name", cname);
                                                    Params.put("comp_email", cmail);
                                                    Params.put("website", cwebsite);
                                                    Params.put("alt_phone", cphone);
                                                    Params.put("bus_type", ProConstant.id);
                                                    Params.put("num_emp", employe);
                                                    Params.put("com_address", address);
                                                    Params.put("city", city);
                                                    Params.put("country", locale);
                                                    Params.put("state", state);
                                                    Params.put("zipcode", zip);
                                                    Params.put("acc_name", accname);
                                                    Params.put("latitude", ProConstant.latitude);
                                                    Params.put("longitude", ProConstant.longtitude);
                                                    Logger.printMessage("PARAMS", String.valueOf(Params));

                                                    new CustomJSONParser().fireAPIForPostMethod(CompanyProfileActivity.this, ProConstant.copanyinfosave, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                                        @Override
                                                        public void onSuccess(String result) {
                                                            Logger.printMessage("result", result);
                                                            deleteDialog.dismiss();
                                                            try {
                                                                JSONObject job = new JSONObject(result);
                                                                String msg = job.getString("message");
                                                                data();

                                                                Toast.makeText(CompanyProfileActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }

                                                        @Override
                                                        public void onError(String error, String response) {
                                                            try {
                                                                deleteDialog.dismiss();
                                                                JSONObject job = new JSONObject(response);
                                                                String message = job.getString("message");
                                                                Toast.makeText(CompanyProfileActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }

                                                        @Override
                                                        public void onError(String error) {

                                                        }

                                                        @Override
                                                        public void onStart() {

                                                        }
                                                    });

                                                }
                                            });
                                            deleteDialogView.findViewById(R.id.NO).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    deleteDialog.dismiss();
                                                }
                                            });

                                            deleteDialog.show();

                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
