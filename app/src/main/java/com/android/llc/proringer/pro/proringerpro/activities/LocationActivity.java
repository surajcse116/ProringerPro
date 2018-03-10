package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.helper.ProHelperClass;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    String selectedPlace = "",city = "", state = "", zip_code = "";
    String lattitude,longttitude;
    EditText locationText;
    ImageView Erase;
    ListView listviewLocation;
    MyLoader myLoader = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        locationText=(EditText)findViewById(R.id.locationed) ;
        Erase=(ImageView)findViewById(R.id.Erase);
        listviewLocation=(ListView)findViewById(R.id.suggestion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(""+getResources().getString(R.string.selectAddress));
        myLoader=new MyLoader(LocationActivity.this);


        listviewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPlace = (String) parent.getItemAtPosition(position);
                Logger.printMessage("selectedPlace",selectedPlace);
                getZipCityState();
                locationText.setText(selectedPlace);
                listviewLocation.setVisibility(View.GONE);
            }
        });
        locationText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fetch_LocationSuggestion(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(locationText.getText().length()>0)
                {
                    Erase.setVisibility(View.VISIBLE);
                }else {
                    Erase.setVisibility(View.GONE);
                }
            }
        });
        Erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationText.setText("");
                fetch_LocationSuggestion("");
                Erase.setVisibility(View.GONE);
                listviewLocation.setVisibility(View.GONE);
            }
        });
    }
    private void fetch_LocationSuggestion(final CharSequence url) {

        ProHelperClass.getInstance(LocationActivity.this).getSearchCountriesByPlacesFilter(new ProHelperClass.onSearchPlacesNameCallback() {

            @Override
            public void onComplete(ArrayList<String> listData) {
                if(listData.size()>0) {
                    ArrayAdapter adapter = new ArrayAdapter(LocationActivity.this, R.layout.location_list_adapter, android.R.id.text1, listData);
                    listviewLocation.setAdapter(adapter);
                    listviewLocation.setVisibility(View.VISIBLE);

                }else {
                    listviewLocation.setVisibility(View.GONE);
                }
                ViewGroup.LayoutParams params = listviewLocation.getLayoutParams();
                listviewLocation.setLayoutParams(params);
                listviewLocation.requestLayout();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onStartFetch() {

            }
        }, URLEncoder.encode( url.toString().trim()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_finder, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            Intent i = new Intent();
            setResult(0, i);

            try {
                InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                View view = this.getCurrentFocus();
                if (view == null) {
                    view = new View(LocationActivity.this);
                }
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
            return true;
        } else if (id == R.id.action_Done) {
            Intent i = new Intent();
            Bundle BD = new Bundle();
            BD.putString("selectedPlace", selectedPlace);
            BD.putString("zip", zip_code);
            BD.putString("city", city);
            BD.putString("state", state);
            BD.putString("lattitude",lattitude);
            BD.putString("longttitude",longttitude);

            Logger.printMessage("Bundle", String.valueOf(BD));
            i.putExtra("data", BD);
            setResult(-1, i);
            //  startActivity(i);

            try {
                InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                View view = this.getCurrentFocus();
                if (view == null) {
                    view = new View(LocationActivity.this);
                }
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getZipCityState() {
        ProHelperClass.getInstance(LocationActivity.this).getZipLocationStateAPI(new ProHelperClass.getApiProcessCallback() {
            @Override
            public void onStart() {
                myLoader.showLoader();
            }

            @Override
            public void onComplete(String message) {
                try {

                    if (myLoader != null && myLoader.isMyLoaderShowing())
                        myLoader.dismissLoader();

                    JSONObject mainRes = new JSONObject(message);
                    Logger.printMessage("sdjl;fdlfj",message);
                    if (mainRes.getString("status").equalsIgnoreCase("OK") &&
                            mainRes.has("results") &&
                            mainRes.getJSONArray("results").length() > 0) {


                        JSONArray results = mainRes.getJSONArray("results");


                        JSONObject innerIncer = results.getJSONObject(0);

                        Logger.printMessage("jhdsdjsh", String.valueOf(innerIncer));
                        JSONObject geo=innerIncer.getJSONObject("geometry");
                        Logger.printMessage("geo", String.valueOf(geo));
                        JSONObject location= new JSONObject(String.valueOf(geo.getJSONObject("location")));
                        Logger.printMessage("loc", String.valueOf(location));
                        lattitude= String.valueOf(location.get("lat"));
                        longttitude= String.valueOf(location.get("lng"));
                        Logger.printMessage("lat", String.valueOf(lattitude));
                        Logger.printMessage("lon", String.valueOf(longttitude));

                        /**
                         * loop through address component
                         * for country and state
                         */
                        if (innerIncer.has("address_components") &&
                                innerIncer.getJSONArray("address_components").length() > 0) {

                            Logger.printMessage("address_components", "" + innerIncer.getJSONArray("address_components"));

                            JSONArray address_components = innerIncer.getJSONArray("address_components");

                            for (int j = 0; j < address_components.length(); j++) {

                                if (address_components.getJSONObject(j).has("types") &&
                                        address_components.getJSONObject(j).getJSONArray("types").length() > 0
                                        ) {

                                    JSONArray types = address_components.getJSONObject(j).getJSONArray("types");

                                    for (int k = 0; k < types.length(); k++) {
                                        if (types.getString(k).equals("locality")) {
                                            city = address_components.getJSONObject(j).getString("short_name");
                                            Logger.printMessage("city", "-->" + city);
                                        }
                                        if (types.getString(k).equals("administrative_area_level_1")) {
                                            state = address_components.getJSONObject(j).getString("short_name");
                                            Logger.printMessage("state", "-->" + state);
                                        }

                                        if (types.getString(k).equals("postal_code")) {
                                            zip_code = address_components.getJSONObject(j).getString("short_name");
                                            Logger.printMessage("zip_code", "-->" + zip_code);
                                        }
                                    }
//                                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                                    imm.hideSoftInputFromWindow(locationText.getWindowToken(), 0);
                                }
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (myLoader != null && myLoader.isMyLoaderShowing())
                        myLoader.dismissLoader();
                }
            }

            @Override
            public void onError(String error) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();
            }
        }, selectedPlace);
    }
}
