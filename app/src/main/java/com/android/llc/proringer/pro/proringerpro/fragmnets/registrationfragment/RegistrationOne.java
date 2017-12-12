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
    ProRegularTextView next;
    ProLightEditText proet_fname, proet_lname, proet_email, proet_cemail, proet_phone, proet_password, proet_cpassword;
    int textLength = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next = (ProRegularTextView) view.findViewById(R.id.next);
        proet_fname = (ProLightEditText) view.findViewById(R.id.proet_fname);
        proet_lname = (ProLightEditText) view.findViewById(R.id.proet_lname);
        proet_email = (ProLightEditText) view.findViewById(R.id.proet_email);
        proet_cemail = (ProLightEditText) view.findViewById(R.id.proet_cemail);
        proet_phone = (ProLightEditText) view.findViewById(R.id.proet_phone);
        proet_password = (ProLightEditText) view.findViewById(R.id.proet_password);
        proet_cpassword = (ProLightEditText) view.findViewById(R.id.proet_cpassword);

        next.setOnClickListener(new View.OnClickListener() {
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
        ProConstant.f_name = proet_fname.getText().toString();
        ProConstant.l_name = proet_lname.getText().toString();
        ProConstant.email = proet_cemail.getText().toString();
        ProConstant.phone = proet_phone.getText().toString();
        ProConstant.password = proet_cpassword.getText().toString();
        String password = proet_password.getText().toString();

        Logger.printMessage("f_name", ProConstant.f_name);
        Logger.printMessage("l_name", ProConstant.l_name);
        Logger.printMessage("email", ProConstant.email);
        Logger.printMessage("phone", ProConstant.phone);
        Logger.printMessage("password", ProConstant.password);

        if (proet_fname.getText().toString().trim().equals("")) {
            proet_fname.setError("Please enter First name.");

            proet_fname.setFocusable(true);
        } else {
            if (proet_lname.getText().toString().trim().equals("")) {
                proet_lname.setError("Please enter Last name.");

                proet_lname.setFocusable(true);
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(proet_email.getText().toString().trim()).matches()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(proet_cemail.getText().toString().trim()).matches()) {
                        if (!proet_cemail.getText().toString().trim().equals(proet_email.getText().toString().trim())) {
                            proet_cemail.setError("Email id dosenot match");
                            proet_cemail.setFocusable(true);
                        } else {
                            if (proet_phone.getText().toString().trim().equals("")) {
                                proet_phone.setError("please enter phone number");
                                proet_phone.setFocusable(true);
                            } else {
                                if (TextUtils.isEmpty(password) || password.length() < 6) {

                                    proet_password.setError("Please enter password at least 6 character");
                                    proet_password.setFocusable(true);

                                } else {
                                    if (proet_cpassword.getText().toString().trim().equals("")) {

                                        proet_cpassword.setError("Enter confirm password");
                                    } else {
                                        if (proet_password.getText().toString().trim().equals(proet_cpassword.getText().toString().trim())) {

                                            ((SignUpActivity) getActivity()).transactRegistrationFragmentTwo();


                                        } else {

                                            proet_cpassword.setError("password dosenot match");
                                            proet_cpassword.setFocusable(true);
                                        }
                                    }

                                }
                            }
                        }

                    } else {

                        proet_cemail.setError("please enter valid confirm email");
                        proet_cemail.setFocusable(true);
                    }

                } else {
                    proet_email.setError("Please enter valid email address");
                    proet_email.setFocusable(true);

                }

            }
        }
    }
}
