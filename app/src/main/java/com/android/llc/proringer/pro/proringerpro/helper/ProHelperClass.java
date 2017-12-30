package com.android.llc.proringer.pro.proringerpro.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.database.DatabaseHandler;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAddressData;
import com.android.llc.proringer.pro.proringerpro.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Bodhidipta on 24/07/17.
 * <!-- * Copyright (c) 2017, The ProringerPro-->
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
 */

public class ProHelperClass {
    private static ProHelperClass instance = null;
    private static Context mcontext = null;
    private String currentLat = "";
    private String currentLng = "";

    public static ProHelperClass getInstance(Context context) {
        mcontext = context;
        if (instance == null)
            instance = new ProHelperClass();

        return instance;
    }

    private ProHelperClass() {
    }

    public void setCurrentLatLng(String currentLat, String currentLng) {
        this.currentLat = currentLat;
        this.currentLng = currentLng;
    }


    public void getSearchCountriesByPlacesFilter(final onSearchPlacesNameCallback callback, String... params) {
        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                String exception = "";
                ArrayList<String> addressList;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStartFetch();
                }

                @Override
                protected String doInBackground(String... params) {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                    try {
                        String searchLocalProject = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + params[0] + "&key=AIzaSyDoLuAdSE7M9SzeIht7-Bm-WrUjnDQBofg&language=en";
                        Logger.printMessage("searchLocationAPI", "" + searchLocalProject);
                        Request request = new Request.Builder()
                                .url(searchLocalProject)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();

                        JSONObject mainRes = new JSONObject(responseString);

                        if (mainRes.getString("status").equalsIgnoreCase("OK") &&
                                mainRes.has("predictions") &&
                                mainRes.getJSONArray("predictions").length() > 0) {

                            addressList = new ArrayList<String>();
                            JSONArray predictions = mainRes.getJSONArray("predictions");

                            for (int i = 0; i < predictions.length(); i++) {

                                JSONObject innerIncer = predictions.getJSONObject(i);

                                if (innerIncer.has("terms") &&
                                        innerIncer.getJSONArray("terms").length() > 0) {

                                    /**
                                     * loop through address component
                                     * for country and state
                                     */

                                    JSONArray terms = innerIncer.getJSONArray("terms");

                                    for (int j = 0; j < terms.length(); j++) {
                                        if (terms.getJSONObject(j).getString("value").contains("United States") ||
                                                terms.getJSONObject(j).getString("value").contains("Canada")) {
                                            Logger.printMessage("description", "" + innerIncer.getString("description"));
                                            addressList.add(innerIncer.getString("description"));
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        return responseString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = "Some error occured while searching entered zip. Please search again.";
                        return exception;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Logger.printMessage("location", s);
                    if (exception.equals("")) {
                        if (addressList != null && addressList.size() > 0)
                            callback.onComplete(addressList);
                    } else {
                        callback.onError(exception);
                    }
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);

        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection));
        }
    }

    /**
     * get notification status
     *
     * @param callback
     */

    public void getUserNotification(final getApiProcessCallback callback) {
        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                String exception = "";

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStart();
                }

                @Override
                protected String doInBackground(String... params) {
                    try {
                        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                        String notificationAPI = ProConstant.notification + "?user_id=" + com.android.llc.proringer.pro.proringerpro.helper.ProApplication.getInstance().getUserId();
                        Logger.printMessage("notificationAPI", notificationAPI);

                        Request request = new Request.Builder()
                                .get()
                                .url(notificationAPI)
                                .build();

                        Logger.printMessage("notificationAPI", notificationAPI);

                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        if (jsonObject.getBoolean("response")) {

                            JSONObject jsonrespnse = new JSONObject(responseString);
                            JSONArray infoJsonArr = jsonrespnse.getJSONArray("info_array");
                            JSONObject innerObject = infoJsonArr.getJSONObject(0);
                            JSONObject emailObj = innerObject.getJSONObject("Email");
                            JSONObject mobileObj = innerObject.getJSONObject("Mobile");

                            ProApplication.getInstance().setNotificationPreference(
                                    emailObj.getString("newsletter"),
                                    emailObj.getString("chat_msg"),
                                    emailObj.getString("tips_article"),
                                    emailObj.getString("job_post"),
                                    emailObj.getString("new_reviews"),
                                    emailObj.getString("account_acheive"),
                                    mobileObj.getString("newsletter"),
                                    mobileObj.getString("chat_msg"),
                                    mobileObj.getString("tips_article"),
                                    mobileObj.getString("job_post"),
                                    mobileObj.getString("new_reviews"),
                                    mobileObj.getString("account_acheive"));

                            return jsonObject.getString("message");
                        } else {
                            exception = jsonObject.getString("message");
                            return exception;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = e.getMessage();
                        return exception;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (exception.equals("")) {
                        callback.onComplete(s);
                    } else {
                        callback.onError(s);
                    }
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection));
        }
    }

    public void updateUserNotification(final getApiProcessCallback callback, String... params) {
        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                String exception = "";

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStart();
                }

                @Override
                protected String doInBackground(String... params) {
                    try {
                        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                        RequestBody requestBody = new FormBody.Builder()
                                .add("user_id", com.android.llc.proringer.pro.proringerpro.helper.ProApplication.getInstance().getUserId())
                                .add(" email_newsletter", params[0])
                                .add("email_chat_msg", params[1])
                                .add("email_tips_article", params[2])
                                .add("email_job_post", params[3])
                                .add("email_new_reviews", params[4])
                                .add("email_acc_achieve", params[5])
                                .add("mobile_newsletter", params[6])
                                .add("mobile_chat_msg", params[7])
                                .add("mobile_article", params[8])
                                .add("mobile_job_post", params[9])
                                .add("mobile_new_reviews", params[10])
                                .add("mobile_acc_achieve", params[11])
                                .build();

                        Logger.printMessage("user_id", ":-" + com.android.llc.proringer.pro.proringerpro.helper.ProApplication.getInstance().getUserId());
                        Logger.printMessage("email_newsletter", ":-" + params[0]);
                        Logger.printMessage("email_chat_msg", ":-" + params[1]);
                        Logger.printMessage("email_tips_article", ":-" + params[2]);
                        Logger.printMessage("email_project_replies", ":-" + params[3]);
                        Logger.printMessage("email_new_reviews", ":-" + params[4]);
                        Logger.printMessage("email_acc_achieve", ":-" + params[5]);
                        Logger.printMessage("mobile_newsletter", ":-" + params[6]);
                        Logger.printMessage("mobile_chat_msg", ":-" + params[7]);
                        Logger.printMessage("mobile_article", ":-" + params[8]);
                        Logger.printMessage("mobile_job_post", ":-" + params[9]);
                        Logger.printMessage("mobile_new_reviews", ":-" + params[10]);
                        Logger.printMessage("mobile_acc_achieve", ":-" + params[11]);
                        Logger.printMessage("updateNotificationDetailsAPI", ProConstant.updateNotificationDetailsAPI);


                        Request request = new Request.Builder()
                                .post(requestBody)
                                .url(ProConstant.updateNotificationDetailsAPI)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        if (jsonObject.getBoolean("response")) {
                            return jsonObject.getString("message");
                        } else {
                            exception = jsonObject.getString("message");
                            return exception;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = e.getMessage();
                        return exception;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (exception.equals("")) {
                        callback.onComplete(s);
                    } else {
                        callback.onError(s);
                    }
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection));
        }

    }



