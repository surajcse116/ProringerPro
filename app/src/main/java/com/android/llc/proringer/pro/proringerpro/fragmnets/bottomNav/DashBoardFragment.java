package com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.AddServiceAreaActivity;
import com.android.llc.proringer.pro.proringerpro.activities.AddServicesActivity;
import com.android.llc.proringer.pro.proringerpro.activities.CompanyProfileActivity;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LicenceAddActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LicenceListActivity;
import com.android.llc.proringer.pro.proringerpro.activities.PortFolioActivity;
import com.android.llc.proringer.pro.proringerpro.activities.PremiumActivity;
import com.android.llc.proringer.pro.proringerpro.activities.UserInformationActivity;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImage;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImageView;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by su on 9/22/17.
 */

public class DashBoardFragment extends Fragment {

    ProSemiBoldTextView  tv_goto_premium,tv_name,tv_rating;
    TextView tv_active_projects;
    ProRegularTextView tv_address,tv_totalmessage,tv_favorite_pros;
    RelativeLayout userInformation, servicearea, service_setting, licence, login_settings, Protofolio;
    RatingBar rbar;
    ImageView profile_pic;
    ArrayList<SetGetAPIPostData> arrayList=null;
    MyLoader myload;

    String pro_premium_status="";
    String Pro_verified="";
    //PopupWindow popupWindow;
    int CASEAPPLY=0;

    String city="",state="",zip="",address="",mCurrentPhotoPath,text;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashborad, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInformation = (RelativeLayout) view.findViewById(R.id.userInformation);
        servicearea = (RelativeLayout) view.findViewById(R.id.servicearea);
        service_setting = (RelativeLayout) view.findViewById(R.id.service_setting);
        licence = (RelativeLayout) view.findViewById(R.id.licence);
        login_settings = (RelativeLayout) view.findViewById(R.id.login_settings);
        Protofolio = (RelativeLayout) view.findViewById(R.id.Protofolio);
        tv_goto_premium = (ProSemiBoldTextView) view.findViewById(R.id.tv_goto_premium);
        tv_active_projects=(TextView)view.findViewById(R.id.tv_active_projects);
        tv_name=(ProSemiBoldTextView)view.findViewById(R.id.tv_name);
        tv_address=(ProRegularTextView)view.findViewById(R.id.tv_address);
        tv_totalmessage=(ProRegularTextView)view.findViewById(R.id.tv_totalmessage);
        tv_favorite_pros=(ProRegularTextView)view.findViewById(R.id.tv_favorite_pros);
        tv_rating=(ProSemiBoldTextView)view.findViewById(R.id.tv_rating);
        profile_pic=(ImageView)view.findViewById(R.id.profile_pic);
        rbar=(RatingBar)view.findViewById(R.id.rbar);
        myload= new MyLoader(getActivity());


