package com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LogInActivity;
import com.android.llc.proringer.pro.proringerpro.activities.SignUpActivity;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

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

public class RegistrationOne extends Fragment {
    ProRegularTextView tv_next;
    ProLightEditText proet_fname, proet_lname, proet_email, proet_c_email, proet_phone, proet_password, proet_c_password;
    int textLength = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_next = (ProRegularTextView) view.findViewById(R.id.tv_next);
        proet_fname = (ProLightEditText) view.findViewById(R.id.proet_fname);
        proet_lname = (ProLightEditText) view.findViewById(R.id.proet_lname);
        proet_email = (ProLightEditText) view.findViewById(R.id.proet_email);
        proet_c_email = (ProLightEditText) view.findViewById(R.id.proet_c_email);
        proet_phone = (ProLightEditText) view.findViewById(R.id.proet_phone);
        proet_password = (ProLightEditText) view.findViewById(R.id.proet_password);
        proet_c_password = (ProLightEditText) view.findViewById(R.id.proet_c_password);

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        view.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LogInActivity.class));
            }
        });


        proet_phone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = proet_phone.getText().toString();
                textLength = proet_phone.getText().length();

                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;

                if (textLength == 1) {
                    if (!text.contains("(")) {
                        proet_phone.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                        proet_phone.setSelection(proet_phone.getText().length());
                    }

                } else if (textLength == 5) {

                    if (!text.contains(")")) {
                        proet_phone.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        proet_phone.setSelection(proet_phone.getText().length());
                    }

                } else if (textLength == 6) {
                    proet_phone.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    proet_phone.setSelection(proet_phone.getText().length());

                } else if (textLength == 10) {
                    if (!text.contains("-")) {
                        proet_phone.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        proet_phone.setSelection(proet_phone.getText().length());
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });


    }

    public void validation() {
        ProConstant.f_name = proet_fname.getText().toString().trim();
        ProConstant.l_name = proet_lname.getText().toString().trim();
        ProConstant.email = proet_c_email.getText().toString().trim();
        ProConstant.phone = proet_phone.getText().toString().trim();
        ProConstant.password = proet_c_password.getText().toString().trim();


        Logger.printMessage("f_name", ProConstant.f_name);
        Logger.printMessage("l_name", ProConstant.l_name);
        Logger.printMessage("email", ProConstant.email);
        Logger.printMessage("phone", ProConstant.phone);
        Logger.printMessage("password", ProConstant.password);


        if (proet_fname.getText().toString().trim().equals("")){
            proet_fname.setError("Please enter First Name");
            proet_fname.requestFocus();
        }else {
            proet_fname.setError(null);
            proet_fname.clearFocus();

            if (proet_lname.getText().toString().trim().equals("")){
                proet_lname.setError("Please enter First Name");
                proet_lname.requestFocus();
            }else {
                proet_lname.setError(null);
                proet_lname.clearFocus();

                if (proet_email.getText().toString().trim().equals("")){
                    proet_email.setError("Please enter Email");
                    proet_email.requestFocus();
                }else {
                    proet_email.setError(null);
                    proet_email.clearFocus();

                    if (MethodsUtils.isValidEmail(proet_email.getText().toString().trim())){

                        proet_email.setError(null);
                        proet_email.clearFocus();

                        if (!proet_email.getText().toString().trim().equals(proet_c_email.getText().toString().trim())){
                            proet_c_email.setError("Confirm Email is not same with given Email");
                            proet_c_email.requestFocus();
                        }
                        else {
                            proet_c_email.setError(null);
                            proet_c_email.clearFocus();

                            if (proet_phone.getText().toString().trim().equals("")){

                                proet_phone.setError("Enter Phone Number");
                                proet_phone.requestFocus();
                            }
                            else {

                                proet_phone.setError(null);
                                proet_phone.clearFocus();


                                if (proet_phone.getText().toString().trim().length()<14){

                                    proet_phone.setError("Enter valid Phone Number");
                                    proet_phone.requestFocus();
                                }
                                else {

                                    proet_phone.setError(null);
                                    proet_phone.clearFocus();


                                    if (proet_password.getText().toString().trim().equals("")){

                                        proet_password.setError("Enter Password");
                                        proet_password.requestFocus();
                                    }
                                    else {

                                        proet_password.setError(null);
                                        proet_password.clearFocus();


                                        if (proet_password.getText().toString().trim().length()<6){

                                            proet_password.setError("password length should be 6 or greater than 6");
                                            proet_password.requestFocus();
                                        }
                                        else {

                                            proet_password.setError(null);
                                            proet_password.clearFocus();


                                            if (!proet_c_password.getText().toString().trim().equals(proet_password.getText().toString().trim())){

                                                proet_c_password.setError("confirm password should be same with password");
                                                proet_c_password.requestFocus();
                                            }
                                            else {

                                                proet_c_password.setError(null);
                                                proet_c_password.clearFocus();

                                                ((SignUpActivity) getActivity()).transactRegistrationFragmentTwo();

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        proet_email.setError("Please Valid Email");
                        proet_email.requestFocus();
                    }
                }
            }
        }
    }
}
