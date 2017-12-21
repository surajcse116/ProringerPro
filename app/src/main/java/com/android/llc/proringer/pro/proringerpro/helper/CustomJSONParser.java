package com.android.llc.proringer.pro.proringerpro.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;

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

    public static String ImageParam;

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

                    customJSONResponse.onStart();

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

    public void fireAPIForGetMethod(Context mcontext, final String URL, final ArrayList<SetGetAPI> setGetData, final CustomJSONResponse customJSONResponse) {

        Logger.printMessage("URLGet", URL);

        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {

            new AsyncTask<Void, Void, Void>() {

                String PARAMS = "";
                private String stringResponse = null;
                private Exception exception = null;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    customJSONResponse.onStart();

                    if (setGetData != null && setGetData.size() > 0) {
//                        PARAMS = "&";
                        for (SetGetAPI data : setGetData) {
                            PARAMS = PARAMS + data.getPARAMS() + "=" + data.getValues() + "&";
                        }
                        Logger.printMessage("url", "" + URL + PARAMS);
                    }
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

    public void APIForWithPhotoPostMethod(Context context, final String URL, final ArrayList<SetGetAPIPostData> apiPostDataArrayList, final ArrayList<File> Photos, final CustomJSONResponse customJSONResponse) {
        if (NetworkUtil.getInstance().isNetworkAvailable(context)) {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            new AsyncTask<Void, Void, Void>() {

                private String responseString = null;
                private Exception exception = null;
                MultipartBody.Builder builderNew;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    customJSONResponse.onStart();

                    Logger.printMessage("@@ POST URL- ", URL);

                    builderNew = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (SetGetAPIPostData data : apiPostDataArrayList) {
                        builderNew.addFormDataPart("" + data.getPARAMS(), data.getValues());
                        Logger.printMessage(data.getPARAMS(), data.getValues());
                    }

                    if (Photos!=null) {
                        for (File file : Photos) {
                                builderNew.addFormDataPart("" + ImageParam, file.getName() + "", RequestBody.create(MEDIA_TYPE_PNG, file));
                        }
                    }
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        if (!isCancelled()) {

                            MultipartBody requestBody = builderNew.build();
                            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(6000, TimeUnit.MILLISECONDS).build();
                            Request request = new Request.Builder().url(URL).method("POST", RequestBody.create(null, new byte[0]))
                                    .post(requestBody).build();
                            Response response = client.newCall(request).execute();


                            responseString = response.body().string();

                            Logger.printMessage("response", "response_::" + responseString);
                            Logger.printMessage("response", "response_ww_message::" + response.message());
                            Logger.printMessage("response", "response_ww_headers::" + response.headers());
                            Logger.printMessage("response", "response_ww_isRedirect::" + response.isRedirect());
//                       Loger.MSG("response", "response_ww_body::" + response.body().string());
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
                            if (new JSONObject(responseString).getBoolean("response")) {
                                customJSONResponse.onSuccess(responseString);
                            } else {
                                customJSONResponse.onError(new JSONObject(responseString).getString("message") + "", responseString);
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
            customJSONResponse.onError(context.getResources().getString(R.string.please_check_your_internet_connection));
        }
    }

    public void APIForWithPhotosMultiplePostMethod(Context context, final String URL, final ArrayList<SetGetAPIPostData> apiPostDataArrayList, final ArrayList<File> Photos, final CustomJSONResponse customJSONResponse) {
        if (NetworkUtil.getInstance().isNetworkAvailable(context)) {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            new AsyncTask<Void, Void, Void>() {

                private String responseString = null;
                private Exception exception = null;
                MultipartBody.Builder builderNew;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    customJSONResponse.onStart();

                    Logger.printMessage("@@ POST URL- ", URL);

                    builderNew = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (SetGetAPIPostData data : apiPostDataArrayList) {
                        builderNew.addFormDataPart("" + data.getPARAMS(), data.getValues());
                        Logger.printMessage(data.getPARAMS(), data.getValues());
                    }
                    int position=0;
                    for (File file : Photos) {
                        if (file != null) {
                            builderNew.addFormDataPart(ImageParam+"["+position+"]", file.getName() + "", RequestBody.create(MEDIA_TYPE_PNG, file));
                            position++;
                        }
                    }
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        if (!isCancelled()) {

                            MultipartBody requestBody = builderNew.build();
                            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(6000, TimeUnit.MILLISECONDS).build();
                            Request request = new Request.Builder().url(URL).method("POST", RequestBody.create(null, new byte[0]))
                                    .post(requestBody).build();
                            Response response = client.newCall(request).execute();


                            responseString = response.body().string();

                            Logger.printMessage("response", "response_::" + responseString);
                            Logger.printMessage("response", "response_ww_message::" + response.message());
                            Logger.printMessage("response", "response_ww_headers::" + response.headers());
                            Logger.printMessage("response", "response_ww_isRedirect::" + response.isRedirect());
//                       Loger.MSG("response", "response_ww_body::" + response.body().string());
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
                            if (new JSONObject(responseString).getBoolean("response")) {
                                customJSONResponse.onSuccess(responseString);
                            } else {
                                customJSONResponse.onError(new JSONObject(responseString).getString("message") + "", responseString);
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
            customJSONResponse.onError(context.getResources().getString(R.string.please_check_your_internet_connection));
        }
    }

    public interface CustomJSONResponse {
        void onSuccess(String result);

        void onError(String error, String response);

        void onError(String error);

        void onStart();
    }

    public void fireAPIForPostMethodNormalTxtArray(Context mcontext, final String url, final HashMap<String, String> apiPostData, final HashMap<String, String> postArrayText, final CustomJSONResponse customJSONResponse) {

        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {

            new AsyncTask<Void, Void, Void>() {

                MultipartBody.Builder builderNew;
                private String stringResponse = null;
                private Exception exception = null;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    customJSONResponse.onStart();

                    builderNew = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    Iterator myVeryOwnIterator = apiPostData.keySet().iterator();

                    while (myVeryOwnIterator.hasNext()) {
                        String key = (String) myVeryOwnIterator.next();
                        String value = (String) apiPostData.get(key);
                        Logger.printMessage(key, value);
                        builderNew.addFormDataPart(key, value);
                    }

                    if (postArrayText != null) {
                        myVeryOwnIterator = postArrayText.keySet().iterator();
                        while (myVeryOwnIterator.hasNext()) {
                            String key = (String) myVeryOwnIterator.next();
                            String value = (String) postArrayText.get(key);
                            Logger.printMessage(key, value);
                            builderNew.addFormDataPart(key, value);
                        }
                    }
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

}