        tv_goto_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openGetVerifiedDialog();
                if (CASEAPPLY==1)
                {
                    //openGetVerifiedDialog();

                    Intent intent=new Intent(getActivity(), GetVerificationActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("state",state);
                    intent.putExtra("zip",zip);
                    intent.putExtra("address",address);
                    startActivityForResult(intent,1111);
                }
                else if (CASEAPPLY==2)
                {
                    Toast.makeText(getContext(),"Verified",Toast.LENGTH_SHORT).show();
                }
                else if (CASEAPPLY==3)
                {
                    //openGetVerifiedDialog();
                    Intent intent=new Intent(getActivity(), GetVerificationActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("state",state);
                    intent.putExtra("zip",zip);
                    intent.putExtra("address",address);
                    startActivityForResult(intent,1111);
//                    Toast.makeText(getContext(),"already applied to verify",Toast.LENGTH_SHORT).show();
                }

                else if (CASEAPPLY==4)
                {
                    Intent i = new Intent(getActivity(), PremiumActivity.class);
                    startActivity(i);
                }
            }
        });

        userInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UserInformationActivity.class);
                startActivity(i);
            }
        });
        servicearea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddServiceAreaActivity.class);
                startActivity(i);
            }
        });
        service_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddServicesActivity.class);
                startActivity(i);
            }
        });

        licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LicenceListActivity.class);
                startActivity(i);
            }
        });
        login_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CompanyProfileActivity.class);
                startActivity(i);
            }
        });
        Protofolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PortFolioActivity.class);
                startActivity(i);
            }
        });

        view.findViewById(R.id.profile_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                startActivityForResult(intent, 200);
                //showUploadImage();
            }
        });

        loadAndShowData();
    }

    public  void loadAndShowData()
    {
        arrayList=new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData =new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.dashboard, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("Result",result);
                try {
                    JSONObject job= new JSONObject(result);
                    Logger.printMessage("resultarr", String.valueOf(job));
                    JSONArray jarr= job.getJSONArray("info_array");


                    for (int i=0;i<jarr.length();i++)
                    {
                        JSONObject jo= jarr.getJSONObject(i);
                        tv_name.setText(jo.getString("company_name"));
                        tv_rating.setText(jo.getString("avg_rating"));
                        tv_active_projects.setText(jo.getString("total_review"));
                        tv_totalmessage.setText(jo.getString("total_msg"));
                        tv_favorite_pros.setText(jo.getString("project_watchlist"));
                        Glide.with(getActivity()).load(jo.getString("profile_img")).into(profile_pic);


                        pro_premium_status=jo.getString("pro_premium_status");
                        Pro_verified=jo.getString("Pro_verified");

                        if (pro_premium_status.equals("2") && Pro_verified.equals("N"))
                        {
                            tv_goto_premium.setText("GET VERIFIED");
                            CASEAPPLY=1;

                        }
                        else if(pro_premium_status.equals("2") && Pro_verified.equals("Y"))
                        {
                            tv_goto_premium.setText("VERIFIED");
                            CASEAPPLY=2;
                        }
                        else if(pro_premium_status.equals("2") && Pro_verified.equals("A"))
                        {
                            tv_goto_premium.setText("GET VERIFIED");
                            CASEAPPLY=3;
                        }

                        else if(pro_premium_status.equals("1") ||pro_premium_status.equals("0"))
                        {
                            tv_goto_premium.setText("GO PREMIUM");
                            CASEAPPLY=4;
                        }


                        city=jo.getString("city");
                        state=jo.getString("state");
                        zip=jo.getString("zipcode");
                        address=jo.getString("address");

                        tv_address.setText(jo.getString("city")+","+jo.getString("state")+jo.getString("zipcode"));
                        rbar.setStepSize((float) 0.5);
                        rbar.setRating(Float.parseFloat(jo.getString("avg_rating")));
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


    private void openGetVerifiedDialog() {

        final Dialog dialog = new Dialog(getContext(), R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.dialog_get_verified);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ProRegularTextView txt_note=dialog.findViewById(R.id.txt_note);
        final ProLightEditText et_confirmphoneno=dialog.findViewById(R.id.et_confirmphoneno);
        ProRegularTextView tv3=dialog.findViewById(R.id.tv3);
        ProRegularTextView tv4=dialog.findViewById(R.id.tv4);
        ProRegularTextView send_now=dialog.findViewById(R.id.send_now);

        ImageView img_close=dialog.findViewById(R.id.img_close);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        et_confirmphoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text=et_confirmphoneno.getText().toString();
                int textLength=et_confirmphoneno.length();
                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;

                if (textLength==1)
                {
                    if (!text.contains("("))
                    {
                        et_confirmphoneno.setText(new StringBuilder(text).insert(text.length()-1,"(").toString());
                        et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());
                    }
                }
                else if (textLength==5)
                {
                    if (!text.contains(")"))
                    {
                        et_confirmphoneno.setText(new StringBuilder(text).insert(text.length()-1,")").toString());
                        et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());
                    }

                }
                else if (textLength==6)
                {
                    et_confirmphoneno.setText(new StringBuilder(text).insert(text.length()-1," ").toString());
                    et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());
                }
                else  if (textLength==10)
                {
                    et_confirmphoneno.setText(new StringBuilder(text).insert(text.length()-1,"-").toString());
                    et_confirmphoneno.setSelection(et_confirmphoneno.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send_now.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* if (text.equals(" "))
                {
                    et_confirmphoneno.setError("Please enter Phone no");
                    et_confirmphoneno.requestFocus();

                }
                else
                {

                }*/

            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // only for gingerbread and newer versions

            tv3.setText(Html.fromHtml(getResources().getString(R.string.get_verified_data2),Html.FROM_HTML_MODE_COMPACT));
            // txt_note.setText(getResources().getString(R.string.get_verified_data));
            txt_note.setText(Html.fromHtml(getResources().getString(R.string.welcome_text),Html.FROM_HTML_MODE_COMPACT));
        }
        else
        {
//            tv3.setText(Html.fromHtml(getResources().getString(R.string.get_verified_data2)+" "+"<b>(111) 111-2222</b>"));
            tv3.setText(Html.fromHtml(getResources().getString(R.string.get_verified_data2)));
            // txt_note.setText(getResources().getString(R.string.get_verified_data));
            txt_note.setText(Html.fromHtml(getResources().getString(R.string.welcome_text)));
        }

        tv4.setText(getResources().getString(R.string.get_verified_data));

        dialog.show();
    }

    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mCurrentPhotoPath = result.getUri().toString();

                loadProfileImage();

                Logger.printMessage("path-->", mCurrentPhotoPath);

                Toast.makeText(getActivity(), "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
//                showImagePickerOption();
            startCropImageActivity(null);
        }
    }



    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        Intent intent = CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(false)
                .setAspectRatio(1,1)
                .getIntent(getActivity());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    public void loadProfileImage() {
        if (mCurrentPhotoPath.trim().equals("")) {
            Toast.makeText(getActivity(), "Please choose Image", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<SetGetAPIPostData> arrayListPostParamsValues = new ArrayList<>();


            SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
            setGetAPIPostData.setPARAMS("user_id");
            setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
            arrayListPostParamsValues.add(setGetAPIPostData);

            ArrayList<File> filesImages = new ArrayList<>();
            File file=new File(mCurrentPhotoPath);
            filesImages.add(file);


            CustomJSONParser.ImageParam = "profile_image";

            new CustomJSONParser().APIForWithPhotoPostMethod(getActivity(), ProConstant.app_pro_profile_img, arrayListPostParamsValues, filesImages, new CustomJSONParser.CustomJSONResponse() {
                @Override
                public void onSuccess(String result) {
                    Logger.printMessage("result", result);
                    myload.dismissLoader();
                    try {
                        JSONObject jo = new JSONObject(result);
                        String msg = jo.getString("message");
                        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

                        loadAndShowData();

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


}