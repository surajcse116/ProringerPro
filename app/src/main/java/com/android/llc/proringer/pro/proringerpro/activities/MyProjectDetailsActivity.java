package com.android.llc.proringer.pro.proringerpro.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.CustomMapView;
import com.android.llc.proringer.pro.proringerpro.helper.ProHelperClass;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.helper.ViewHelper;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 8/18/17.
 */

public class MyProjectDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    int screenHeight;
    int screenWidth;
    String project_id = "", zip = "";
    ImageView img_project, img_likes;
    CustomMapView mapview;
    RelativeLayout RLImage;
    GoogleMap mMap;
    ArrayList<SetGetAPI> arrayList = null;
    MyLoader myload;
    double slat,slong,northlat,northlong;
    int REQ_PERMISSION=1000;
    ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myload = new MyLoader(MyProjectDetailsActivity.this);

        project_id = getIntent().getStringExtra("project_id");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        RLImage = (RelativeLayout) findViewById(R.id.RLImage);
        img_project = (ImageView) findViewById(R.id.img_project);
        img_likes = (ImageView) findViewById(R.id.img_likes);
        mapview = (CustomMapView) findViewById(R.id.mapview);

        mapview.onCreate(Bundle.EMPTY);
        mapview.getMapAsync(this);
        MapsInitializer.initialize(this);


        RLImage.getLayoutParams().height = (int) 3 * (screenWidth - 20) / 4;
        RLImage.getLayoutParams().width = screenWidth - 20;

        mapview.getLayoutParams().height = (int) 2 * (screenWidth - 10) / 5;

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
        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);

        setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("project_id");
        setGetAPI.setValues(project_id);
        arrayList.add(setGetAPI);

        new CustomJSONParser().fireAPIForGetMethod(MyProjectDetailsActivity.this, ProConstant.app_pro_myproject_details, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("Result", result);

                try {
                    JSONArray info_array = new JSONObject(result).getJSONArray("info_array");
                    JSONObject jsonObject = info_array.getJSONObject(0);

                    ((ProRegularTextView) findViewById(R.id.tv_posted_by_value)).setText(jsonObject.getString("posted_by"));
                    ((ProRegularTextView) findViewById(R.id.tv_posted_date)).setText(jsonObject.getString("post_date"));
                    ((ProRegularTextView) findViewById(R.id.tv_address)).setText(jsonObject.getString("address"));

                    Glide.with(MyProjectDetailsActivity.this).load(jsonObject.getString("project_image")).into(img_project);
                    ((ProRegularTextView) findViewById(R.id.tv_project_title)).setText(jsonObject.getString("project_name"));
                    ((ProRegularTextView) findViewById(R.id.tv_project_title)).setText(jsonObject.getString("project_name"));
                    ((ProRegularTextView) findViewById(R.id.tv_type_of_work)).setText(jsonObject.getString("type_of_work"));
                    ((ProRegularTextView) findViewById(R.id.tv_service)).setText(jsonObject.getString("service"));
                    ((ProRegularTextView) findViewById(R.id.tv_property)).setText(jsonObject.getString("property"));
                    ((ProRegularTextView) findViewById(R.id.tv_phone)).setText(jsonObject.getString("phone"));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ((ProRegularTextView) findViewById(R.id.tv_describetion)).setText(Html.fromHtml(ViewHelper.SetParaAlign(jsonObject.getString("job_details"), ViewHelper.P_Justify), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        ((ProRegularTextView) findViewById(R.id.tv_describetion)).setText(Html.fromHtml(ViewHelper.SetParaAlign(jsonObject.getString("job_details"), ViewHelper.P_Justify)));
                    }


                    zip = jsonObject.getString("zip");

                    if (jsonObject.getString("add_favouite").equalsIgnoreCase("0")) {
//                        img_likes.setImageResource(R.drawable.ic_unfavorite);
                        Glide.with(MyProjectDetailsActivity.this).load("").placeholder(R.drawable.ic_unfavorite).into(img_likes);
                    } else {
//                        img_likes.setImageResource(R.drawable.ic_favorite);
                        Glide.with(MyProjectDetailsActivity.this).load("").placeholder(R.drawable.ic_favorite).into(img_likes);
                    }
                    showlatlong();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();

//                CustomAlert customAlert = new CustomAlert();
//                customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
//                    @Override
//                    public void callBackOk() {
//
//                    }
//
//                    @Override
//                    public void callBackCancel() {
//
//                    }
//                });
            }

            @Override
            public void onError(String error) {
                myload.dismissLoader();
                if (error.equalsIgnoreCase("No internet connection found. Please check your internet connection.")) {
//                    LLNetworkDisconnection.setVisibility(View.VISIBLE);
//                    LL_Main.setVisibility(View.GONE);
                }
            }

            @Override
            public void onStart() {
                myload.showLoader();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }


    @Override
    protected void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapview.onLowMemory();
        super.onLowMemory();
    }

    public void showlatlong() {
        ProHelperClass.getInstance(MyProjectDetailsActivity.this).getlatlongAPI(new ProHelperClass.getApiProcessCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(String message) {
                Logger.printMessage("valu", message);
                try {
                    JSONObject jo = new JSONObject(message);
                    Logger.printMessage("infoarry", String.valueOf(jo));
                    if (jo.getString("status").equalsIgnoreCase("OK") && jo.has("results") &&
                            jo.getJSONArray("results").length() > 0) {
                        JSONArray results = jo.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {

                            Logger.printMessage("totalvalue", String.valueOf(results.getJSONObject(i)));

                            Logger.printMessage("totalvalu", String.valueOf(results.getJSONObject(i).getJSONObject("geometry")));
                            JSONObject location = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
                            Logger.printMessage("loc", String.valueOf(location));
//                            locationlat = Double.parseDouble(location.getString("lat"));
//                            locationlong = Double.parseDouble(location.getString("lng"));
//                            Logger.printMessage("locationlat", String.valueOf(locationlat));
//                            Logger.printMessage("locationlong", String.valueOf(locationlong));
                            JSONObject view = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("viewport");
                            Logger.printMessage("view", String.valueOf(view));
                            JSONObject north = view.getJSONObject("northeast");
                            northlat = Double.parseDouble(north.getString("lat"));
                            northlong = Double.parseDouble(north.getString("lng"));

                            // mMap.addMarker(new MarkerOptions().position(new LatLng(locationlat,locationlong)).title("service area"));
                            Logger.printMessage("northlat", String.valueOf(northlat));
                            Logger.printMessage("northlong", String.valueOf(northlong));
                            JSONObject sw = view.getJSONObject("southwest");
                            slat = Double.parseDouble(sw.getString("lat"));
                            slong = Double.parseDouble(sw.getString("lng"));

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(northlat, northlong), 9));

                            Logger.printMessage("slat", String.valueOf(slat));
                            Logger.printMessage("slong", String.valueOf(slong));


                            PolygonOptions rectOptions = new PolygonOptions()
                                    .add(new LatLng(northlat, northlong))
                                    .add(new LatLng(slat, northlong))  // North of the previous point, but at the same longitude
                                    .add(new LatLng(slat, slong))  // Same latitude, and 30km to the west
                                    .add(new LatLng(northlat, slong))  // Same longitude, and 16km to the south
                                    .add(new LatLng(northlat, northlong)).strokeColor(Color.RED).fillColor(Color.rgb(226, 173, 173)).strokeWidth(5);
                            // Closes the polyline.

                            mMap.addPolygon(rectOptions);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {

            }
        }, zip);
    }

    public void setUpMap() {

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(MyProjectDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(MyProjectDetailsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
            }
        }else {
            mMap.setMyLocationEnabled(true);
        }

        // mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("Permission-->", "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    if (checkPermission())
                        mMap.setMyLocationEnabled(true);

                } else {
                    // Permission denied

                }
                break;
            }
        }
    }

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d("checkPermission-->", "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(MyProjectDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED );
    }
}