    public void getZipLocationStateAPI(final getApiProcessCallback callback, String... params) {

        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                String exception = "";

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStart();
                }

                @Override
                protected String doInBackground(String... params) {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                    try {
                        String query = URLEncoder.encode(params[0], "utf-8");
                        String searchLocalProject = "https://maps.googleapis.com/maps/api/geocode/json?address=" + query + "&key=AIzaSyDoLuAdSE7M9SzeIht7-Bm-WrUjnDQBofg&language=en";
                        Logger.printMessage("searchLocationAPI", "" + searchLocalProject);
                        Request request = new Request.Builder()
                                .url(searchLocalProject)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();

                        JSONObject mainRes = new JSONObject(responseString);

                        if (mainRes.getString("status").equalsIgnoreCase("OK")) {
                            return responseString;
                        } else {
                            exception = mainRes.getString("status");
                            return exception;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = "Something error. Please search again.";
                        return exception;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try {
                        JSONObject jo =new JSONObject(s);
                        JSONArray JOB= jo.getJSONArray("results");
                        for (int i=0;i<JOB.length();i++)
                        {
                            JSONObject ji= JOB.getJSONObject(i);
                            ProConstant.placeid=ji.getString("place_id");
                            Logger.printMessage("palceidvalue",ProConstant.placeid);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (exception.equals("")) {
                        callback.onComplete(s);
                        Logger.printMessage("Message",s);
                    } else {
                        callback.onError(exception);
                    }
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);

        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection));
        }
    }

