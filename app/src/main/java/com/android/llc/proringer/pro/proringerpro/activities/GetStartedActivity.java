package com.android.llc.proringer.pro.proringerpro.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.GetStartedTutorial;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.HelperClass;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by bodhidipta on 10/06/17.
 * <!-- * Copyright (c) 2017, Proringer-->
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -->
 */

public class GetStartedActivity extends AppCompatActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ViewPager get_started_pager;
    private GetStartedTutorial adapter;
    private ImageView pager_dot_one, pager_dot_two, pager_dot_three, pager_dot_four, slide_left, slide_right;
    private ProRegularTextView get_started, sign_in;
    public static final int LOG_IN_REQUEST = 1;
    //RelativeLayout RLBottom;
    public static final int SIGN_UP_REQUEST = 2;
    ImageView img_background;
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    String updatelocation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_started);

        Logger.printMessage(TAG, "onCreate ...............................");
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


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        img_background = (ImageView) findViewById(R.id.img_background);

        Glide.with(GetStartedActivity.this).load(R.drawable.welcome_intro_get_started).into(img_background);

        //RLBottom= (RelativeLayout) findViewById(R.id.RLBottom);

        //RLBottom.getLayoutParams().width = width;
        //RLBottom.getLayoutParams().height = height/2;

        pager_dot_one = (ImageView) findViewById(R.id.pager_dot_one);
        pager_dot_two = (ImageView) findViewById(R.id.pager_dot_two);
        pager_dot_three = (ImageView) findViewById(R.id.pager_dot_three);
        pager_dot_four = (ImageView) findViewById(R.id.pager_dot_four);


        get_started_pager = (ViewPager) findViewById(R.id.get_started_pager);
        //get_started = (ProRegularTextView) findViewById(R.id.get_started);
        sign_in = (ProRegularTextView) findViewById(R.id.sign_in);

//        get_started.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(GetStartedActivity.this, SignUpActivity.class), SIGN_UP_REQUEST);
//            }
//        });
//

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(GetStartedActivity.this, LogInActivity.class), LOG_IN_REQUEST);


            }
        });


        adapter = new GetStartedTutorial(getSupportFragmentManager());
        get_started_pager.setAdapter(adapter);

        get_started_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                manageDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void manageDots(int position) {
        switch (position) {
            case 0:
                pager_dot_one.setBackgroundResource(R.drawable.circle_orenge_border);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
            case 1:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_orenge_border);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
            case 2:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_orenge_border);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
            case 3:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_orenge_border);
                break;
            case 4:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
        }
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
                customAlert.getOkEventFromNormalAlert(GetStartedActivity.this, getResources().getString(R.string.text_location_permission), getResources().getString(R.string.text_location_permission), new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        ActivityCompat.requestPermissions(GetStartedActivity.this,
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
                            Logger.printMessage(TAG, "LocationActivity update started ..............: ");
                            Logger.printMessage(TAG, "LocationActivity update resumed .....................");
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
    public void onLocationChanged(Location location) {
        Logger.printMessage(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();

    }

    @Override
    public void onConnected(Bundle bundle) {
        Logger.printMessage(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                ///////////////Here called location /////////////////

                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                Logger.printMessage(TAG, "LocationActivity update started ..............: ");
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
        Logger.printMessage(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            ProConstant.latitude = lat;
            ProConstant.longtitude = lng;
            Logger.printMessage("LATTITUIDE", lat);
            Logger.printMessage("Longtitude", lng);

            HelperClass.getInstance(GetStartedActivity.this).setCurrentLatLng(lat, lng);

            Logger.printMessage("updateUI", "At Time: " + mLastUpdateTime + "\n" +
                    "Latitude: " + lat + "\n" +
                    "Longitude: " + lng + "\n" +
                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                    "Provider: " + mCurrentLocation.getProvider());
        } else {
            Logger.printMessage(TAG, "location is null ...............");
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
                    Logger.printMessage(TAG, "LocationActivity update resumed .....................");
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
                    Logger.printMessage(TAG, "LocationActivity update stopped .......................");
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
}



