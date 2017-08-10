package com.android.llc.proringer.pro.proringerpro.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProApplication;
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.utils.NetworkUtil;

import org.json.JSONObject;

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

public class HelperClass {
    private static HelperClass instance = null;
    private static Context mcontext = null;

    public static HelperClass getInstance(Context context) {
        mcontext = context;
        if (instance == null)
            instance = new HelperClass();

        return instance;
    }

    private final String LOG_IN_API = "http://esolz.co.in/lab6/proringer_latest/app_pro_login";

    public void authenticateUser(String username, String password, final onResponseCallback callback) {
        if (NetworkUtil.getInstance().isNetworkAvailable(mcontext)) {
            new AsyncTask<String, Void, String>() {
                String exception = "";

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    callback.onStart();
                }

                @Override
                protected String doInBackground(String... strings) {
                    try {
                        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

                        RequestBody body = new FormBody.Builder()
                                .add("email", strings[0])
                                .add("password", strings[1])
                                .add("device_type", "a")
                                .add("user_type", "C")
                                .build();
                        Request request = new Request.Builder()
                                .post(body)
                                .url(LOG_IN_API)
                                .build();
                        Response response = client.newCall(request).execute();

                        Logger.printMessage("LogInActivity", "" + response);

                        /*{
                            info_array:
                            {
                                user_id:"206",
                                user_type:"C",
                                first_name:"Radhika",
                                last_name:"Gupta"
                            },
                            response:true,
                            message:"User logs in successfully",
                            execution_time:"0.0298"
                        }
                        */
                        String responseString = response.body().string();
                        response.close();

                        JSONObject jsonObject = new JSONObject(responseString);
                        if (jsonObject.getBoolean("response")) {
                            ProApplication.getInstance().setLoginPrefernce(jsonObject.getJSONObject("info_array").getString("user_id"), jsonObject.getJSONObject("info_array").getString("first_name"), jsonObject.getJSONObject("info_array").getString("last_name"));
                            return responseString;
                        } else {
                            exception = jsonObject.getString("message");
                            return exception;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        exception = e.getMessage();
                        return null;
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
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username, password);
        } else {
            callback.onError(mcontext.getResources().getString(R.string.no_internet_error));
        }

    }

    public interface onResponseCallback {
        void onStart();

        void onComplete(String response);

        void onError(String error);
    }
}
