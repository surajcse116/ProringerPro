package com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LocationActivity;
import com.android.llc.proringer.pro.proringerpro.activities.SignupCompleteActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialog_catagory;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyCustomAlertListener;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProLightTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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

public class RegistrationTwo extends Fragment implements MyCustomAlertListener {


    ProLightEditText prolight_businessname,prolight_address,prolight_city,prolight_State,prolight_zip,prolight_phone,prolight_email,prolight_service,prolight_service_area;
    ProRegularTextView protv_com,tv_service;
    public MyLoader myload= null;
    RelativeLayout relative_dropdown;
    JSONArray catagory;
    PopupWindow popupWindow;
   CustomListAdapterDialog_catagory custom = null;
    String pros_contact_service="";
    ImageView dropdown;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProLightTextView terms_and_condition=(ProLightTextView)view.findViewById(R.id.terms_and_condition);
        String contactTextOne = "By singing up with ProRinger you agree with our  ";
        String contactTextClick = "Terms of Use";
        String contactTextTwo = " and ";
        String privacyPolicies = "Privacy Policy";

        myload= new MyLoader(getActivity());

        protv_com=(ProRegularTextView)view.findViewById(R.id.protv_com);
        prolight_businessname=(ProLightEditText)view.findViewById(R.id.prolight_businessname);
        prolight_address=(ProLightEditText)view.findViewById(R.id.prolight_address);
        prolight_city=(ProLightEditText)view.findViewById(R.id.prolight_city);
        prolight_State=(ProLightEditText)view.findViewById(R.id.prolight_State);
        prolight_zip=(ProLightEditText)view.findViewById(R.id.prolight_zip);
        prolight_phone=(ProLightEditText)view.findViewById(R.id.prolight_phone);
        prolight_email=(ProLightEditText)view.findViewById(R.id.prolight_email);
        prolight_service_area=(ProLightEditText)view.findViewById(R.id.prolight_service_area);
        tv_service=(ProRegularTextView)view.findViewById(R.id.tv_service);
        dropdown=(ImageView)view.findViewById(R.id.dropdown);
        relative_dropdown=(RelativeLayout)view.findViewById(R.id.relative_dropdown);
        prolight_address.setFocusable(false);
        prolight_address.setClickable(false);
        prolight_city.setEnabled(false);
        prolight_city.setClickable(false);
        prolight_State.setEnabled(false);
        prolight_State.setClickable(false);
        prolight_zip.setEnabled(false);
        prolight_phone.setClickable(true);
        catagory();
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogServies(view,catagory);

            }
        });
        relative_dropdown=(RelativeLayout)view.findViewById(R.id.relative_dropdown);
        relative_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_dropdown.setBackgroundResource(R.drawable.background_solidorange_border);
            }
        });
        prolight_zip.setClickable(false);
        protv_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validation();

            }
        });
        prolight_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent( getActivity(), LocationActivity.class);
                startActivityForResult(i,1);

            }
        });

        Spannable word1 = new SpannableString(contactTextOne);

        word1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, contactTextOne.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        terms_and_condition.setText(word1);


        Spannable word2 = new SpannableString(contactTextClick);

        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // There is the OnCLick. put your intent to Register class here
                widget.invalidate();
                Logger.printMessage("SpanHello", "click");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorAccent));
                ds.setUnderlineText(false);
            }
        };
        word2.setSpan(myClickableSpan, 0, contactTextClick.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        terms_and_condition.append(word2);

        Spannable word3 = new SpannableString(contactTextTwo);

        word3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, contactTextTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        terms_and_condition.append(word3);

        Spannable word4 = new SpannableString(privacyPolicies);

        ClickableSpan privacySpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // There is the OnCLick. put your intent to Register class here
                widget.invalidate();
                Logger.printMessage("SpanHello", "click");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorAccent));
                ds.setUnderlineText(false);
            }
        };
        word4.setSpan(privacySpan, 0, privacyPolicies.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        terms_and_condition.append(word4);



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.printMessage("data", String.valueOf(data));

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle extras = data.getBundleExtra("data");
                if (extras != null) {

                    Logger.printMessage("selectedPlace", "--->" + extras.getString("selectedPlace"));
                    Logger.printMessage("zip", "--->" + extras.getString("zip"));
                    Logger.printMessage("city", "--->" + extras.getString("city"));
                    Logger.printMessage("state", "--->" + extras.getString("state"));

                    if (!extras.getString("selectedPlace").equals("")) {
                        prolight_address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }
                    prolight_zip.setText(extras.getString("zip"));
                    prolight_city.setText(extras.getString("city"));
                    prolight_State.setText(extras.getString("state"));
                    String servicearea=extras.getString("city")+","+extras.getString("state");
                    Logger.printMessage("ServiceArea-->",servicearea);
                    prolight_service_area.setText(servicearea);

                }

            }
        }
    }
    private void showDialogServies(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(getActivity());
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = getActivity().getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(getActivity()));

        custom = new CustomListAdapterDialog_catagory(getActivity(),PredictionsJsonArray, new onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_service.setText(value.getString("category_name"));
                    pros_contact_service = value.getString("id");
                    Logger.printMessage("id",pros_contact_service);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rcv_.setAdapter(custom);
        // some other visual settings
        popupWindow.setFocusable(false);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(dailogView);
        popupWindow.showAsDropDown(v, -5, 0);

    }
    public interface onOptionSelected {
        void onItemPassed(int position, JSONObject value);
    }

    public  void catagory()
    {
        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.catagory, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
               // Log.d("responese",result);
                try {
                    JSONObject job= new JSONObject(result);
                    catagory= job.getJSONArray("info_array");
                    Logger.printMessage("categoryArray", String.valueOf(catagory));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error, String response) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onStart() {

            }
        });

    }


    public void validation()
    {
        String businessname=prolight_businessname.getText().toString();
        String address=prolight_address.getText().toString();
        String city=prolight_city.getText().toString();
        String state=prolight_State.getText().toString();
        String zip=prolight_zip.getText().toString();
        String phonenumber=prolight_phone.getText().toString();
        String businessemail=prolight_email.getText().toString();
        String primaryservice=tv_service.getText().toString();
        String servicearea=prolight_service_area.getText().toString();


        if (prolight_businessname.getText().toString().trim().equals(""))
        {
            prolight_businessname.setError("Please enter Business name");
            prolight_businessname.setFocusable(true);
        }
        else
        {
            if (prolight_address.getText().toString().trim().equals(""))
            {
                prolight_address.setError("Please enter Address");
                prolight_address.setFocusable(true);
            }
            else
            {
                if (prolight_city.getText().toString().trim().equals(""))
                {
                    prolight_city.setError("Please enter City");
                    prolight_city.setFocusable(true);

                }
                else
                {
                    if (prolight_State.getText().toString().trim().equals(""))
                    {
                        prolight_State.setError("Please enter State ");
                        prolight_State.setFocusable(true);

                    }
                    else
                    {
                        if (prolight_zip.getText().toString().trim().equals(""))
                        {
                            prolight_zip.setError("Please enter zip");
                            prolight_State.setFocusable(true);
                        }
                        else
                        {
                            if (prolight_phone.getText().toString().trim().equals(""))
                            {
                                prolight_phone.setError("please enter Phone number");
                                prolight_phone.setFocusable(false);
                            }
                            else
                            {
                                if (prolight_email.getText().toString().trim().equals(""))
                                {
                                    prolight_email.setError("Please enter Business email ");
                                    prolight_email.setFocusable(true);
                                }
                                else
                                {
                                    if (tv_service.getText().toString().trim().equals(""))
                                    {
                                        tv_service.setError("Please enter Primary service");
                                        tv_service.setFocusable(true);
                                    }
                                    else
                                    {
                                        if (prolight_service_area.getText().toString().trim().equals(""))
                                        {
                                            prolight_service_area.setError("Please enter service area");
                                            prolight_service_area.setFocusable(true);
                                        }
                                        else
                                        {
                                            Logger.printMessage("fname", ProConstant.f_name);
                                            Logger.printMessage("lname",ProConstant.l_name);
                                            Logger.printMessage("email",ProConstant.email);
                                            Logger.printMessage("phone",ProConstant.phone);
                                            Logger.printMessage("password",ProConstant.password);
                                            Logger.printMessage("busimessname",businessname);
                                            Logger.printMessage("address",address);
                                            Logger.printMessage("city",city);
                                            Logger.printMessage("state",state);
                                            Logger.printMessage("zip",zip);
                                            Logger.printMessage("businessemail",businessemail);
                                            Logger.printMessage("phonenumber",phonenumber);
                                            Logger.printMessage("primaryservice",primaryservice);
                                            Logger.printMessage("servicearea",servicearea);
                                            Logger.printMessage("latittuted",ProConstant.latitude);
                                            Logger.printMessage("Logtitude",ProConstant.longtitude);
                                            Logger.printMessage("country",ProConstant.Country);

                                            HashMap<String,String> Params1=new HashMap<>();
                                            Params1.put("contact_f_name",ProConstant.f_name);
                                            Params1.put("contact_l_name",ProConstant.l_name);
                                            Params1.put("pro_email",ProConstant.email);
                                            Params1.put("pro_phone",ProConstant.phone);
                                            Params1.put("pro_password",ProConstant.password);
                                            Params1.put("com_name",businessname);
                                            Params1.put("com_address",address);
                                            Params1.put("city",city);
                                            Params1.put("state",state);
                                            Params1.put("zipcode",zip);
                                            Params1.put("country",ProConstant.Country);
                                            Params1.put("alt_phone",phonenumber);
                                            Params1.put("com_email",businessemail);
                                            Params1.put("primary_category",pros_contact_service);
                                            Params1.put("service_area",servicearea);
                                            Params1.put("latitude",ProConstant.latitude);
                                            Params1.put("longitude",ProConstant.longtitude);
                                            Params1.put("device_type","A");

                                            Logger.printMessage("PARAMS", String.valueOf(Params1));

                                            myload.showLoader();


                                            new CustomJSONParser().fireAPIForPostMethod(getActivity(),ProConstant.Signup,Params1,null, new CustomJSONParser.CustomJSONResponse() {
                                               @Override
                                               public void onSuccess(String result) {
                                                  myload.dismissLoader();
                                                   try {
                                                       JSONObject myob= new JSONObject(result);
                                                       boolean response= Boolean.parseBoolean(myob.getString("response"));
                                                       String messsage= myob.getString("message");

                                                           Toast.makeText(getActivity(), ""+messsage,Toast.LENGTH_SHORT).show();
                                                           Intent i=new Intent(getActivity(), SignupCompleteActivity.class);
                                                           startActivity(i);

                                                   } catch (JSONException e) {
                                                       e.printStackTrace();
                                                   }



                                               }

                                               @Override
                                               public void onError(String error, String response) {
                                                myload.dismissLoader();
                                                   try {
                                                       JSONObject job= new JSONObject(response);
                                                       String resp=job.getString("message");
                                                       Toast.makeText(getActivity(), ""+resp, Toast.LENGTH_SHORT).show();
                                                   } catch (JSONException e) {
                                                       e.printStackTrace();
                                                   }
                                               }

                                               @Override
                                               public void onError(String error) {
                                                 myload.dismissLoader();
                                               }

                                               @Override
                                               public void onStart() {

                                               }
                                           });

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void callbackForAlert(String result, int i) {

    }
}
