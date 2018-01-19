package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImage;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImageView;
import com.android.llc.proringer.pro.proringerpro.helper.ImageCompressor;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by su on 15/1/18.
 */

public class GetVerificationThirdFragment extends Fragment {
    RelativeLayout RL_one,RL_two,RL_three;
    ImageView image_one_close,image_two_close,image_three_close;
    ProRegularTextView tv_continew,add_document_one,add_document_two,add_document_three;
    ImageView image1,image2,image3;
    RadioGroup radioGroup;
    ProLightEditText pin_number;
    String text_et;
    int text_et_length=0;
    private String mCurrentPhotoPath = "";
    private  String mCurrentPhotoPath_two="";
    String mCurrentPhotoPath_one="";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    int flag=0;
    RadioButton rb;
    File fileimage;
    MyLoader myload;
    File fileimage_one;
    File fileimage_two;
    File fileimage_three;
    RadioButton rb_yes_one,rb_no_one;

    ArrayList<String> image_docarrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_verification_third, container, false);
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((GetVerificationActivity) getActivity()).increaseStep();
        image_docarrayList=new ArrayList<>();
        RL_one=view.findViewById(R.id.RL_one);
        RL_two=view.findViewById(R.id.RL_two);
        RL_three=view.findViewById(R.id.RL_three);
        image_one_close=view.findViewById(R.id.image_one_close);
        image_two_close=view.findViewById(R.id.image_two_close);
        image_three_close=view.findViewById(R.id.image_three_close);
        tv_continew=view.findViewById(R.id.tv_continew);
        pin_number=view.findViewById(R.id.pin_number);
        image1=view.findViewById(R.id.image1);
        image2=view.findViewById(R.id.image2);
        image3=view.findViewById(R.id.image3);
        add_document_one=view.findViewById(R.id.add_document_one);
        add_document_two=view.findViewById(R.id.add_document_two);
        add_document_three=view.findViewById(R.id.add_document_three);
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
                Intent intent = new Intent(getActivity(), PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                Log.d("i_if","chooser");
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                flag=3;
            }
        });
        add_document_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                Log.d("i_if","chooser");
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                flag=2;

            }
        });
        add_document_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("frag_First----------->","chooser");
                Intent intent1 = new Intent(getActivity(), PermissionController.class);
                //Intent intent = new Intent(getActivity(), PermissionController.class);
                intent1.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                Log.d("i_if","chooser");
                startActivityForResult(intent1, REQUEST_IMAGE_CAPTURE);
                flag=1;

            }
        });
        pin_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_et=pin_number.getText().toString();
                text_et_length=pin_number.getText().toString().length();
                Log.d("text_et",text_et);
                Log.d("text_et_length",""+text_et_length);
                Log.d("char",""+s);
                if (text_et_length==3)
                {
                    if (!text_et.contains("-"))
                    {
                        pin_number.setText(new StringBuilder(text_et).insert(text_et.length()-1,"-").toString());
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
                Log.d("tv_continew", "tv_continew");
                if (!pin_number.getText().toString().trim().equals(""))
                {
                    getdata obj=new getdata();
                    obj.execute();
                }
                else
                {
                    pin_number.setError("Enter EIN pin");
                    pin_number.requestFocus();
                }

              //  CallDoc();
                /*if (!rb_no_one.isChecked() && !rb_yes_one.isChecked()) {

                    if (!rb_no_one.isChecked()) {
                        ((GetVerificationActivity) getActivity()).callVerificationFragments(4);

                    } else {
                        if (pin_number.getText().toString().trim().equals("")) {
                            pin_number.setError("Enter EIN pin");
                            pin_number.requestFocus();
                        } else {
                            CallDoc();
                        }
                    }
                }
                else
                {
                    pin_number.setError("Enter EIN pin");
                    pin_number.requestFocus();

                }*/
            }
        });
    }


    public void onActivityResult(final int requestCode, int resultCode, final Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            Log.d("frag_first_if_exc","first_if_exc");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if ( resultCode == Activity.RESULT_OK )
            {
                if (flag==1)
                {
                    Log.d("frag_2nd_if_exc","2nd_if_exc");
                    // startCropImageActivity(null);
                    mCurrentPhotoPath=result.getUri().toString();
                    Log.d("mCurrentPhotoPath",mCurrentPhotoPath);
                    RL_one.setVisibility(View.VISIBLE);
                    image1.setImageURI(Uri.parse(mCurrentPhotoPath));
                   /* String[] filepath = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(Uri.parse(mCurrentPhotoPath), filepath, null, null, null);
                   cursor.moveToFirst();
                    int column_index = cursor.getColumnIndex(filepath[0]);
                    String imgpath = cursor.getString(column_index);*/


                    fileimage_one=new File(mCurrentPhotoPath);
                }
                if (flag==2)
                {

                    mCurrentPhotoPath_one=result.getUri().toString();
                    Log.d("mCurrentPhotoPath",mCurrentPhotoPath_one);
                    RL_two.setVisibility(View.VISIBLE);
                    image2.setImageURI(Uri.parse(mCurrentPhotoPath_one));

                    fileimage_two=new File(mCurrentPhotoPath_one);
                }
                if (flag==3)
                {

                    mCurrentPhotoPath_two=result.getUri().toString();
                    Log.d("mCurrentPhotoPath",mCurrentPhotoPath_two);
                    RL_three.setVisibility(View.VISIBLE);
                    image3.setImageURI(Uri.parse(mCurrentPhotoPath_two));

                    fileimage_three=new File(mCurrentPhotoPath_two);
                }
            }
        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Log.d("frag_first_else_if","first_else_if");
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
                .setAspectRatio(4,3)
                .getIntent(getActivity());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

    }



    public class getdata extends AsyncTask<String, Void, String>
    {
        String docurl="http://esolz.co.in/lab6/proringer_latest/app_pro_verify_business_doc";

        File upload_temp;
        File upload_temp1;
        File upload_temp2;
        String exception = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();


                if (!mCurrentPhotoPath.equals("")&&!mCurrentPhotoPath_one.equals("")&&!mCurrentPhotoPath_two.equals("")) {
                    try {
                        Bitmap bmp = ImageCompressor.with(getActivity()).compressBitmap(mCurrentPhotoPath);
                        Bitmap bmp1 = ImageCompressor.with(getActivity()).compressBitmap(mCurrentPhotoPath);
                        Logger.printMessage("*****", "%% Bitmap size:: " + (bmp.getByteCount() / 1024) + " kb");
                        upload_temp = new File(getActivity().getCacheDir(), "" + System.currentTimeMillis() + ".png");
                        upload_temp1=new File(getActivity().getCacheDir(), "" + System.currentTimeMillis() + ".png");
                        upload_temp2=new File(getActivity().getCacheDir(), "" + System.currentTimeMillis() + ".png");
                        upload_temp.createNewFile();
                        upload_temp1.createNewFile();
                        upload_temp2.createNewFile();
                        ///for first doc
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        FileOutputStream fos = new FileOutputStream(upload_temp);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                        ///for 2nd doc
                        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] bitmapdata1 = bos1.toByteArray();

                        FileOutputStream fos1 = new FileOutputStream(upload_temp1);
                        fos1.write(bitmapdata1);
                        fos1.flush();
                        fos1.close();
                        //for 3rd doc
                        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] bitmapdata2 = bos2.toByteArray();

                        FileOutputStream fos2 = new FileOutputStream(upload_temp2);
                        fos2.write(bitmapdata2);
                        fos2.flush();
                        fos2.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }
                }
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

                MultipartBody.Builder requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", ProApplication.getInstance().getUserId())
                        .addFormDataPart("ein_no",text_et)
                       // .addFormDataPart("licensed_certified",upload_temp.getName() + "", RequestBody.create(MEDIA_TYPE_PNG, upload_temp))
                        .addFormDataPart("business_filed","")
                        .addFormDataPart("business_insured", "");
                if (upload_temp != null) {
                    Log.d("path*****", "" + upload_temp.getAbsolutePath());
                    requestBody.addFormDataPart("licensed_certified", upload_temp.getName() + "", RequestBody.create(MEDIA_TYPE_PNG, upload_temp));
                    requestBody.addFormDataPart("business_filed",upload_temp1.getName() + "", RequestBody.create(MEDIA_TYPE_PNG, upload_temp1));
                    requestBody.addFormDataPart("business_insured",upload_temp2.getName() + "", RequestBody.create(MEDIA_TYPE_PNG, upload_temp2));
                } else {
                    requestBody.addFormDataPart("licensed_certified", "");
                    requestBody.addFormDataPart("business_filed", "");
                    requestBody.addFormDataPart("business_insured", "");
                }


                Request request = new Request.Builder()
                        .url(docurl)
                        .post(requestBody.build())
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                String responseString = response.body().string();
                Logger.printMessage("responseInfo", responseString);
                JSONObject respo = new JSONObject(responseString);
                Logger.printMessage("response-->", String.valueOf(respo.getBoolean("response")));

                if (respo.getBoolean("response")) {
                    Logger.printMessage("message-->", respo.getString("message"));
                    return respo.getString("message");
                } else {
                    exception = respo.getString("message");
                    return exception;
                }

            } catch (Exception e) {
                e.printStackTrace();
                exception = e.getMessage();
                return exception;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("doc_res---------->",s);
            ((GetVerificationActivity)getActivity()).callVerificationFragments(4);

        }
    }


}