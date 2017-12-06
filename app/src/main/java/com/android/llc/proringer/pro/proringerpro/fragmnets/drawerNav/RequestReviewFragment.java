package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.Constant.AppConstant;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LicenceActivity;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.MyCustomAlertListener;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Bodhidipta on 25/07/17.
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

public class RequestReviewFragment extends Fragment {
    ProLightEditText et_fname, et_lname, et_email, et_cemail, et_comment;
    ProRegularTextView request;
    MyLoader myload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_fname = (ProLightEditText) view.findViewById(R.id.et_fname);
        et_lname = (ProLightEditText) view.findViewById(R.id.et_lname);
        et_email = (ProLightEditText) view.findViewById(R.id.et_email);
        et_cemail = (ProLightEditText) view.findViewById(R.id.et_cemail);
        et_comment = (ProLightEditText) view.findViewById(R.id.et_comment);
        request = (ProRegularTextView) view.findViewById(R.id.request);
        myload = new MyLoader(getActivity());
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                validation();
            }
        });

    }

    public void validation() {
        String fname = et_fname.getText().toString().trim();
        String lname = et_lname.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String cemail = et_cemail.getText().toString().trim();
        String comment = et_comment.getText().toString().trim();
        if (fname.equals("")) {
            et_fname.setError("Please enter the first name");
            et_fname.setFocusable(true);
        } else {
            if (lname.equals("")) {
                et_lname.setError("Please enter the last name");
                et_lname.setFocusable(true);
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
                    if (Patterns.EMAIL_ADDRESS.matcher((et_cemail.getText().toString())).matches()) {
                        if (!et_cemail.getText().toString().trim().equals(et_email.getText().toString().trim())) {
                            et_cemail.setError("Email id dose not match!");
                            et_cemail.setFocusable(true);
                        } else {
                            if (comment.equals("")) {
                                et_comment.setError("Please enter  comment");
                                et_comment.setFocusable(true);
                            } else {
                                HashMap<String, String> Params = new HashMap<>();
                                Params.put("user_id", ProApplication.getInstance().getUserId());
                                Params.put("first_name", fname);
                                Params.put("last_name", lname);
                                Params.put("email", email);
                                Params.put("conf_emailid", cemail);
                                Params.put("comment", comment);
                                Log.d("parms", String.valueOf(Params));

                                new CustomJSONParser().fireAPIForPostMethod(getActivity(), AppConstant.replyreviw, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                    @Override
                                    public void onSuccess(String result) {
                                        myload.dismissLoader();
                                        Log.d("result", result);
                                        try {
                                            JSONObject job = new JSONObject(result);
                                            String message = job.getString("message");
                                            Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onError(String error, String response) {
                                        myload.dismissLoader();
                                        Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onError(String error) {
                                        myload.dismissLoader();


                                        CustomAlert customAlert = new CustomAlert();
                                        customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Ok", "Cancel", new CustomAlert.MyCustomAlertListener() {
                                            @Override
                                            public void callBackOk() {

                                            }

                                            @Override
                                            public void callBackCancel() {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onStart() {
                                        myload.showLoader();

                                    }
                                });

                            }

                        }

                    } else {
                        et_cemail.setError("Please enter valid confirm email");
                        et_cemail.setFocusable(true);
                    }

                } else {
                    et_email.setError("Please enter valid email");
                    et_email.setFocusable(true);
                }
            }
        }

    }
}
