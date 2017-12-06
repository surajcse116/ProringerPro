package com.android.llc.proringer.pro.proringerpro.activities;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.llc.proringer.pro.proringerpro.Constant.AppConstant;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


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

public class LogInActivity extends AppCompatActivity {
    private ProSemiBoldTextView sign_up;
    private ProSemiBoldTextView log_in;
    private ProLightEditText email, password;

    public MyLoader myLoader=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myLoader=new MyLoader(LogInActivity.this);
        final String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Logger.printMessage("DEVICE ID", android_id);
        findViewById(R.id.forget_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, ForgetPasswordActivity.class));
            }
        });
        sign_up = (ProSemiBoldTextView) findViewById(R.id.sign_up);
        log_in = (ProSemiBoldTextView) findViewById(R.id.log_in);
        email = (ProLightEditText) findViewById(R.id.email);
        password = (ProLightEditText) findViewById(R.id.password);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().trim().equals("")) {
                    email.setError("Please enter your email address.");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Please enter password.");
                } else {

                    HashMap<String, String> Params = new HashMap<>();
                    Params.put("email", email.getText().toString().trim());
                    Params.put("password", password.getText().toString().trim());
                    Params.put("device_type", "a");
                    Params.put("user_type", "C");
                    Params.put("device_token", android_id);
                    Logger.printMessage("PARAMS", String.valueOf(Params));
                    new CustomJSONParser().fireAPIForPostMethod(LogInActivity.this, AppConstant.Login, Params, null, new CustomJSONParser.CustomJSONResponse() {
                        @Override
                        public void onSuccess(String result) {
                            myLoader.dismissLoader();
                            JSONObject mainResponseObj = null;
                            try {
                                mainResponseObj = new JSONObject(result);
                                JSONObject jsonInfo = mainResponseObj.getJSONObject("info_array");
                                Logger.printMessage("infoArray", String.valueOf(jsonInfo));

                                ProApplication.getInstance().setUserPreference(jsonInfo.getString("user_id"), jsonInfo.getString("user_type"), jsonInfo.getString("first_name"), jsonInfo.getString("last_name"));
                                ProApplication.getInstance().setUserEmail(email.getText().toString().trim());
                                Intent i = new Intent(LogInActivity.this, LandScreenActivity.class);
                                startActivity(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(String error, String response) {
                            myLoader.dismissLoader();
                            new MYAlert(LogInActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                                @Override
                                public void OnOk(boolean res) {

                                }
                            });
                        }

                        @Override
                        public void onError(String error) {
                            myLoader.dismissLoader();
                            new MYAlert(LogInActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
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

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home)
//            onBackPressed();
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        setResult(GetStartedActivity.RESULT_CANCELED);
//        finish();
//        }
}

