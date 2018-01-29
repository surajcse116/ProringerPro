package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;


import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImage;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImageView;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;;
import java.util.ArrayList;
import static android.app.Activity.RESULT_OK;

/**
 * Created by su on 15/1/18.
 */

public class GetVerificationThirdFragment extends Fragment {
    RelativeLayout RL_one, RL_two, RL_three;
    ImageView image_one_close, image_two_close, image_three_close;
    ProRegularTextView tv_continew, add_document_one, add_document_two, add_document_three;
    ImageView image1, image2, image3;
    ProLightEditText pin_number;
    String text_et;
    int text_et_length = 0;
    private String mCurrentPhotoPathOne = "";
    private String mCurrentPhotoPathThree = "";
    private String mCurrentPhotoPathTwo = "";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    int flag = 0;
    MyLoader myLoader;

    File fileimage_one = null;
    File fileimage_two = null;
    File fileimage_three = null;

    ArrayList<String> image_docarrayList;

    RadioGroup radio_group_pin;
    RadioGroup radio_group_licence;
    RadioGroup radio_group_business_field;
    RadioGroup radio_group_business;


    boolean pinNumberRadioCheck = false;
    boolean licenceOrCertifiedRadioCheck = false;
    boolean legalBusinessNameRadioCheck = false;
    boolean businessInsureRadioCheck = false;

    boolean confirmNowOrLaterValue=false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_verification_third, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((GetVerificationActivity) getActivity()).increaseStep();
        image_docarrayList = new ArrayList<>();
        RL_one = view.findViewById(R.id.RL_one);
        RL_two = view.findViewById(R.id.RL_two);
        RL_three = view.findViewById(R.id.RL_three);
        image_one_close = view.findViewById(R.id.image_one_close);
        image_two_close = view.findViewById(R.id.image_two_close);
        image_three_close = view.findViewById(R.id.image_three_close);
        tv_continew = view.findViewById(R.id.tv_continew);
        pin_number = view.findViewById(R.id.pin_number);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        add_document_one = view.findViewById(R.id.add_document_one);
        add_document_two = view.findViewById(R.id.add_document_two);
        add_document_three = view.findViewById(R.id.add_document_three);

        myLoader=new MyLoader(getActivity());


        pin_number.setEnabled(false);
        pin_number.setClickable(false);
        pin_number.setBackgroundResource(R.drawable.background_disable_inputtext);


