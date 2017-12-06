package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.Constant.AppConstant;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LicenceActivity;
import com.android.llc.proringer.pro.proringerpro.helper.Appdata;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.MyCustomAlertListener;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.APIGetData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by su on 9/4/17.
 */

public class SocialMediaFragment extends Fragment {


    ProLightEditText et_facebook, et_twit, et_google, et_in, et_youtube, et_insta, et_sky, et_prin;
    ProRegularTextView save;
    ArrayList<APIGetData> arrayList = null;
    MyLoader myload;
    String paypal = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social_media, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_facebook = (ProLightEditText) view.findViewById(R.id.et_facebook);
        et_twit = (ProLightEditText) view.findViewById(R.id.et_twit);
        et_google = (ProLightEditText) view.findViewById(R.id.et_google);
        et_in = (ProLightEditText) view.findViewById(R.id.et_youtube);
        et_youtube = (ProLightEditText) view.findViewById(R.id.et_youtube);
        et_insta = (ProLightEditText) view.findViewById(R.id.et_insta);
        et_sky = (ProLightEditText) view.findViewById(R.id.et_sky);
        et_prin = (ProLightEditText) view.findViewById(R.id.et_prin);
        save = (ProRegularTextView) view.findViewById(R.id.save);
        myload = new MyLoader(getActivity());
        showdata();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

    }

    public void validation() {
        final String face = et_facebook.getText().toString().trim();
        final String twit = et_twit.getText().toString().trim();
        final String google = et_google.getText().toString().trim();
        final String ink = et_in.getText().toString().toString().trim();
        final String youtube = et_youtube.getText().toString().trim();
        final String insta = et_insta.getText().toString().trim();
        final String sky = et_sky.getText().toString().trim();
        final String print = et_prin.getText().toString().trim();

        if (face.equals("")) {
            et_facebook.setError("Please fill the link");
            et_facebook.setFocusable(true);
        } else {
            if (twit.equals("")) {
                et_twit.setError("Please fill the link");
                et_twit.setFocusable(true);
            } else {
                if (google.equals("")) {
                    et_google.setError("Please fill the link");
                    et_google.setFocusable(true);
                } else {
                    if (ink.equals("")) {
                        et_in.setError("Please fill the link");
                        et_in.setFocusable(true);
                    } else {
                        if (youtube.equals("")) {
                            et_youtube.setError("Please fill the link");
                            et_youtube.setFocusable(true);
                        } else {
                            if (print.equals("")) {
                                et_prin.setError("Please fill the link");
                                et_prin.setFocusable(true);
                            } else {
                                if (insta.equals("")) {
                                    et_insta.setError("Please fill the link");
                                    et_insta.setFocusable(true);
                                } else {
                                    if (sky.equals("")) {
                                        et_sky.setError("Please fill the link");
                                        et_sky.setFocusable(true);
                                    } else {
                                        Log.d("facebook", face);
                                        Log.d("twit", twit);
                                        Log.d("google", google);
                                        Log.d("ink", ink);
                                        Log.d("youtube", youtube);
                                        Log.d("insta", insta);
                                        Log.d("sky", sky);
                                        Log.d("print", print);
                                        Log.d("paypal_link", Appdata.paypal);
                                        HashMap<String, String> Params = new HashMap<>();
                                        Params.put("user_id", ProApplication.getInstance().getUserId());
                                        Params.put("fblink", face);
                                        Params.put("twitterlink", twit);
                                        Params.put("googlepluslink", google);
                                        Params.put("linkedinlink", ink);
                                        Params.put("youtubelink", youtube);
                                        Params.put("pinterestlink", print);
                                        Params.put("instagramlink", insta);
                                        Params.put("skypelink", sky);
                                        Params.put("paypallink", Appdata.paypal);
                                        new CustomJSONParser().fireAPIForPostMethod(getActivity(), AppConstant.save, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                            @Override
                                            public void onSuccess(String result) {
                                                myload.dismissLoader();
                                                Log.d("result", result);
                                                try {

                                                    JSONObject info = new JSONObject(result);
                                                    String message = info.getString("message");
                                                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();


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

                                                CustomAlert customAlert = new CustomAlert();
                                                customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
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
                            }
                        }
                    }
                }
            }
        }

    }

    public void showdata()

    {

        arrayList = new ArrayList<APIGetData>();
        APIGetData apiGetData = new APIGetData();
        apiGetData.setPARAMS("user_id");
        apiGetData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(apiGetData);
        Log.d("hgdgfd", String.valueOf(arrayList));
        new CustomJSONParser().fireAPIForGetMethod(getActivity(), AppConstant.socialmedia, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Log.d("result", result);
                try {
                    JSONObject info = new JSONObject(result);
                    JSONObject jo = info.getJSONObject("info_array");
                    Log.d("info", String.valueOf(jo));
                    et_facebook.setText(jo.getString("fb_link"));
                    et_twit.setText(jo.getString("twt_link"));
                    et_google.setText(jo.getString("gp+_link"));
                    et_in.setText(jo.getString("lnkin_link"));
                    et_youtube.setText(jo.getString("youtb_link"));
                    et_prin.setText(jo.getString("pintrst_link"));
                    et_insta.setText(jo.getString("instgm_link"));
                    et_sky.setText(jo.getString("skype_link"));
                    Appdata.paypal = jo.getString("paypal_link");

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
                customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
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