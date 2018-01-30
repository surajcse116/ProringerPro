package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetVerificationFirstFragment extends Fragment {
    ProLightEditText et_confirmphoneno;
    ProRegularTextView send_now, ph_field;
    int textLength = 0;
    String text;
    String user_phone_number;
    ArrayList<SetGetAPIPostData> arrayList = null;
    CheckBox cb;
    MyLoader myload;

    public GetVerificationFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_verification_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        send_now = (ProRegularTextView) view.findViewById(R.id.send_now);
        et_confirmphoneno = view.findViewById(R.id.et_confirmphoneno);
        cb = (CheckBox) view.findViewById(R.id.cb);
        myload = new MyLoader(getActivity());
        ProRegularTextView tv3 = (ProRegularTextView) view.findViewById(R.id.tv3);
        ProRegularTextView tv4 = (ProRegularTextView) view.findViewById(R.id.tv4);
        ph_field = (ProRegularTextView) view.findViewById(R.id.ph_field);
        ProRegularTextView txt_note = (ProRegularTextView) view.findViewById(R.id.txt_note);

        getUserInfoList();

        ((GetVerificationActivity) getActivity()).increaseStep();

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb.isChecked())
                {
                    send_now.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else
                {
                    send_now.setBackgroundColor(getResources().getColor(R.color.colorDarkGray));
                }
            }
        });

        et_confirmphoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text = et_confirmphoneno.getText().toString();
                textLength = et_confirmphoneno.getText().length();

                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;

                if (textLength == 1) {
                    if (!text.contains("(")) {
                        et_confirmphoneno.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                        et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());
                    }

                } else if (textLength == 5) {

                    if (!text.contains(")")) {
                        et_confirmphoneno.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());
                    }

                } else if (textLength == 6) {
                    et_confirmphoneno.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());

                } else if (textLength == 10) {
                    if (!text.contains("-")) {
                        et_confirmphoneno.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        send_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb.isChecked()) {
                    if (!et_confirmphoneno.getText().toString().trim().equals("") && et_confirmphoneno.getText().toString().trim().length() < 14) {
                        et_confirmphoneno.setError("enter correct us format phone number");
                        et_confirmphoneno.requestFocus();
                    } else if (et_confirmphoneno.getText().toString().trim().length() == 14) {
                        callProVerifiedNumber();
                    }
                    else if (et_confirmphoneno.getText().toString().trim().equals("")){
                        ((GetVerificationActivity) getActivity()).callVerificationFragments(2);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv4.setText(getResources().getString(R.string.get_verified_data));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // only for gingerbread and newer versions

            tv3.setText(" We will send this code to the number we have on file:");
            // txt_note.setText(getResources().getString(R.string.get_verified_data));
            txt_note.setText(Html.fromHtml(getResources().getString(R.string.welcome_text), Html.FROM_HTML_MODE_COMPACT));
        } else {
//            tv3.setText(Html.fromHtml(getResources().getString(R.string.get_verified_data2)+" "+"<b>(111) 111-2222</b>"));
            //tv3.setText(Html.fromHtml(getResources().getString(R.string.get_verified_data2)));
            tv3.setText(" We will send this code to the number we have on file:");
            // txt_note.setText(getResources().getString(R.string.get_verified_data));
            txt_note.setText(Html.fromHtml(getResources().getString(R.string.welcome_text)));
        }
    }

    private void callProVerifiedNumber() {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", ProApplication.getInstance().getUserId());
        params.put("pros_ph_no", et_confirmphoneno.getText().toString().trim());
        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_verified_number, params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("onSuccess", "onSuccess");
                Logger.printMessage("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("response").equals("true")) {
                        ((GetVerificationActivity) getActivity()).phoneNumberPinSent=et_confirmphoneno.getText().toString().trim();
                        ((GetVerificationActivity) getActivity()).callVerificationFragments(2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                Logger.printMessage("onError", response);
                myload.dismissLoader();
                try {
                    Toast.makeText(getActivity(), new JSONObject(response).getString("message"), Toast.LENGTH_SHORT).show();
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
                Logger.printMessage("onStart", "onStart");
                myload.showLoader();
            }
        });

    }


    private void getUserInfoList() {
        arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);
        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_prouserinfo_list, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {

                myload.dismissLoader();

                Logger.printMessage("response_phonenumber", result);
                try {
                    JSONObject prouserInfojobj = new JSONObject(result);
                    JSONArray infoarr = prouserInfojobj.getJSONArray("info_array");
                    for (int i = 0; i < infoarr.length(); i++) {
                        JSONObject jo = infoarr.getJSONObject(i);
                        user_phone_number = jo.getString("phone");
                        Logger.printMessage("ph_no", user_phone_number);
                        ph_field.setText(user_phone_number);
                        ((GetVerificationActivity)getActivity()).phoneNumberPinSent=user_phone_number;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();
            }

            @Override
            public void onError(String error) {
                myload.dismissLoader();
            }

            @Override
            public void onStart() {
                myload.showLoader();
            }
        });
    }


}
