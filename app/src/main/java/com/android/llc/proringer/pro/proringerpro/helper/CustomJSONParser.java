package com.android.llc.proringer.pro.proringerpro.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.android.llc.proringer.pro.proringerpro.pojo.APIGetData;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by su on 9/1/17.
 */

public class CustomJSONParser {

    public void fireAPIForPostMethod(Context mcontext, final String url, final HashMap<String, String> apiPostData, final HashMap<String, File> photos, final CustomJSONResponse customJSONResponse) {

        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {

            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            new AsyncTask<Void, Void, Void>() {

                MultipartBody.Builder builderNew;
                private String stringResponse = null;
                private Exception exception = null;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    builderNew = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    Iterator myVeryOwnIterator = apiPostData.keySet().iterator();

                    while (myVeryOwnIterator.hasNext()) {
                        String key = (String) myVeryOwnIterator.next();
                        String value = (String) apiPostData.get(key);
                        Logger.printMessage(key, value);
                        builderNew.addFormDataPart(key, value);
                    }

                    if (photos != null) {
                        myVeryOwnIterator = photos.keySet().iterator();
                        while (myVeryOwnIterator.hasNext()) {
                            String key = (String) myVeryOwnIterator.next();
                            File value = (File) photos.get(key);
                            {
                                if (value != null)
                                    builderNew.addFormDataPart("" + key, value.getName() + "", RequestBody.create(MEDIA_TYPE_PNG, value.getName()));

                            }
                        }
                    }
                    customJSONResponse.onStart();
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        if (!isCancelled()) {

                            MultipartBody requestBody = builderNew.build();
                            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(6000, TimeUnit.MILLISECONDS).build();
                            Request request = new Request.Builder().url(url).method("POST", RequestBody.create(null, new byte[0]))
                                    .post(requestBody).build();
                            Response response = client.newCall(request).execute();

                            stringResponse = response.body().string();

                            Logger.printMessage("response", "respose_::" + stringResponse);
                            Logger.printMessage("response", "respose_ww_message::" + response.message());
                            Logger.printMessage("response", "respose_ww_headers::" + response.headers());
                            Logger.printMessage("response", "respose_ww_isRedirect::" + response.isRedirect());
//                       Logger.printMessage("response", "respose_ww_body::" + response.body().string());
                        }
                    } catch (Exception e) {
                        this.exception = e;
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (!isCancelled() && exception == null) {

                        try {
                            if (new JSONObject(stringResponse).getBoolean("response")) {
                                customJSONResponse.onSuccess(stringResponse);
                            } else {
                                customJSONResponse.onError(new JSONObject(stringResponse).getString("message") + "", stringResponse);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        customJSONResponse.onError(exception.getMessage() + "");
                    }
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onCancelled(Void aVoid) {
                    super.onCancelled(aVoid);
                }

                @Override
                protected void onCancelled() {
                    super.onCancelled();
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            customJSONResponse.onError("No internet connection found. Please check your internet connection.");
        }
    }

    public void fireAPIForGetMethod(Context mcontext, final String URL, final ArrayList<APIGetData> apiGetData, final CustomJSONResponse customJSONResponse) {

        Logger.printMessage("URLGet", URL);

        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {

            new AsyncTask<Void, Void, Void>() {

                String PARAMS = "";
                private String stringResponse = null;
                private Exception exception = null;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    if (apiGetData != null && apiGetData.size() > 0) {
                        PARAMS = "&";
                        for (APIGetData data : apiGetData) {
                            PARAMS = PARAMS + data.getPARAMS() + "=" + data.getValues() + "&";
                        }
                        Logger.printMessage("url", "" + URL + PARAMS);
                    }

                    customJSONResponse.onStart();
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        if (!isCancelled()) {

                            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(6000, TimeUnit.MILLISECONDS).build();
                            Request request = new Request.Builder().url(URL + PARAMS).build();
                            Response response = client.newCall(request).execute();

                            stringResponse = response.body().string();
                            new JSONObject(stringResponse);

                            Logger.printMessage("response", "respose_::" + stringResponse);
                            Logger.printMessage("response", "respose_ww_message::" + response.message());
                            Logger.printMessage("response", "respose_ww_headers::" + response.headers());
                            Logger.printMessage("response", "respose_ww_isRedirect::" + response.isRedirect());
//                       Logger.printMessage("response", "respose_ww_body::" + response.body().string());
                        }
                    } catch (Exception e) {
                        this.exception = e;
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (!isCancelled() && exception == null) {
                        try {
                            if (new JSONObject(stringResponse).getBoolean("response")) {
                                customJSONResponse.onSuccess(stringResponse);
                            } else {
                                customJSONResponse.onError(new JSONObject(stringResponse).getString("message") + "", stringResponse);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        customJSONResponse.onError(exception.getMessage() + "");
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            customJSONResponse.onError("No internet connection found. Please check your internet connection.");
        }
    }


    public interface CustomJSONResponse {
        void onSuccess(String result);

        void onError(String error, String response);

        void onError(String error);

        void onStart();
    }
}