    public void getlatlongAPI(final getApiProcessCallback callback, String... params) {

        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                String exception = "";

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStart();
                }

                @Override
                protected String doInBackground(String... params) {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                    try {
                        String query = URLEncoder.encode(params[0], "utf-8");
                        String searchLocalProject = "https://maps.googleapis.com/maps/api/geocode/json?address=" + query + "&key=AIzaSyDoLuAdSE7M9SzeIht7-Bm-WrUjnDQBofg&language=en";
                        Logger.printMessage("searchLocationAPI", "" + searchLocalProject);
                        Request request = new Request.Builder()
                                .url(searchLocalProject)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();

                        JSONObject mainRes = new JSONObject(responseString);

                        if (mainRes.getString("status").equalsIgnoreCase("OK")) {
                            return responseString;
                        } else {
                            exception = mainRes.getString("status");
                            return exception;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = "Something error. Please search again.";
                        return exception;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try {
                        JSONObject jo =new JSONObject(s);
                        JSONArray JOB= jo.getJSONArray("results");
                        for (int i=0;i<JOB.length();i++)
                        {
                            JSONObject ji= JOB.getJSONObject(i);
                            ProConstant.placeid=ji.getString("place_id");
                            Logger.printMessage("palceidvalue",ProConstant.placeid);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (exception.equals("")) {
                        callback.onComplete(s);
                        Logger.printMessage("Message",s);
                    } else {
                        callback.onError(exception);
                    }
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);

        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection));
        }
    }


    /**
     * Search area by google API
     *
     * @param callback
     * @param params
     */

    public void getSearchArea(final onSearchZipCallback callback, String... params) {
        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                String exception = "";
                List<SetGetAddressData> addressList;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStartFetch();
                }

