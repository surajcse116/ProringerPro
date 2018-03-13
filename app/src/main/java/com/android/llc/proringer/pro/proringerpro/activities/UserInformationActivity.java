package com.android.llc.proringer.pro.proringerpro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
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

public class UserInformationActivity extends AppCompatActivity{

    ProLightEditText et_fname,et_lname,et_title,et_phone;
    ProRegularTextView tv_save_information;
    ArrayList<SetGetAPIPostData> arrayList=null;
    MyLoader myload;
    int textLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        et_fname=(ProLightEditText) findViewById(R.id.et_fname);
        et_lname=(ProLightEditText) findViewById(R.id.et_lname);
        et_title=(ProLightEditText) findViewById(R.id.et_title);
        et_phone=(ProLightEditText) findViewById(R.id.et_phone);
        tv_save_information=(ProRegularTextView)findViewById(R.id.tv_save_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myload= new MyLoader(UserInformationActivity.this);


        arrayList=new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData =new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);
        myload.showLoader();
        showdata();
        tv_save_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });


        et_phone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = et_phone.getText().toString();
                textLength = et_phone.getText().length();

                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;

                if (textLength == 1) {
                    if (!text.contains("(")) {
                        et_phone.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                        et_phone.setSelection(et_phone.getText().length());
                    }

                } else if (textLength == 5) {

                    if (!text.contains(")")) {
                        et_phone.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        et_phone.setSelection(et_phone.getText().length());
                    }

                } else if (textLength == 6) {
                    et_phone.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    et_phone.setSelection(et_phone.getText().length());

                } else if (textLength == 10) {
                    if (!text.contains("-")) {
                        et_phone.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        et_phone.setSelection(et_phone.getText().length());
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });


    }
    public void validation() {

        if (et_fname.getText().toString().trim().equals("")) {
            et_fname.setError("PLease enter First name");
            et_fname.requestFocus();
        } else {
            et_fname.setError(null);
            et_fname.clearFocus();

            if (et_lname.getText().toString().trim().equals("")) {
                et_lname.setError("Please enter Last name");
                et_lname.requestFocus();
            } else {

                et_lname.setError(null);
                et_lname.clearFocus();

                if (et_title.getText().toString().trim().equals("")) {
                    et_title.setError("Please enter title or position");
                    et_title.requestFocus();
                } else {
                    et_title.setError(null);
                    et_title.clearFocus();

                    if (et_phone.getText().toString().trim().equals("") || et_phone.getText().toString().trim().length() < 14) {
                        et_phone.setError("Please enter correct Phone number");
                        et_phone.requestFocus();
                    } else {
                        et_phone.setError(null);
                        et_phone.clearFocus();
                        myload.showLoader();

                        Logger.printMessage("fname-->",et_fname.getText().toString().trim());
                        Logger.printMessage("lname-->",et_lname.getText().toString().trim());
                        Logger.printMessage("position-->",et_title.getText().toString().trim());
                        Logger.printMessage("phone-->",et_phone.getText().toString().trim());

                        HashMap<String,String> Params=new HashMap<>();

                        Params.put("user_id",ProApplication.getInstance().getUserId());
                        Params.put("contact_f_name",et_fname.getText().toString().trim());
                        Params.put("contact_l_name",et_lname.getText().toString().trim());
                        Params.put("title_pos",et_title.getText().toString().trim());
                        Params.put("phone",et_phone.getText().toString().trim());

                        Logger.printMessage("PARAMS", String.valueOf(Params));

                        new CustomJSONParser().fireAPIForPostMethod( UserInformationActivity.this, ProConstant.app_prouserinfo_save,Params,null, new CustomJSONParser.CustomJSONResponse() {
                            @Override
                            public void onSuccess(String result) {
                                myload.dismissLoader();

                                Logger.printMessage("result",result);

                                JSONObject mainResponseObj = null;
                                try {
                                    mainResponseObj = new JSONObject(result);
                                    String message=mainResponseObj.getString("message");
                                    showdata();
                                    Toast.makeText(UserInformationActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String error, String response) {
                              myload.dismissLoader();
                                Toast.makeText(UserInformationActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(UserInformationActivity.this, ""+error, Toast.LENGTH_SHORT).show();

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
    public void showdata()
    {
        new CustomJSONParser().fireAPIForGetMethod(UserInformationActivity.this, ProConstant.app_prouserinfo_list, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();

                Logger.printMessage("result",result);

                try {
                    JSONObject mainResponseObj =  new JSONObject(result);
                    JSONArray info= mainResponseObj.getJSONArray("info_array");
                    for(int i=0;i<info.length();i++)
                    {
                        JSONObject jo=info.getJSONObject(i);
                        String f_name=jo.getString("f_name");
                        et_fname.setText(jo.getString("f_name"));
                        et_lname.setText(jo.getString("l_name"));
                        et_title.setText(jo.getString("title_pos"));
                        et_phone.setText(jo.getString("phone"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();
                Toast.makeText(UserInformationActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                myload.dismissLoader();

                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(UserInformationActivity.this, "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
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

            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
