package com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.CompanyProfileActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LocationActivity;
import com.android.llc.proringer.pro.proringerpro.activities.SignupCompleteActivity;
import com.android.llc.proringer.pro.proringerpro.activities.TermsPrivacyActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogCategory;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyCustomAlertListener;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProLightTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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

public class RegistrationTwoFragment extends Fragment implements MyCustomAlertListener {

    ProLightEditText edt_businessname, edt_city, edt_State, edt_zip, edt_phone, edt_email;
    ProRegularTextView protv_com, tv_service,tv_prolight_address,tv_service_area;
    public MyLoader myload = null;
    RelativeLayout RL_Service,RL_address,RL_Service_Area;
    JSONArray catagory;
    ProLightTextView terms_and_condition;
    PopupWindow popupWindow;
    CustomListAdapterDialogCategory custom = null;
    String pros_contact_service = "",latitude="",longitude="",countryCod="";
    int textLength = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        terms_and_condition= (ProLightTextView) view.findViewById(R.id.terms_and_condition);
        terms_and_condition.setMovementMethod(LinkMovementMethod.getInstance());

        String contactTextOne = "By singing up with ProRinger you agree with our  ";
        String contactTextClick = "Terms of Use";
        String contactTextTwo = " and ";
        String privacyPolicies = "Privacy Policy";

        myload = new MyLoader(getActivity());

        protv_com = (ProRegularTextView) view.findViewById(R.id.protv_com);
        edt_businessname = (ProLightEditText) view.findViewById(R.id.edt_businessname);
        tv_prolight_address = (ProRegularTextView) view.findViewById(R.id.tv_prolight_address);
        edt_city = (ProLightEditText) view.findViewById(R.id.edt_city);
        edt_State = (ProLightEditText) view.findViewById(R.id.edt_State);
        edt_zip = (ProLightEditText) view.findViewById(R.id.edt_zip);
        edt_phone = (ProLightEditText) view.findViewById(R.id.edt_phone);
        edt_email = (ProLightEditText) view.findViewById(R.id.edt_email);
        tv_service_area = (ProRegularTextView) view.findViewById(R.id.tv_service_area);
        tv_service = (ProRegularTextView) view.findViewById(R.id.tv_service);
        RL_Service = (RelativeLayout) view.findViewById(R.id.RL_Service);
        RL_Service_Area = (RelativeLayout) view.findViewById(R.id.RL_Service_Area);
        RL_address = (RelativeLayout) view.findViewById(R.id.RL_address);


        edt_phone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = edt_phone.getText().toString();
                textLength = edt_phone.getText().length();

                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;

                if (textLength == 1) {
                    if (!text.contains("(")) {
                        edt_phone.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                        edt_phone.setSelection(edt_phone.getText().length());
                    }

                } else if (textLength == 5) {

                    if (!text.contains(")")) {
                        edt_phone.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        edt_phone.setSelection(edt_phone.getText().length());
                    }

                } else if (textLength == 6) {
                    edt_phone.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    edt_phone.setSelection(edt_phone.getText().length());

                } else if (textLength == 10) {
                    if (!text.contains("-")) {
                        edt_phone.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        edt_phone.setSelection(edt_phone.getText().length());
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });


        edt_city.setEnabled(false);
        edt_city.setClickable(false);

        edt_State.setEnabled(false);
        edt_State.setClickable(false);

        edt_zip.setEnabled(false);
        edt_zip.setClickable(false);

//        prolight_service_area.setEnabled(false);
//        prolight_service_area.setClickable(false);

        category();