                @Override
                protected String doInBackground(String... params) {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                    try {
                        String searchLocalProject = "https://maps.googleapis.com/maps/api/geocode/json?address=" + params[0] + "&key=AIzaSyDoLuAdSE7M9SzeIht7-Bm-WrUjnDQBofg&language=en";
                        Logger.printMessage("searchLocationAPI", "" + searchLocalProject);
                        Request request = new Request.Builder()
                                .url(searchLocalProject)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();

                        JSONObject mainRes = new JSONObject(responseString);

                        if (mainRes.getString("status").equalsIgnoreCase("OK") &&
                                mainRes.has("results") &&
                                mainRes.getJSONArray("results").length() > 0) {

                            addressList = new ArrayList<SetGetAddressData>();
                            JSONArray results = mainRes.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {

                                JSONObject outerJsonObj = results.getJSONObject(i);
                                if (outerJsonObj.getJSONArray("types").toString().contains("postal_code")) {

                                    String city = "";
                                    String country = "";
                                    String state_code = "";

                                    /**
                                     * loop through address component
                                     * for country and state
                                     */
                                    if (outerJsonObj.has("address_components") &&
                                            outerJsonObj.getJSONArray("address_components").length() > 0) {

                                        JSONArray address_components = outerJsonObj.getJSONArray("address_components");

                                        for (int j = 0; j < address_components.length(); j++) {

                                            if (address_components.getJSONObject(j).has("types") &&
                                                    address_components.getJSONObject(j).getJSONArray("types").length() > 0
                                                    ) {

                                                JSONArray types = address_components.getJSONObject(j).getJSONArray("types");

                                                for (int k = 0; k < types.length(); k++) {
                                                    if (types.getString(k).equals("administrative_area_level_2")) {
                                                        city = address_components.getJSONObject(j).getString("short_name");
                                                    }

                                                    if (types.getString(k).equals("administrative_area_level_1")) {
                                                        state_code = address_components.getJSONObject(j).getString("short_name");
                                                    }

                                                    if (types.getString(k).equals("country")) {
                                                        country = address_components.getJSONObject(j).getString("short_name");
                                                    }
                                                }
                                            }
                                        }
                                        if (country.equals("CA") || country.equals("US")) {
                                            SetGetAddressData data = new SetGetAddressData(
                                                    outerJsonObj.getString("formatted_address"),
                                                    state_code,
                                                    country,
                                                    params[0]
                                            );
                                            data.setCity(city);
                                            data.setLatitude(outerJsonObj.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                            data.setLongitude(outerJsonObj.getJSONObject("geometry").getJSONObject("location").getString("lng"));
                                            addressList.add(data);
                                        } else {
                                            exception = "This Zip code does not belongs to the USA or CANADA";
                                        }
                                    }
                                } else {
                                    exception = "Please enter valid postal code.";
                                }
                            }
                        }

                        Logger.printMessage("location", "" + responseString);
                        return responseString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = "Some error occured while searching entered zip. Please search again.";
                        return exception;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (exception.equals("")) {
                        if (addressList != null && addressList.size() > 0)
                            callback.onComplete(addressList);
                    } else {
                        callback.onError(exception);
                    }
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);

        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection));
        }
    }


    /**
     * get zip code  using google api
     *
     * @param callback
     */
    public void getZipCodeUsingGoogleApi(final getApiProcessCallback callback) {
        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                ;
                String exception = "";

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStart();
                }

                @Override
                protected String doInBackground(String... params) {
                    try {
                        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                        String geocodenotiAPI = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + ProHelperClass.getInstance(mcontext).getCurrentLatLng()[0] + "," + ProHelperClass.getInstance(mcontext).getCurrentLatLng()[1]
                                + "&key=AIzaSyDoLuAdSE7M9SzeIht7-Bm-WrUjnDQBofg&language=en";
                        Logger.printMessage("geocode", geocodenotiAPI);
                        Request request = new Request.Builder()
                                .get()
                                .url(geocodenotiAPI)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        // Logger.printMessage("jsonObject",""+jsonObject);
                        if (jsonObject.getString("status").equalsIgnoreCase("OK")) {
                            return responseString;
                        } else {
                            exception = jsonObject.getString("error_message");
                            return exception;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = e.getMessage();
                        return exception;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (exception.equals("")) {
                        callback.onComplete(s);
                    } else {
                        callback.onError(s);
                    }
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection));
        }
    }


    public interface onSearchPlacesNameCallback {
        void onComplete(ArrayList<String> listdata);

        void onError(String error);

        void onStartFetch();
    }

    /**
     * Interface used to get call back for search locationList
     */
    public interface onSearchZipCallback {
        void onComplete(List<SetGetAddressData> listdata);

        void onError(String error);

        void onStartFetch();
    }

    public interface getApiProcessCallback {
        void onStart();

        void onComplete(String message);

        void onError(String error);
    }

    public String[] getCurrentLatLng() {
        String str[] = {
                currentLat, currentLng
        };
        return str;
    }
}
