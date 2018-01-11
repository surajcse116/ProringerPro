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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.CustomMapView;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.ProHelperClass;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.helper.ViewHelper;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import java.util.HashMap;

/**
 * Created by su on 8/18/17.
 */

public class ProjectDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
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
    MyLoader myLoader;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myload = new MyLoader(ProjectDetailsActivity.this);

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
        progressBar= (ProgressBar) findViewById(R.id.progressBar);

        myLoader = new MyLoader(ProjectDetailsActivity.this);

        mapview.onCreate(Bundle.EMPTY);
        mapview.getMapAsync(this);
        MapsInitializer.initialize(this);


        RLImage.getLayoutParams().height = (int) 3 * (screenWidth - 20) / 4;
        RLImage.getLayoutParams().width = screenWidth - 20;

        mapview.getLayoutParams().height = (int) 2 * (screenWidth - 10) / 5;

        showData();

        img_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (img_likes.getTag().equals("0")){
                    CustomAlert customAlert = new CustomAlert();
                    customAlert.getEventFromNormalAlert(ProjectDetailsActivity.this, "", "Are you sure you want to add to watchlist?", "ok", "cancel", new CustomAlert.MyCustomAlertListener() {
                        @Override
                        public void callBackOk() {
                            addOrRemoveWatchlist(project_id,"1");
                        }

                        @Override
                        public void callBackCancel() {

                        }
                    });
                }else {
                    CustomAlert customAlert = new CustomAlert();
                    customAlert.getEventFromNormalAlert(ProjectDetailsActivity.this, "", "Are you sure you want to remove from watchlist?", "ok", "cancel", new CustomAlert.MyCustomAlertListener() {
                        @Override
                        public void callBackOk() {
                            addOrRemoveWatchlist(project_id,"0");
                        }

                        @Override
                        public void callBackCancel() {

                        }
                    });
                }
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

        new CustomJSONParser().fireAPIForGetMethod(ProjectDetailsActivity.this, ProConstant.app_pro_myproject_details, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("Result", result);

                try {
                    JSONArray info_array = new JSONObject(result).getJSONArray("info_array");
                    JSONObject jsonObject = info_array.getJSONObject(0);

                    String splitPostedBy[]=jsonObject.getString("posted_by").split(" ");
                    String name="";
                    for (int i=0;i<splitPostedBy.length-1;i++){
                        name=splitPostedBy[i].substring(0,1)+".";
                    }
                    ((ProRegularTextView) findViewById(R.id.tv_posted_by_value)).setText(name+splitPostedBy[splitPostedBy.length-1]);
//                    ((ProRegularTextView) findViewById(R.id.tv_posted_by_value)).setText(jsonObject.getString("posted_by"));
                    ((ProRegularTextView) findViewById(R.id.tv_posted_date)).setText(jsonObject.getString("post_date"));
                    ((ProRegularTextView) findViewById(R.id.tv_address)).setText(jsonObject.getString("address"));

                    progressBar.setVisibility(View.VISIBLE);
                    Glide.with(ProjectDetailsActivity.this).load(jsonObject.getString("project_image")).listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(img_project);


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
                        img_likes.setImageResource(R.drawable.ic_unfavorite);
                        img_likes.setTag("0");
//                        Glide.with(ProjectDetailsActivity.this).load("").placeholder(R.drawable.ic_unfavorite).into(img_likes);
                    } else {
                        img_likes.setImageResource(R.drawable.ic_favorite);
                        img_likes.setTag("1");
//                        Glide.with(ProjectDetailsActivity.this).load("").placeholder(R.drawable.ic_favorite).into(img_likes);
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

    public void addOrRemoveWatchlist(final String project_id, final String project_function){
        HashMap<String, String> Params = new HashMap<>();

        Params.put("user_id", ProApplication.getInstance().getUserId());
        Params.put("project_id", project_id);
        Params.put("project_function", project_function);

        Logger.printMessage("user_id", ProApplication.getInstance().getUserId());
        Logger.printMessage("project_id", project_id);
        Logger.printMessage("project_function", project_function);

        Logger.printMessage("PARAMS", String.valueOf(Params));


        new CustomJSONParser().fireAPIForPostMethod(ProjectDetailsActivity.this, ProConstant.app_pro_watchlist_delete, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                try {
                    JSONObject mainResponseObj = new JSONObject(result);
                    Logger.printMessage("message", mainResponseObj.getString("message"));
                    Toast.makeText(ProjectDetailsActivity.this, mainResponseObj.getString("message"), Toast.LENGTH_SHORT).show();


                    if (project_function.equals("1")) {
                        img_likes.setImageResource(R.drawable.ic_favorite);
                        img_likes.setTag("1");
                    }else {

                        img_likes.setImageResource(R.drawable.ic_unfavorite);
                        img_likes.setTag("0");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
                new MYAlert(ProjectDetailsActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                    @Override
                    public void OnOk(boolean res) {

                    }
                });
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                new MYAlert(ProjectDetailsActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
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
        ProHelperClass.getInstance(ProjectDetailsActivity.this).getlatlongAPI(new ProHelperClass.getApiProcessCallback() {
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
            if ((ContextCompat.checkSelfPermission(ProjectDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(ProjectDetailsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
            }
        }else {
            mMap.setMyLocationEnabled(true);
        }

        // mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Set a preference for minimum and maximum zoom.
        mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(12.0f);

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
        return (ContextCompat.checkSelfPermission(ProjectDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED );
    }
}
