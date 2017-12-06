package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.app.ProgressDialog;
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

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by bodhidipta on 22/06/17.
 * <!-- * Copyright (c) 2017, The Proringer-->
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

public class InviteAfriendFragment extends Fragment{
    ProLightEditText first_name, last_name, email, confirm_email;
    ProRegularTextView invited_submit;
    MyLoader myload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invite_friend, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        first_name = (ProLightEditText) view.findViewById(R.id.first_name);
        last_name = (ProLightEditText) view.findViewById(R.id.last_name);
        email = (ProLightEditText) view.findViewById(R.id.email);
        confirm_email = (ProLightEditText) view.findViewById(R.id.confirm_email);
        invited_submit=(ProRegularTextView)view.findViewById(R.id.invited_submit);
        myload=new MyLoader(getActivity());
        invited_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                validateInvite();
            }
        });

    }

    private void validateInvite() {
        String fname=first_name.getText().toString().trim();
        String  lname=last_name.getText().toString().trim();
        String   mail=email.getText().toString().trim();
        String   cmail=confirm_email.getText().toString().trim();
        if (first_name.getText().toString().trim().equals("")) {
            first_name.setError("First name can not be blank.");
        } else {
            if (last_name.getText().toString().trim().equals("")) {
                last_name.setError("Last name can not be blank.");
                last_name.requestFocus();
            } else {
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Email name can not be blank.");
                    email.requestFocus();
                } else {
                    if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
                        if (email.getText().toString().trim().equals(confirm_email.getText().toString().trim())) {

                            HashMap<String,String> Params=new HashMap<>();
                            Params.put("user_id", ProApplication.getInstance().getUserId());
                            Params.put("first_name",fname);
                            Params.put("last_name",lname);
                            Params.put("email",mail);
                            Params.put("conf_emailid",cmail);

                            Logger.printMessage("params", String.valueOf(Params));

                          new CustomJSONParser() .fireAPIForPostMethod(getActivity(), ProConstant.invitefriend, Params, null, new CustomJSONParser.CustomJSONResponse() {
                              @Override
                              public void onSuccess(String result) {
                                  Logger.printMessage("result",result);
                                  myload.dismissLoader();
                                  try {
                                      JSONObject job= new JSONObject(result);
                                      String message= job.getString("message");
                                      Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                              }
                              @Override
                              public void onError(String error, String response) {
                                  myload.dismissLoader();
                                  Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                              }

                              @Override
                              public void onError(String error) {

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

                        } else {
                            confirm_email.setError("Email and confirm email does not match.");
                            confirm_email.requestFocus();
                        }
                    } else {
                        email.setError("Invalid email address.");
                        email.requestFocus();
                    }
                }
            }
        }
    }


    private void resetForm(){
        first_name.setText("");
        last_name.setText("");
        email.setText("");
        confirm_email.setText("");
    }

}