        RL_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RL_Service.setBackgroundResource(R.drawable.background_solidorange_border);
                showDialogServices(view, catagory);
            }
        });

        RL_Service_Area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LocationActivity.class);
                startActivityForResult(i, 2);
            }
        });

        protv_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        tv_prolight_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LocationActivity.class);
                startActivityForResult(i, 1000);
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

                Intent intentTerms = new Intent(getActivity(), TermsPrivacyActivity.class);
                intentTerms.putExtra("value", "term");
                startActivity(intentTerms);
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

                Intent intentPolicy = new Intent(getActivity(), TermsPrivacyActivity.class);
                intentPolicy.putExtra("value", "policy");
                startActivity(intentPolicy);
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

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle extras = data.getBundleExtra("data");
                if (extras != null) {

                    Logger.printMessage("selectedPlace", "--->" + extras.getString("selectedPlace"));
                    Logger.printMessage("zip", "--->" + extras.getString("zip"));
                    Logger.printMessage("city", "--->" + extras.getString("city"));
                    Logger.printMessage("state", "--->" + extras.getString("state"));

                    if (!extras.getString("selectedPlace").equals("")) {
                        tv_prolight_address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }
                    edt_zip.setText(extras.getString("zip"));
                    edt_city.setText(extras.getString("city"));
                    edt_State.setText(extras.getString("state"));
                    String servicearea = extras.getString("city") + ", " + extras.getString("state");
                    Logger.printMessage("ServiceArea-->", servicearea);
                    tv_service_area.setText(servicearea);


                    latitude = extras.getString("lattitude");
                    longitude = extras.getString("longttitude");
                    getAddress(Double.valueOf(latitude), Double.valueOf(longitude));
                }
            }
        }
        if (requestCode==2){
            if (resultCode == Activity.RESULT_OK) {

                Bundle extras = data.getBundleExtra("data");
                if (extras != null) {

                    Logger.printMessage("city", "--->" + extras.getString("city"));
                    Logger.printMessage("state", "--->" + extras.getString("state"));

                    if (!extras.getString("selectedPlace").equals("")) {
                        tv_prolight_address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }

                    String servicearea = extras.getString("city") + ", " + extras.getString("state");
                    Logger.printMessage("ServiceArea-->", servicearea);
                    tv_service_area.setText(servicearea);
                }
            }
        }
    }

    private void showDialogServices(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(getActivity());
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = getActivity().getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(getActivity()));

        custom = new CustomListAdapterDialogCategory(getActivity(), PredictionsJsonArray, new onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_service.setText(value.getString("category_name"));
                    pros_contact_service = value.getString("id");
                    Logger.printMessage("id", pros_contact_service);
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

    public void category() {
        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_categorylist, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                // Logger.printMessage("responese",result);
                try {
                    JSONObject job = new JSONObject(result);
                    catagory = job.getJSONArray("info_array");
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


    public void validation() {
        if (edt_businessname.getText().toString().trim().equals("")) {
            edt_businessname.setError("Please enter Business name");
            edt_businessname.requestFocus();
        } else {

            edt_businessname.setError(null);
            edt_businessname.clearFocus();

            if (tv_prolight_address.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(),"Please choose Address",Toast.LENGTH_SHORT).show();
            } else {
                if (edt_city.getText().toString().trim().equals("")) {
                    edt_city.setError("Please enter City");
                    edt_city.requestFocus();

                } else {

                    edt_city.setError(null);
                    edt_city.clearFocus();

                    if (edt_State.getText().toString().trim().equals("")) {
                        edt_State.setError("Please enter State ");
                        edt_State.requestFocus();

                    } else {
                        edt_State.setError(null);
                        edt_State.clearFocus();

                        if (edt_zip.getText().toString().trim().equals("")) {
                            edt_zip.setError("Please enter zip");
                            edt_zip.requestFocus();
                        } else {
                            edt_zip.setError(null);
                            edt_zip.clearFocus();

                            if (edt_phone.getText().toString().trim().equals("")) {
                                edt_phone.setError("please enter Phone number");
                                edt_phone.requestFocus();
                            } else {
                                edt_phone.setError(null);
                                edt_phone.clearFocus();

                                if (edt_email.getText().toString().trim().equals("")) {
                                    edt_email.setError("Please enter Business email ");
                                    edt_email.requestFocus();
                                } else {
                                    edt_email.setError(null);
                                    edt_email.clearFocus();

                                    if (MethodsUtils.isValidEmail(edt_email.getText().toString().trim()))
                                    {
                                        edt_email.setError(null);
                                        edt_email.clearFocus();

                                        if (tv_service.getText().toString().trim().equals("")) {
                                            Toast.makeText(getActivity(),"Please select Primary service",Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (tv_service_area.getText().toString().trim().equals("")) {
                                                Toast.makeText(getActivity(),"Please choose Service Area",Toast.LENGTH_SHORT).show();
                                            } else {
                                                Logger.printMessage("fname", ProConstant.f_name);
                                                Logger.printMessage("lname", ProConstant.l_name);
                                                Logger.printMessage("email", ProConstant.email);
                                                Logger.printMessage("phone", ProConstant.phone);
                                                Logger.printMessage("password", ProConstant.password);
                                                Logger.printMessage("busimessname", edt_businessname.getText().toString().trim());
                                                Logger.printMessage("address", tv_prolight_address.getText().toString().trim());
                                                Logger.printMessage("city", edt_city.getText().toString().trim());
                                                Logger.printMessage("state", edt_State.getText().toString().trim());
                                                Logger.printMessage("zip", edt_zip.getText().toString().trim());
                                                Logger.printMessage("businessemail", edt_email.getText().toString().trim());
                                                Logger.printMessage("phonenumber", edt_phone.getText().toString().trim());
                                                Logger.printMessage("primaryservice", tv_service.getText().toString().trim());
                                                Logger.printMessage("servicearea", tv_service_area.getText().toString().trim());
                                                Logger.printMessage("latitude", latitude);
                                                Logger.printMessage("longitude", longitude);
                                                Logger.printMessage("country",countryCod );

                                                HashMap<String, String> Params = new HashMap<>();
                                                Params.put("contact_f_name", ProConstant.f_name);
                                                Params.put("contact_l_name", ProConstant.l_name);
                                                Params.put("pro_email", ProConstant.email);
                                                Params.put("pro_phone", ProConstant.phone);
                                                Params.put("pro_password", ProConstant.password);


                                                Params.put("com_name", edt_businessname.getText().toString().trim());
                                                Params.put("com_address", tv_prolight_address.getText().toString().trim());
                                                Params.put("city", edt_city.getText().toString().trim());
                                                Params.put("state", edt_State.getText().toString().trim());
                                                Params.put("zipcode", edt_zip.getText().toString().trim());

                                                Params.put("alt_phone", edt_phone.getText().toString().trim());
                                                Params.put("com_email", edt_email.getText().toString().trim());
                                                Params.put("primary_category", pros_contact_service);


                                                Params.put("service_area", tv_service_area.getText().toString().trim());
                                                Params.put("latitude",latitude );
                                                Params.put("longitude",longitude);
                                                Params.put("country",countryCod);
                                                Params.put("device_type", "A");

                                                Logger.printMessage("PARAMS", String.valueOf(Params));

                                                myload.showLoader();


                                                new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_signup, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                                    @Override
                                                    public void onSuccess(String result) {
                                                        myload.dismissLoader();
                                                        try {
                                                            JSONObject myob = new JSONObject(result);
                                                            boolean response = Boolean.parseBoolean(myob.getString("response"));
                                                            String messsage = myob.getString("message");

                                                            Toast.makeText(getActivity(), "" + messsage, Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(getActivity(), SignupCompleteActivity.class);
                                                            startActivity(i);
                                                            getActivity().finish();

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(String error, String response) {
                                                        myload.dismissLoader();
                                                        try {
                                                            JSONObject job = new JSONObject(response);
                                                            String resp = job.getString("message");
                                                            Toast.makeText(getActivity(), "" + resp, Toast.LENGTH_SHORT).show();
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
                                    }else {
                                        edt_email.setError("Please enter Valid Business email ");
                                        edt_email.requestFocus();
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

    public void getAddress(Double lat, Double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address obj = addresses.get(0);
        String add = obj.getAddressLine(0);
        String currentAddress = obj.getSubAdminArea() + ","
                + obj.getAdminArea();
        String latitude = String.valueOf(obj.getLatitude());
        String longitude = String.valueOf(obj.getLongitude());
        String currentCity = obj.getSubAdminArea();
        String currentState = obj.getAdminArea();
        add = add + "\n" + obj.getCountryName();
        add = add + "\n" + obj.getCountryCode();
        countryCod = obj.getCountryCode();
        Logger.printMessage("countrycod", countryCod);
        add = add + "\n" + obj.getAdminArea();
        add = add + "\n" + obj.getPostalCode();
        add = add + "\n" + obj.getSubAdminArea();
        add = add + "\n" + obj.getLocality();
        add = add + "\n" + obj.getSubThoroughfare();

        Log.v("IGA", "Address" + add);
        // Toast.makeText(this, "Address=>" + add,
        // Toast.LENGTH_SHORT).show();

        // TennisAppActivity.showDialog(add);

    }
}
