package com.android.llc.proringer.pro.proringerpro.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 7/27/17.
 */

public class TermsPrivacyActivity extends AppCompatActivity {
    MyLoader myLoader = null;
    ProRegularTextView tv_title;
    LinearLayout main_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_privacy);

        String HeaderString = getIntent().getStringExtra("value");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myLoader = new MyLoader(TermsPrivacyActivity.this);

        main_container = (LinearLayout) findViewById(R.id.main_container);

        tv_title = (ProRegularTextView) findViewById(R.id.tv_title);

        if (HeaderString.equalsIgnoreCase("term")) {
            tv_title.setText("Terms of Use");
            loadDataTermsOfUse();
        } else {
            tv_title.setText("Privacy Policy");
            loadPrivacyPolicy();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadDataTermsOfUse() {

        new CustomJSONParser().fireAPIForGetMethod(TermsPrivacyActivity.this, ProConstant.app_term, new ArrayList<SetGetAPI>(), new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                Logger.printMessage("message", "" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv = new TextView(TermsPrivacyActivity.this);
                    tv.setLayoutParams(lparams);

                    String textValue = jsonObject.getString("page_content");

                    Logger.printMessage("lastIndexOfContactUs", "-->" + textValue.lastIndexOf("If you have questions"));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tv.setText(Html.fromHtml(textValue.substring(0, textValue.lastIndexOf("If you have questions")), Html.FROM_HTML_MODE_LEGACY));
                        tv.append("If you have questions, please");
                        ClickableSpan resentClickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // There is the OnCLick. put your intent to Register class here
                                widget.invalidate();
                                Logger.printMessage("ClickOnContactUs","-->Yes");
                                String[] TOSuppory = {"admin@proringer.com"};
                                Uri uriSupport = Uri.parse("mailto:admin@proringer.com")
                                        .buildUpon()
                                        .appendQueryParameter("subject", "Support")
                                        .appendQueryParameter("body", "I think \n \n \n Proringer mobile app v1.0.1")
                                        .build();
                                Intent emailSupportIntent = new Intent(Intent.ACTION_SENDTO, uriSupport);
                                emailSupportIntent.putExtra(Intent.EXTRA_EMAIL, TOSuppory);
                                startActivity(Intent.createChooser(emailSupportIntent, "Send mail..."));
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(getResources().getColor(R.color.colorAccent));
                                ds.setUnderlineText(false);
                            }
                        };
                        String contact_us = " contact us.";
                        Spannable resentWordClick = new SpannableString(contact_us);
                        resentWordClick.setSpan(resentClickableSpan, 0, contact_us.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.append(resentWordClick);

                    } else {
                        tv.setText(Html.fromHtml(textValue.substring(0, textValue.lastIndexOf("If you have questions"))));
                        tv.append("If you have questions, please");
                        ClickableSpan resentClickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // There is the OnCLick. put your intent to Register class here
                                widget.invalidate();

                                widget.invalidate();
                                Logger.printMessage("ClickOnContactUs","-->Yes");
                                String[] TOSuppory = {"admin@proringer.com"};
                                Uri uriSupport = Uri.parse("mailto:admin@proringer.com")
                                        .buildUpon()
                                        .appendQueryParameter("subject", "Support")
                                        .appendQueryParameter("body", "I think \n \n \n Proringer mobile app v1.0.1")
                                        .build();
                                Intent emailSupportIntent = new Intent(Intent.ACTION_SENDTO, uriSupport);
                                emailSupportIntent.putExtra(Intent.EXTRA_EMAIL, TOSuppory);
                                startActivity(Intent.createChooser(emailSupportIntent, "Send mail..."));
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(getResources().getColor(R.color.colorAccent));
                                ds.setUnderlineText(false);
                            }
                        };
                        String contact_us = " contact us.";
                        Spannable resentWordClick = new SpannableString(contact_us);
                        resentWordClick.setSpan(resentClickableSpan, 0, contact_us.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.append(resentWordClick);
                    }

                    main_container.addView(tv);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                new CustomAlert().getOkEventFromNormalAlert(TermsPrivacyActivity.this, "Terms Of Use", "" + error, new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }

                    @Override
                    public void callBackCancel() {

                    }
                });

                if (error.equalsIgnoreCase(getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection))) {
                    findViewById(R.id.ScrollViewMAin).setVisibility(View.GONE);
                    findViewById(R.id.LLNetworkDisconnection).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }

    public void loadPrivacyPolicy() {

        new CustomJSONParser().fireAPIForGetMethod(TermsPrivacyActivity.this, ProConstant.app_privacy_policy, new ArrayList<SetGetAPI>(), new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                Logger.printMessage("message", "" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv = new TextView(TermsPrivacyActivity.this);
                    tv.setLayoutParams(lparams);

                    String textValue = jsonObject.getString("page_content");

                    Logger.printMessage("lastIndexOfContactUs", "-->" + textValue.lastIndexOf("If you have any questions or suggestions regarding our Privacy Policy"));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tv.setText(Html.fromHtml(textValue.substring(0, textValue.lastIndexOf("If you have any questions or suggestions regarding our Privacy Policy")), Html.FROM_HTML_MODE_LEGACY));
                        tv.append("If you have any questions or suggestions regarding our Privacy Policy, please");
                        ClickableSpan resentClickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // There is the OnCLick. put your intent to Register class here
                                widget.invalidate();
                                Logger.printMessage("ClickOnContactUs","-->Yes");
                                String[] TOSuppory = {"admin@proringer.com"};
                                Uri uriSupport = Uri.parse("mailto:admin@proringer.com")
                                        .buildUpon()
                                        .appendQueryParameter("subject", "Support")
                                        .appendQueryParameter("body", "I think \n \n \n Proringer mobile app v1.0.1")
                                        .build();
                                Intent emailSupportIntent = new Intent(Intent.ACTION_SENDTO, uriSupport);
                                emailSupportIntent.putExtra(Intent.EXTRA_EMAIL, TOSuppory);
                                startActivity(Intent.createChooser(emailSupportIntent, "Send mail..."));
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(getResources().getColor(R.color.colorAccent));
                                ds.setUnderlineText(false);
                            }
                        };
                        String contact_us = " contact us.";
                        Spannable resentWordClick = new SpannableString(contact_us);
                        resentWordClick.setSpan(resentClickableSpan, 0, contact_us.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.append(resentWordClick);

                    } else {
                        tv.setText(Html.fromHtml(textValue.substring(0, textValue.lastIndexOf("If you have any questions or suggestions regarding our Privacy Policy"))));
                        tv.append("If you have any questions or suggestions regarding our Privacy Policy, please");
                        ClickableSpan resentClickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // There is the OnCLick. put your intent to Register class here
                                widget.invalidate();

                                widget.invalidate();
                                Logger.printMessage("ClickOnContactUs","-->Yes");
                                String[] TOSuppory = {"admin@proringer.com"};
                                Uri uriSupport = Uri.parse("mailto:admin@proringer.com")
                                        .buildUpon()
                                        .appendQueryParameter("subject", "Support")
                                        .appendQueryParameter("body", "I think \n \n \n Proringer mobile app v1.0.1")
                                        .build();
                                Intent emailSupportIntent = new Intent(Intent.ACTION_SENDTO, uriSupport);
                                emailSupportIntent.putExtra(Intent.EXTRA_EMAIL, TOSuppory);
                                startActivity(Intent.createChooser(emailSupportIntent, "Send mail..."));
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(getResources().getColor(R.color.colorAccent));
                                ds.setUnderlineText(false);
                            }
                        };
                        String contact_us = " contact us.";
                        Spannable resentWordClick = new SpannableString(contact_us);
                        resentWordClick.setSpan(resentClickableSpan, 0, contact_us.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.append(resentWordClick);
                    }

                    main_container.addView(tv);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();

                new CustomAlert().getOkEventFromNormalAlert(TermsPrivacyActivity.this, "Privacy Policy", "" + error, new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }

                    @Override
                    public void callBackCancel() {

                    }
                });

                if (error.equalsIgnoreCase(getResources().getString(R.string.no_internet_connection_found_Please_check_your_internet_connection))) {
                    findViewById(R.id.ScrollViewMAin).setVisibility(View.GONE);
                    findViewById(R.id.LLNetworkDisconnection).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }
}
