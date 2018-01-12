package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by su on 1/12/18.
 */

public class QuickReplyFragment extends Fragment {

    ProRegularEditText edt_description;
    ProRegularTextView tv_submit;
    CheckBox cb_autoreply;
    Toolbar toolbar;
    ArrayList<SetGetAPI> arrayList = null;
    MyLoader myload;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quick_reply, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_description = (ProRegularEditText)view. findViewById(R.id.edt_description);
        tv_submit = (ProRegularTextView) view.findViewById(R.id.tv_submit);
        cb_autoreply = (CheckBox)view. findViewById(R.id.cb_autoreply);
        myload = new MyLoader(getActivity());
        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);
        loadPage();
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickSave();
            }
        });
        cb_autoreply.setEnabled(false);

    }

    public void loadPage() {

        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.autoquickreply, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("response", result);
                try {
                    JSONObject mainResponseObj = new JSONObject(result);

                    JSONObject info_arry = mainResponseObj.getJSONObject("info_array");
                    String reply_enaable = info_arry.getString("reply_enabled");
                    String message = info_arry.getString("message");
                    if (reply_enaable.equalsIgnoreCase("1")) {

                        cb_autoreply.setChecked(true);
                        edt_description.setText(message);
                    } else {
                        cb_autoreply.setChecked(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();
                Logger.printMessage("error", response);
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                myload.dismissLoader();

                Logger.printMessage("error foe", error);

                CustomAlert customAlert = new CustomAlert();

                customAlert.getEventFromNormalAlert( getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
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

    public void quickSave() {

        if (cb_autoreply.isChecked()){

            if (edt_description.getText().toString().trim().equals("")) {
                edt_description.setError("Please enter the description");
                edt_description.requestFocus();
            } else {

                edt_description.setError(null);
                edt_description.clearFocus();

                Logger.printMessage("check_unceck", "" + cb_autoreply.isChecked());
                Logger.printMessage("uid", ProApplication.getInstance().getUserId());
                Logger.printMessage("desc", edt_description.getText().toString().trim());

                HashMap<String, String> Params = new HashMap<>();
                Params.put("user_id", ProApplication.getInstance().getUserId());

                try {
                    Params.put("message", URLEncoder.encode(edt_description.getText().toString().trim(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (cb_autoreply.isChecked()) {
                    Params.put("quick_reply_enabled", "1");
                } else {
                    Params.put("quick_reply_enabled", "0");
                }

                Logger.printMessage("PARAMS", String.valueOf(Params));

                new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.quicksave, Params, null, new CustomJSONParser.CustomJSONResponse() {
                    @Override
                    public void onSuccess(String result) {

                        myload.dismissLoader();
                        Logger.printMessage("response", result);

                        try {
                            JSONObject jo = new JSONObject(result);
                            String message = jo.getString("message");
                            Logger.printMessage("message", message);
                            Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            loadPage();
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
        }else {
            Toast.makeText(getActivity(),"Upgrade your account to Premium",Toast.LENGTH_SHORT).show();
        }
    }

}