        image_three_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL_three.setVisibility(View.GONE);
            }
        });
        image_two_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL_two.setVisibility(View.GONE);
            }
        });
        image_one_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL_one.setVisibility(View.GONE);
            }
        });
        add_document_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (businessInsureRadioCheck) {
                    Intent intent = new Intent(getActivity(), PermissionController.class);
                    intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    flag = 3;
                }
            }
        });
        add_document_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (legalBusinessNameRadioCheck) {
                    Intent intent = new Intent(getActivity(), PermissionController.class);
                    intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    flag = 2;
                }

            }
        });
        add_document_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (licenceOrCertifiedRadioCheck) {
                    Intent intent1 = new Intent(getActivity(), PermissionController.class);
                    //Intent intent = new Intent(getActivity(), PermissionController.class);
                    intent1.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                    startActivityForResult(intent1, REQUEST_IMAGE_CAPTURE);
                    flag = 1;
                }

            }
        });
        pin_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_et = pin_number.getText().toString();
                text_et_length = pin_number.getText().toString().length();
                if (text_et_length == 3) {
                    if (!text_et.contains("-")) {
                        pin_number.setText(new StringBuilder(text_et).insert(text_et.length() - 1, "-").toString());
                        pin_number.setSelection(pin_number.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_continew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmNowOrLaterValue) {
                    validationCheck();
                }
                else {
                    ((GetVerificationActivity)getActivity()).callVerificationFragments(4);
                }
            }
        });


        radio_group_pin = (RadioGroup) view.findViewById(R.id.radio_group_pin);
        radio_group_pin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yes_one:
                        // do operations specific to this selection
                        pinNumberRadioCheck = true;
                        pin_number.setEnabled(true);
                        pin_number.setClickable(true);
                        pin_number.setBackgroundResource(R.drawable.background_solidorange_border);
                        Logger.printMessage("pinNumberRadioCheck-->", ""+pinNumberRadioCheck);
                        break;
                    case R.id.rb_no_one:
                        // do operations specific to this selection
                        pinNumberRadioCheck = false;
                        pin_number.setText("");
                        pin_number.setError(null);
                        pin_number.clearFocus();
                        pin_number.setEnabled(false);
                        pin_number.setClickable(false);
                        pin_number.setBackgroundResource(R.drawable.background_disable_inputtext);
                        Logger.printMessage("pinNumberRadioCheck-->", ""+pinNumberRadioCheck);
                        break;
                }
                confirmNowOrLater();
            }
        });


        radio_group_licence = (RadioGroup) view.findViewById(R.id.radio_group_licence);
        radio_group_licence.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yes_two:
                        // do operations specific to this selection
                        licenceOrCertifiedRadioCheck = true;
                        Logger.printMessage("licenceOrCertifiedRadioCheck-->", ""+licenceOrCertifiedRadioCheck);
                        break;
                    case R.id.rb_no_two:
                        // do operations specific to this selection
                        licenceOrCertifiedRadioCheck = false;
                        RL_one.setVisibility(View.GONE);

                        fileimage_one=null;
                        mCurrentPhotoPathOne = "";

                        Logger.printMessage("licenceOrCertifiedRadioCheck-->", ""+licenceOrCertifiedRadioCheck);
                        break;
                }
                confirmNowOrLater();
            }
        });


        radio_group_business_field = (RadioGroup) view.findViewById(R.id.radio_group_business_field);
        radio_group_business_field.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yes_three:
                        // do operations specific to this selection
                        legalBusinessNameRadioCheck = true;
                        Logger.printMessage("legalBusinessNameRadioCheck-->", ""+legalBusinessNameRadioCheck);
                        break;
                    case R.id.rb_no_three:
                        // do operations specific to this selection
                        legalBusinessNameRadioCheck = false;
                        RL_two.setVisibility(View.GONE);
                        fileimage_two=null;
                        mCurrentPhotoPathTwo = "";
                        Logger.printMessage("legalBusinessNameRadioCheck-->", ""+legalBusinessNameRadioCheck);
                        break;
                }
                confirmNowOrLater();
            }
        });


        radio_group_business = (RadioGroup) view.findViewById(R.id.radio_group_business);
        radio_group_business.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yes_four:
                        // do operations specific to this selection
                        businessInsureRadioCheck = true;
                        Logger.printMessage("businessInsureRadioCheck-->", ""+businessInsureRadioCheck);
                        break;
                    case R.id.rb_no_four:
                        // do operations specific to this selection
                        businessInsureRadioCheck = false;
                        RL_three.setVisibility(View.GONE);
                        fileimage_three=null;
                        mCurrentPhotoPathThree = "";
                        Logger.printMessage("businessInsureRadioCheck-->", ""+businessInsureRadioCheck);
                        break;
                }
                confirmNowOrLater();
            }
        });


    }


    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Logger.printMessage("frag_first_if_exc", "first_if_exc");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                if (flag == 1) {
                    Logger.printMessage("frag_2nd_if_exc", "2nd_if_exc");
                    // startCropImageActivity(null);
                    mCurrentPhotoPathOne = result.getUri().toString();
                    Logger.printMessage("mCurrentPhotoPathOne", mCurrentPhotoPathOne);
                    RL_one.setVisibility(View.VISIBLE);
                    image1.setImageURI(Uri.parse(mCurrentPhotoPathOne));
                   /* String[] filepath = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(Uri.parse(mCurrentPhotoPathOne), filepath, null, null, null);
                   cursor.moveToFirst();
                    int column_index = cursor.getColumnIndex(filepath[0]);
                    String imgpath = cursor.getString(column_index);*/


                    fileimage_one = new File(mCurrentPhotoPathOne);
                }
                if (flag == 2) {

                    mCurrentPhotoPathTwo = result.getUri().toString();
                    Logger.printMessage("mCurrentPhotoPath", mCurrentPhotoPathTwo);
                    RL_two.setVisibility(View.VISIBLE);
                    image2.setImageURI(Uri.parse(mCurrentPhotoPathTwo));

                    fileimage_two = new File(mCurrentPhotoPathTwo);
                }
                if (flag == 3) {

                    mCurrentPhotoPathThree = result.getUri().toString();
                    Logger.printMessage("mCurrentPhotoPath", mCurrentPhotoPathThree);
                    RL_three.setVisibility(View.VISIBLE);
                    image3.setImageURI(Uri.parse(mCurrentPhotoPathThree));
                    fileimage_three = new File(mCurrentPhotoPathThree);
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Logger.printMessage("frag_first_else_if", "first_else_if");
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
                .setAspectRatio(4, 3)
                .getIntent(getActivity());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    public void validationCheck() {

        if (radio_group_pin.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Toast.makeText(getActivity(), "Please Check Yes or No For Pin Number", Toast.LENGTH_SHORT).show();
        } else {
            // one of the radio buttons is checked

            if (radio_group_licence.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
                Toast.makeText(getActivity(), "Please Check Yes or No For Licence or certified field", Toast.LENGTH_SHORT).show();
            } else {
                // one of the radio buttons is checked

                if (radio_group_business_field.getCheckedRadioButtonId() == -1) {
                    // no radio buttons are checked
                    Toast.makeText(getActivity(), "Please Check Yes or No for legal business name", Toast.LENGTH_SHORT).show();

                } else {
                    // one of the radio buttons is checked

                    if (radio_group_business.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        Toast.makeText(getActivity(), "Please Check Yes or No for business insured", Toast.LENGTH_SHORT).show();

                    } else {
                        // one of the radio buttons is checked

                        if (pinNumberRadioCheck) {
                            if (pin_number.getText().toString().trim().equals("")) {
                                pin_number.setError("Enter EIN Number");
                                pin_number.requestFocus();
                            } else {
                                pin_number.setError(null);
                                pin_number.clearFocus();

                                if (pin_number.getText().toString().trim().length()<10) {
                                    pin_number.setError("Enter Correct EIN Number");
                                    pin_number.requestFocus();
                                } else {
                                    pin_number.setError(null);
                                    pin_number.clearFocus();

                                    afterEinCheck();
                                }
                            }
                        } else {
                            afterEinCheck();
                        }
                    }
                }

            }
        }
    }

    public void fireDocumentSubmit() {

        ArrayList<SetGetAPIPostData> arrayListPostParamsValues = new ArrayList<>();


        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("ein_no");
        setGetAPIPostData.setValues(pin_number.getText().toString().trim());
        arrayListPostParamsValues.add(setGetAPIPostData);


        ArrayList<String> filesImagesKey = new ArrayList<>();
        ArrayList<File> filesImagesValue = new ArrayList<>();

        if (fileimage_one != null) {
            filesImagesKey.add("licensed_certified");
            filesImagesValue.add(fileimage_one);
        }
        if (fileimage_two != null) {
            filesImagesKey.add("business_filed");
            filesImagesValue.add(fileimage_two);
        }
        if (fileimage_three != null) {
            filesImagesKey.add("business_insured");
            filesImagesValue.add(fileimage_three);
        }


        new CustomJSONParser().APIForWithPhotoPostMethodMultipleKeyImageParams(getActivity(), ProConstant.app_pro_verify_business_doc, arrayListPostParamsValues, filesImagesKey, filesImagesValue, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                myLoader.dismissLoader();
                try {
                    JSONObject jo = new JSONObject(result);
                    String msg = jo.getString("message");
                    Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();


                    ((GetVerificationActivity)getActivity()).callVerificationFragments(4);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
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

    public void afterEinCheck(){
        if (licenceOrCertifiedRadioCheck) {
            Logger.printMessage("licenceOrCertifiedRadioCheck1-->",""+licenceOrCertifiedRadioCheck);
            Logger.printMessage("fileimage_one1-->",""+fileimage_one);

            if (fileimage_one == null) {
                Toast.makeText(getActivity(), "Please Choose licence or certified file", Toast.LENGTH_SHORT).show();
            } else {
                afterLicenceOrCertified();
            }
        } else {
            Logger.printMessage("licenceOrCertifiedRadioCheck2-->",""+licenceOrCertifiedRadioCheck);
            Logger.printMessage("fileimage_one2-->",""+fileimage_one);
            afterLicenceOrCertified();
        }
    }

    public void afterLicenceOrCertified(){
        if (legalBusinessNameRadioCheck) {

            Logger.printMessage("legalBusinessNameRadioCheck1-->",""+legalBusinessNameRadioCheck);
            Logger.printMessage("fileimage_two1-->",""+fileimage_two);

            if (fileimage_two == null) {
                Toast.makeText(getActivity(), "Please Choose legal business name field", Toast.LENGTH_SHORT).show();
            } else {
                afterLegalBusiness();
            }

        }else {
            Logger.printMessage("legalBusinessNameRadioCheck2-->",""+legalBusinessNameRadioCheck);
            Logger.printMessage("fileimage_two2-->",""+fileimage_two);
            afterLegalBusiness();
        }
    }

    public void afterLegalBusiness(){
        if(businessInsureRadioCheck){
            if (fileimage_three==null){
                Toast.makeText(getActivity(),"please choose business insured",Toast.LENGTH_SHORT).show();
            }else {
                Logger.printMessage("fire1-->","yes");
                fireDocumentSubmit();
            }
        }else {
            Logger.printMessage("fire2-->","yes");
            fireDocumentSubmit();
        }
    }

    public void confirmNowOrLater(){
        if (pinNumberRadioCheck||licenceOrCertifiedRadioCheck||legalBusinessNameRadioCheck||businessInsureRadioCheck){
            confirmNowOrLaterValue=true;
            tv_continew.setText("confirm and continue");
            tv_continew.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }else {
            confirmNowOrLaterValue=false;
            tv_continew.setText("confirm later and continue");
            tv_continew.setBackgroundColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void loadPreviousVisibleValues(){
        if(fileimage_three!=null){
            RL_three.setVisibility(View.VISIBLE);
            image3.setImageURI(Uri.parse(mCurrentPhotoPathThree));
        }
        else if(fileimage_two!=null){
            RL_two.setVisibility(View.VISIBLE);
            image2.setImageURI(Uri.parse(mCurrentPhotoPathTwo));
        }
        else if(fileimage_one!=null){
            RL_one.setVisibility(View.VISIBLE);
            image1.setImageURI(Uri.parse(mCurrentPhotoPathOne));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        loadPreviousVisibleValues();
    }
}