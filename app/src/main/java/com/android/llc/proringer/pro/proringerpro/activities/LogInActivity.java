package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;


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
    private ProgressDialog pgDialog = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
//                    HelperClass.getInstance(LogInActivity.this).authenticateUser(
//                            email.getText().toString().trim(),
//                            password.getText().toString().trim(),
//                            new HelperClass.onResponseCallback() {
//                                @Override
//                                public void onStart() {
//                                    pgDialog = new ProgressDialog(LogInActivity.this);
//                                    pgDialog.setTitle("Log In");
//                                    pgDialog.setMessage("Logging in. Please wait.");
//                                    pgDialog.setCancelable(false);
//                                    pgDialog.show();
//                                }
//
//                                @Override
//                                public void onComplete(String response) {
//                                    if (pgDialog != null && pgDialog.isShowing())
//                                        pgDialog.dismiss();
//                                }
//
//                                @Override
//                                public void onError(String error) {
//                                    if (pgDialog != null && pgDialog.isShowing())
//                                        pgDialog.dismiss();
//                                    new AlertDialog.Builder(LogInActivity.this)
//                                            .setTitle("Log In Error")
//                                            .setMessage("" + error)
//                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                    dialogInterface.dismiss();
//                                                }
//                                            })
//                                            .show();
//                                }
//                            }
//                    );
                    setResult(GetStartedActivity.RESULT_OK);
                    finish();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(GetStartedActivity.RESULT_CANCELED);
        finish();
    }
}
