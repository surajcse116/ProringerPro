package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by su on 2/21/18.
 */

public class ProReplyReviewActivity extends AppCompatActivity {
    String review_reply_id = "";
    MyLoader myLoader = null;

    ProRegularEditText pro_review_description_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_review);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pro_review_description_text = (ProRegularEditText) findViewById(R.id.pro_review_description_text);

        myLoader = new MyLoader(ProReplyReviewActivity.this);

        if (getIntent().getExtras() != null) {
            review_reply_id = getIntent().getExtras().getString("review_reply_id");
        }

        findViewById(R.id.pro_reply_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = pro_review_description_text.getText().toString().trim();

                if ((text.length() >= 30)) {
//                    ((ProRegularEditText) findViewById(R.id.pro_review_description_text)).setError("Please enter report abuse description");
                    submitReviewReply();
                } else {
                    CustomAlert customAlert = new CustomAlert();
                    customAlert.getEventFromNormalAlert(ProReplyReviewActivity.this, "Report Abuse", "Please tell us more Must be over 30 characters.", "ok", "cancel", new CustomAlert.MyCustomAlertListener() {
                        @Override
                        public void callBackOk() {

                        }

                        @Override
                        public void callBackCancel() {

                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(GetStartedActivity.RESULT_CANCELED);
        finish();
    }

    public void submitReviewReply(){
        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id", ProApplication.getInstance().getUserId());
        Params.put("pro_id", ProApplication.getInstance().getUserId());
        Params.put("review_reply_id", review_reply_id);
        Params.put("report_comment", (((ProRegularEditText) findViewById(R.id.pro_review_description_text)).getText().toString().trim()));

        new CustomJSONParser().fireAPIForPostMethod(ProReplyReviewActivity.this, ProConstant.app_homeowner_replyreview, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {

                Logger.printMessage("result", result);

                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();
                try {
                    JSONObject jsonObject=new JSONObject(result);

                    CustomAlert customAlert = new CustomAlert();
                    customAlert.getOkEventFromNormalAlert(ProReplyReviewActivity.this, "Pros Review Reply", "" + jsonObject.getString("message"), new CustomAlert.MyCustomAlertListener() {
                        @Override
                        public void callBackOk() {
                            finish();
                        }

                        @Override
                        public void callBackCancel() {

                        }
                    });

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();


                CustomAlert customAlert = new CustomAlert();
                customAlert.getOkEventFromNormalAlert(ProReplyReviewActivity.this, "Pros Review Reply", "" + error, new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        finish();
                    }

                    @Override
                    public void callBackCancel() {

                    }
                });
            }

            @Override
            public void onError(String error) {
                if (myLoader != null && myLoader.isMyLoaderShowing())
                    myLoader.dismissLoader();


                CustomAlert customAlert = new CustomAlert();
                customAlert.getOkEventFromNormalAlert(ProReplyReviewActivity.this, "Pros Review Reply", "" + error, new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        finish();
                    }

                    @Override
                    public void callBackCancel() {

                    }
                });
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }
}
