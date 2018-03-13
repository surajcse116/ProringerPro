package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginSettingActivity extends AppCompatActivity {

    ProLightEditText new_email,confirm_new_email,current_password,new_password,confirm_new_password;
    ProRegularTextView update_email,change_password,current_email;
    public MyLoader myLoader = null;
    ImageView show_password,show_passwordnew,show_passwordconfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsetting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ProRegularTextView) findViewById(R.id.tv_title)).setText("Login Settings");
        myLoader = new MyLoader(LoginSettingActivity.this);
        new_email=(ProLightEditText)findViewById(R.id.new_email);
        confirm_new_email=(ProLightEditText)findViewById(R.id.confirm_new_email);
        current_password=(ProLightEditText)findViewById(R.id.current_password);
        new_password=(ProLightEditText)findViewById(R.id.new_password);
        confirm_new_password=(ProLightEditText)findViewById(R.id.confirm_new_password);
        update_email=(ProRegularTextView)findViewById(R.id.update_email);
        change_password=(ProRegularTextView)findViewById(R.id.change_password);
        current_email=(ProRegularTextView)findViewById(R.id.current_email);
        show_password=(ImageView)findViewById(R.id.show_password);
        show_passwordnew=(ImageView)findViewById(R.id.show_passwordnew);
        show_passwordconfirm=(ImageView)findViewById(R.id.show_passwordconfirm);
        current_email.setText(ProApplication.getInstance().getUserEmail());
        show_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch ( motionEvent.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        current_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        current_password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;

            }
        });
        show_passwordnew.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch ( motionEvent.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        new_password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;

            }
        });
        show_passwordconfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch ( motionEvent.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        confirm_new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        confirm_new_password .setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;

            }
        });
        update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationmail();
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.printMessage("email", ProApplication.getInstance().getUserEmail());
                closeKeyboard();
                validationpass();
            }
        });
    }
    public void validationmail()
    {
       if (new_email.getText().toString().trim().equals(""))
       {
           new_email.setError("Please enter email");
           new_email.requestFocus();
       }
       else
       {
           new_email.setError(null);
           new_email.clearFocus();

           if (MethodsUtils.isValidEmail(new_email.getText().toString().trim()))
           {
               new_email.setError(null);
               new_email.clearFocus();


               if (confirm_new_email.getText().toString().trim().equals(""))

               {
                   confirm_new_email.setError("Please enter email");
                   confirm_new_email.requestFocus();
               }
               else
               {
                   confirm_new_email.setError(null);
                   confirm_new_email.clearFocus();
                   if (!new_email.getText().toString().trim().equals(confirm_new_email.getText().toString().trim()))
                   {
                       confirm_new_email.setError("Confirm Email is not same with given Email");
                       confirm_new_email.requestFocus();
                   }
                   else
                   {
                       confirm_new_email.setError(null);
                       confirm_new_email.clearFocus();
                       HashMap<String, String> Params = new HashMap<>();
                       Params.put("user_id",ProApplication.getInstance().getUserId());
                       Params.put("newEmail",confirm_new_email.getText().toString().trim());
                       new CustomJSONParser().fireAPIForPostMethod(LoginSettingActivity.this, ProConstant.app_updatemail, Params, null, new CustomJSONParser.CustomJSONResponse() {
                           @Override
                           public void onSuccess(String result) {
                               try {
                                   myLoader.dismissLoader();
                                   JSONObject job= new JSONObject(result);
                                   Toast.makeText(LoginSettingActivity.this, job.getString("message"), Toast.LENGTH_SHORT).show();
                                   LayoutInflater factory = LayoutInflater.from(LoginSettingActivity.this);
                                   final View deleteDialogView = factory.inflate(R.layout.otpdialog, null);
                                   final AlertDialog deleteDialog = new AlertDialog.Builder(LoginSettingActivity.this).create();
                                   deleteDialog.setView(deleteDialogView);


                                   deleteDialogView.findViewById(R.id.prosemi_ok).setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           HashMap<String, String> Params = new HashMap<>();
                                           Params.put("user_id",ProApplication.getInstance().getUserId());
                                           Params.put("verificationCode", ((ProLightEditText)deleteDialogView.findViewById(R.id.confirm_otp)).getText().toString().trim());
                                           new CustomJSONParser().fireAPIForPostMethod(LoginSettingActivity.this, ProConstant.app_vrificationcode, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                               @Override
                                               public void onSuccess(String result) {

                                                   deleteDialog.dismiss();
                                                   myLoader.dismissLoader();
                                                   Logger.printMessage("vefiotp",result);
                                                   try {
                                                       ProApplication.getInstance().setUserEmail(confirm_new_email.getText().toString().trim());
                                                       Toast.makeText(LoginSettingActivity.this, new JSONObject(result).getString("message"), Toast.LENGTH_LONG).show();
                                                       current_email.setText(ProApplication.getInstance().getUserEmail());
                                                       new_email.setText("");
                                                       confirm_new_email.setText("");
                                                   } catch (JSONException e) {
                                                       e.printStackTrace();
                                                   }
                                               }

                                               @Override
                                               public void onError(String error, String response) {
                                                   myLoader.dismissLoader();
                                                   Toast.makeText(LoginSettingActivity.this,error,Toast.LENGTH_SHORT).show();
                                               }

                                               @Override
                                               public void onError(String error) {
                                                   myLoader.dismissLoader();
                                                   Toast.makeText(LoginSettingActivity.this,error,Toast.LENGTH_SHORT).show();

                                               }

                                               @Override
                                               public void onStart() {
                                                   myLoader.showLoader();
                                               }
                                           });
                                       }
                                   });

                                   deleteDialogView.findViewById(R.id.prosemi_cancle).setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           deleteDialog.dismiss();
                                       }
                                   });
                                   deleteDialog.show();
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }

                           }

                           @Override
                           public void onError(String error, String response) {
                             myLoader.dismissLoader();
                             Toast.makeText(LoginSettingActivity.this,error,Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onError(String error) {
                             myLoader.dismissLoader();
                           }

                           @Override
                           public void onStart() {
                              myLoader.showLoader();
                           }
                       });

                   }
               }
           }
           else
           {
               new_email.setError("Please Enter valid email");
               new_email.requestFocus();
           }
       }
    }
    public void validationpass()
    {
        if (current_password.getText().toString().trim().equals(""))
        {
            current_password.setError("Please enter current password");
            current_password.requestFocus();
        }
        else
        {
            current_password.setError(null);
            current_password.clearFocus();

            if (new_password.getText().toString().trim().equals(""))
            {
                new_password.setError("Enter new password");
                new_password.requestFocus();
            }
            else
            {
                new_password.setError(null);
                new_password.clearFocus();
                if (new_password.getText().toString().trim().length()<=5)
                {
                    new_password.setError("Please enter minimum 6 character or digit");
                    new_password.requestFocus();
                }
                else
                {
                        new_password.setError(null);
                        new_password.clearFocus();

                    if (confirm_new_password.getText().toString().trim().equals(""))
                    {
                        confirm_new_password.setError("Please enter confirm password");
                        confirm_new_password.requestFocus();
                    }
                    else {
                        confirm_new_password.setError(null);
                        confirm_new_password.clearFocus();

                        if (!new_password.getText().toString().trim().equals(confirm_new_password.getText().toString().trim()))
                        {
                            confirm_new_password.setError("Confirm password is not same with given password");
                            confirm_new_password.requestFocus();
                        }
                        else
                        {
                            confirm_new_password.setError(null);
                            confirm_new_password.clearFocus();
                            HashMap<String, String> Params = new HashMap<>();
                            Params.put("email",ProApplication.getInstance().getUserEmail());
                            Params.put("old_password",current_password.getText().toString().trim());
                            Params.put("new_password",confirm_new_password.getText().toString());

                            new CustomJSONParser().fireAPIForPostMethod(LoginSettingActivity.this, ProConstant.app_updatepassword, Params, null, new CustomJSONParser.CustomJSONResponse() {
                                @Override
                                public void onSuccess(String result) {
                                    myLoader.dismissLoader();
                                    Logger.printMessage("rspondghsgjdg",result);
                                    try {
                                        current_password.setText("");
                                        new_password.setText("");
                                        confirm_new_password.setText("");
                                        JSONObject job= new JSONObject(result);
                                        Toast.makeText(LoginSettingActivity.this,job.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onError(String error, String response) {
                                    myLoader.dismissLoader();
                                    try {
                                        JSONObject job= new JSONObject(response);
                                        Toast.makeText(LoginSettingActivity.this,""+job.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onError(String error) {
                                    myLoader.dismissLoader();
                                    new MYAlert(LoginSettingActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                                        @Override
                                        public void OnOk(boolean res) {

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
                }
            }
        }
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)LoginSettingActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(LoginSettingActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
