package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
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


    ProLightEditText et_facebook, et_twitter, et_google, et_linkedin, et_youtube,et_pinterest, et_instagram, et_skype;
    ProRegularTextView tv_save;
    ArrayList<SetGetAPI> arrayList = null;
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
        et_twitter = (ProLightEditText) view.findViewById(R.id.et_twitter);
        et_google = (ProLightEditText) view.findViewById(R.id.et_google);
        et_linkedin = (ProLightEditText) view.findViewById(R.id.et_linkedin);
        et_youtube = (ProLightEditText) view.findViewById(R.id.et_youtube);
        et_pinterest = (ProLightEditText) view.findViewById(R.id.et_pinterest);
        et_instagram = (ProLightEditText) view.findViewById(R.id.et_instagram);
        et_skype = (ProLightEditText) view.findViewById(R.id.et_skype);


        tv_save = (ProRegularTextView) view.findViewById(R.id.tv_save);

        myload = new MyLoader(getActivity());

        showData();

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

    }

    public void validation() {

        if (et_facebook.getText().toString().trim().equals("")) {
            et_facebook.setError("Please fill the facebook link");
            et_facebook.requestFocus();
        } else {
            if (!facebookUrlCheck(et_facebook.getText().toString().trim())) {
                et_facebook.setError("Please fill correct facebook link");
               et_facebook.requestFocus();
            } else {
                et_facebook.setError(null);//removes error
                et_facebook.clearFocus();
                if (et_twitter.getText().toString().trim().equals("")) {
                    et_twitter.setError("Please fill the twitter link");
                    et_twitter.requestFocus();
                } else {
                    if (!twitterUrlCheck(et_twitter.getText().toString().trim())) {
                        et_twitter.setError("Please fill correct twitter link");
                        et_twitter.requestFocus();
                    } else {
                        et_twitter.setError(null);//removes error
                        et_twitter.clearFocus();
                        if (et_google.getText().toString().trim().equals("")) {
                            et_google.setError("Please fill the google link");
                            et_google.requestFocus();
                        } else {
                            if (!googlePlusUrlCheck(et_google.getText().toString().trim())) {
                                et_google.setError("Please fill correct google link");
                                et_google.requestFocus();
                            } else {
                                et_google.setError(null);//removes error
                                et_google.clearFocus();
                                if (et_linkedin.getText().toString().trim().equals("")) {
                                    et_linkedin.setError("Please fill the linkedin link");
                                    et_linkedin.requestFocus();
                                } else {
                                    if (!linkedInUrlCheck(et_linkedin.getText().toString().trim())) {
                                        et_linkedin.setError("Please fill correct linkedin link");
                                        et_linkedin.requestFocus();
                                    } else {
                                        et_linkedin.setError(null);//removes error
                                        et_linkedin.clearFocus();
                                        if (et_youtube.getText().toString().trim().equals("")) {
                                            et_youtube.setError("Please fill the link");
                                            et_youtube.requestFocus();
                                        } else {
                                            if (!youtubeUrlCheck(et_youtube.getText().toString().trim())) {
                                                et_youtube.setError("Please fill correct youTube link");
                                                et_youtube.requestFocus();
                                            } else {
                                                et_youtube.setError(null);//removes error
                                                et_youtube.clearFocus();
                                                if (et_pinterest.getText().toString().trim().equals("")) {
                                                    et_pinterest.setError("Please fill the pinterest link");
                                                    et_pinterest.requestFocus();
                                                } else {
                                                    if(!pinterestUrlCheck(et_pinterest.getText().toString().trim())){
                                                        et_pinterest.setError("Please fill correct pinterest link");
                                                        et_pinterest.requestFocus();
                                                    }else {
                                                        et_pinterest.setError(null);//removes error
                                                        et_pinterest.clearFocus();
                                                        if (et_instagram.getText().toString().trim().equals("")) {
                                                            et_instagram.setError("Please fill the instagram link");
                                                            et_instagram.requestFocus();
                                                        } else {
                                                            if (!instagramtUrlCheck(et_instagram.getText().toString().trim())){
                                                                et_instagram.setError("Please fill correct instagram link");
                                                                et_instagram.requestFocus();
                                                            }else {
                                                                et_instagram.setError(null);//removes error
                                                                et_instagram.clearFocus();
                                                                if (et_skype.getText().toString().trim().equals("")) {
                                                                    et_skype.setError("Please fill the link");
                                                                    et_skype.requestFocus();
                                                                } else {
                                                                    et_skype.setError(null);//removes error
                                                                    et_skype.clearFocus();
                                                                    submitData();
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
                        }
                    }
                }
            }
        }
    }

    public void showData() {

        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);
        Logger.printMessage("hgdgfd", String.valueOf(arrayList));
        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.socialmedia, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("result", result);
                try {
                    JSONObject info = new JSONObject(result);
                    JSONObject jo = info.getJSONObject("info_array");
                    Logger.printMessage("info", String.valueOf(jo));
                    et_facebook.setText(jo.getString("fb_link"));
                    et_twitter.setText(jo.getString("twt_link"));
                    et_google.setText(jo.getString("gp+_link"));
                    et_linkedin.setText(jo.getString("lnkin_link"));
                    et_youtube.setText(jo.getString("youtb_link"));
                    et_pinterest.setText(jo.getString("pintrst_link"));
                    et_instagram.setText(jo.getString("instgm_link"));
                    et_skype.setText(jo.getString("skype_link"));
                    paypal = jo.getString("paypal_link");

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

    public void submitData(){
        Logger.printMessage("facebook", et_facebook.getText().toString().trim());
        Logger.printMessage("twitter", et_twitter.getText().toString().trim());
        Logger.printMessage("google", et_google.getText().toString().trim());
        Logger.printMessage("ink", et_linkedin.getText().toString().trim());
        Logger.printMessage("youtube", et_youtube.getText().toString().trim());
        Logger.printMessage("pinterest", et_pinterest.getText().toString().trim());
        Logger.printMessage("instagram", et_instagram.getText().toString().trim());
        Logger.printMessage("sky", et_skype.getText().toString().trim());
        Logger.printMessage("paypal_link", paypal);

        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id", ProApplication.getInstance().getUserId());
        Params.put("fblink", et_facebook.getText().toString().trim());
        Params.put("twitterlink", et_twitter.getText().toString().trim());
        Params.put("googlepluslink", et_google.getText().toString().trim());
        Params.put("linkedinlink", et_linkedin.getText().toString().trim());
        Params.put("youtubelink", et_youtube.getText().toString().trim());
        Params.put("pinterestlink", et_pinterest.getText().toString().trim());
        Params.put("instagramlink", et_instagram.getText().toString().trim());
        Params.put("skypelink", et_skype.getText().toString().trim());
        Params.put("paypallink", paypal);
        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.save, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("result", result);
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

    public boolean facebookUrlCheck(String someURL) {
        String regex = "((http|https)://)?(www[.])?facebook.com/.+";
        return someURL.matches(regex);
    }

    public boolean twitterUrlCheck(String someURL) {
        String regex = "((http|https)://)?(www[.])?twitter.com/.+";
        return someURL.matches(regex);
    }

    public boolean googlePlusUrlCheck(String someURL) {
        String regex = "((http|https)://)?(www[.])?plus.google.com/.+";
        return someURL.matches(regex);
    }

    public boolean linkedInUrlCheck(String someURL) {
        String regex = "((http|https)://)?(www[.])?linkedin.com/.+";
        return someURL.matches(regex);
    }

    public boolean youtubeUrlCheck(String someURL) {
        String regex = "((http|https)://)?(www[.])?youtube.com/.+";
        return someURL.matches(regex);
    }

    public boolean pinterestUrlCheck(String someURL) {
        String regex = "((http|https)://)?(www[.])?pinterest.com/.+";
        return someURL.matches(regex);
    }

    public boolean instagramtUrlCheck(String someURL) {
        String regex = "((http|https)://)?(www[.])?instagram.com/.+";
        return someURL.matches(regex);
    }
}