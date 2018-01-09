package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
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

        if (et_fname.getText().toString().trim().equals("")) {
            et_fname.setError("Please enter the first name");
            et_fname.requestFocus();
        } else {
            et_fname.setError(null);
            et_fname.clearFocus();
            if (et_lname.getText().toString().trim().equals("")) {
                et_lname.setError("Please enter the last name");
                et_lname.requestFocus();
            } else {
                et_lname.setError(null);
                et_lname.clearFocus();
                if (Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {

                    et_email.setError(null);
                    et_email.clearFocus();

                    if (Patterns.EMAIL_ADDRESS.matcher((et_cemail.getText().toString())).matches()) {

                        et_cemail.setError(null);
                        et_cemail.clearFocus();

                        if (!et_cemail.getText().toString().trim().equals(et_email.getText().toString().trim())) {
                            et_cemail.setError("Email id dose not match!");
                            et_cemail.requestFocus();
                        } else {
                            et_cemail.setError(null);
                            et_cemail.clearFocus();
                            if (et_comment.getText().toString().trim().equals("")) {
                                et_comment.setError("Please enter  comment");
                                et_comment.requestFocus();
                            } else {
                                et_comment.setError(null);
                                et_comment.clearFocus();
                                HashMap<String, String> Params = new HashMap<>();
                                Params.put("user_id", ProApplication.getInstance().getUserId());
                                Params.put("first_name", et_fname.getText().toString().trim());
                                Params.put("last_name", et_lname.getText().toString().trim());
                                Params.put("email", et_email.getText().toString().trim());
                                Params.put("conf_emailid", et_cemail.getText().toString().trim());
                                Params.put("comment", et_comment.getText().toString().trim());
                                Logger.printMessage("parms", String.valueOf(Params));

                                new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.replyreviw, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                    @Override
                                    public void onSuccess(String result) {
                                        myload.dismissLoader();
                                        Logger.printMessage("result", result);
                                        try {
                                            JSONObject job = new JSONObject(result);
                                            String message = job.getString("message");
                                            Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                                            resetForm();
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
                        et_cemail.requestFocus();
                    }
                } else {
                    et_email.setError("Please enter valid email");
                    et_email.requestFocus();
                }
            }
        }

    }

    private void resetForm() {
        et_fname.setText("");
        et_lname.setText("");
        et_email.setText("");
        et_cemail.setText("");
        et_comment.setText("");
    }
}
