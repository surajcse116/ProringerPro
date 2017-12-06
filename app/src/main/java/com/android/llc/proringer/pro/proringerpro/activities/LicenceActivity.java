package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.Constant.AppConstant;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialog_catagory;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwo;
import com.android.llc.proringer.pro.proringerpro.helper.Appdata;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert_error;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.MyCustomAlertListener;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.APIGetData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.showlicencesetget;
import com.android.llc.proringer.pro.proringerpro.utils.ImageFilePath;
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.HighRegularTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by su on 8/18/17.
 */

public class LicenceActivity extends AppCompatActivity implements MyCustomAlertListener {

    private static final int REQUEST_WRITE_PERMISSION1 = 3000;
    private static final int REQUEST_WRITE_PERMISSION2 = 4000;
    RecyclerView rcv_licence_list;
    RelativeLayout RLAddLicence, RLEmpty,relative_dropdown;
    LicenceAdapter licenceAdapter;

    ProRegularTextView protv_com,tv_service,tv_save_licence;
    ProLightEditText tv_issue,tv_licence_number;
    ProRegularTextView tv_expires;
    File file = null;
    Dialog dialog = null;
    MyLoader myLoader;
    LinearLayout LLEdit, LLUpload;
    ImageView img_licence_file;
    private int PICK_IMAGE_REQUEST = 100;
    private int PICK_PDF_REQUEST = 200;
    ArrayList<APIGetData> arrayList = null;
    ArrayList<showlicencesetget>arrayList1;
    int arrlistsize;
    String mycurrentphotopath="";
    String pros_contact_service="";
    CustomListAdapterDialog_catagory custom = null;
    PopupWindow popupWindow;
    ImageView img_add_licence,dropdown;
    JSONArray catagory;
    private int mYear=0, mMonth=0, mDay=0, mHour, mMinute;
    boolean flag=true;
    int year;
    int month,date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RLAddLicence = (RelativeLayout) findViewById(R.id.RLAddLicence);
        RLEmpty = (RelativeLayout) findViewById(R.id.RLEmpty);
        tv_save_licence=(ProRegularTextView)findViewById(R.id.tv_save_licence);

        relative_dropdown=(RelativeLayout)findViewById(R.id.relative_dropdown);
        relative_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_dropdown.setBackgroundResource(R.drawable.background_solidorange_border);
            }
        });
        tv_save_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }
        });
        myLoader= new MyLoader(LicenceActivity.this);
        img_add_licence=(ImageView)findViewById(R.id.img_add_licence);
        tv_service=(ProRegularTextView)findViewById(R.id.tv_service);
        tv_issue=(ProLightEditText)findViewById(R.id.tv_issue);
        tv_licence_number=(ProLightEditText)findViewById(R.id.tv_licence_number);
        tv_expires=(ProRegularTextView)findViewById(R.id.tv_expires);
        dropdown=(ImageView)findViewById(R.id.dropdown);
        arrayList = new ArrayList<APIGetData>();
        APIGetData apiGetData = new APIGetData();
        apiGetData.setPARAMS("user_id");
        apiGetData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(apiGetData);
        arrayList1= new ArrayList<>();
        showData();
        rcv_licence_list = (RecyclerView) findViewById(R.id.rcv_licence_list);
        rcv_licence_list.setLayoutManager(new LinearLayoutManager(LicenceActivity.this));

        RLAddLicence.setVisibility(View.GONE);
        rcv_licence_list.setVisibility(View.GONE);
//        RLEmpty.setVisibility(View.VISIBLE);

        LLUpload = (LinearLayout) findViewById(R.id.LLUpload);
        LLEdit = (LinearLayout) findViewById(R.id.LLEdit);
        img_licence_file = (ImageView) findViewById(R.id.img_licence_file);

        licenceAdapter = new LicenceAdapter(LicenceActivity.this,arrayList1);
        rcv_licence_list.setAdapter(licenceAdapter);
        LLUpload.setVisibility(View.VISIBLE);
        LLEdit.setVisibility(View.GONE);
        Log.d("bdvfjdhgfjdgfjsgfhgsj",mycurrentphotopath);
        catagory();
        Log.d("ksjhddhfjkdhfjkdhjfhs", String.valueOf(arrayList1.size()));

            img_add_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RLAddLicence.getVisibility() == View.VISIBLE) {
                    RLAddLicence.setVisibility(View.GONE);
                    rcv_licence_list.setVisibility(View.VISIBLE);
                } else {
                    RLAddLicence.setVisibility(View.VISIBLE);
                    rcv_licence_list.setVisibility(View.GONE);
                    img_add_licence.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });

        findViewById(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCategory(view,catagory);
            }
        });
        tv_expires.setOnClickListener(new View.OnClickListener() {
            boolean check= true;
            @Override
            public void onClick(View view) {
                tv_expires.setBackgroundResource(R.drawable.background_solidorange_border);
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                Log.d("year--------->", "" + mYear);


                if (!check)
                {
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                if (!flag)
                {
                    mYear=year;
                    mMonth=month;
                    mDay=date;
                }
                DatePickerDialog datePickerDialog=new DatePickerDialog(LicenceActivity.this, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker datePicker, final int i, final int i1, final int i2) {

                        int selectedDate=datePicker.getDayOfMonth();

                        Log.d("selectedDate",""+selectedDate);
                        // datePicker.setMinDate(System.currentTimeMillis() - 1000);
                        Log.d("onDateSet","onDateSet");
                        String date1="";
                        year=i;
                        month=i1;
                        date=i2;
                        Log.d("year",""+year);
                        String s=i+"-"+month+"-"+i2;
                        Calendar calendar=Calendar.getInstance();
                        calendar.set(year,month,date);
                        Date date2=calendar.getTime();
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                        date1 = format.format(date2);
                        setDate(date1);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Cancel","Cancel");
                        check=false;
                    }
                });
                datePickerDialog.show();

            }
        });
        onSaveInstanceState(new Bundle());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPhotoDialog() {
        dialog = new Dialog(LicenceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_image_or_pdf);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       int height = displayMetrics.heightPixels/2 ;
       int width = displayMetrics.widthPixels/2;

        ImageView img_gallery = (ImageView) dialog.findViewById(R.id.img_gallery);
        ImageView img_pdf = (ImageView) dialog.findViewById(R.id.img_pdf);
        ProRegularTextView TXTTitle = (ProRegularTextView) dialog.findViewById(R.id.Title);

        LinearLayout LLMain = (LinearLayout) dialog.findViewById(R.id.LLMain);

       // LLMain.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(LicenceActivity.this)[1]) /2;
//        RLMain.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        LLMain.setMinimumWidth(height);
        LLMain.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(LicenceActivity.this)[1]) / 2;

        TXTTitle.setText("Licence");

        img_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION1);
                } else {
                    openImageGallery();
                }
            }
        });

        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION2);
                } else {
                    openPDFGallery();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImageGallery();
        }
        if (requestCode == REQUEST_WRITE_PERMISSION2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openPDFGallery();
        }
    }

    private void openImageGallery() {
        try {
            file = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        dialog.dismiss();
    }

    private void openPDFGallery() {
        try {
            file = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);

        dialog.dismiss();
    }

    File createImageFile() throws IOException {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());///new approach
        String imagefilename = "IMAGE_" + timestamp + "_";///new approach
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imagefilename, ".jpg", storageDirectory);/////new approach
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && file != null) {
            Uri selectedImageURI = data.getData();
               Log.d("mukdjfhfdl",mycurrentphotopath);
            try {
                file = new File(ImageFilePath.getPath(getApplicationContext(), selectedImageURI));


                if (file.getAbsolutePath().contains(".jpeg") || file.getAbsolutePath().contains(".png")
                        || file.getAbsolutePath().contains(".jpg")) {
                    mycurrentphotopath=file.getAbsolutePath();

                    Log.d("my important path",mycurrentphotopath);
                    LLUpload.setVisibility(View.GONE);
                    LLEdit.setVisibility(View.VISIBLE);
                    img_licence_file.setImageResource(android.R.color.transparent);
                    ((ProRegularTextView) findViewById(R.id.tv_file_name)).setText(file.getName());
                    Glide.with(getApplicationContext()).load(file).into(img_licence_file);
                } else {
                    Toast.makeText(getApplicationContext(), "This is not an image", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && file != null) {
            Uri selectedDataURI = data.getData();
            try {
                Log.i("selectedDataURI", "" + selectedDataURI);
                file = new File(ImageFilePath.getPath(getApplicationContext(), selectedDataURI));
                Log.i("path", "" + file.getAbsolutePath());
                if (file.getAbsolutePath().contains(".pdf")) {
                    mycurrentphotopath=file.getAbsolutePath();
                    LLUpload.setVisibility(View.GONE);
                    LLEdit.setVisibility(View.VISIBLE);
                    img_licence_file.setImageResource(android.R.color.transparent);
                    img_licence_file.setImageResource(R.drawable.ic_pdf);
                    ((ProRegularTextView) findViewById(R.id.tv_file_name)).setText(file.getName());
//                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(img_licence_file);
//                    Glide.with(getApplicationContext()).load(R.drawable.ic_pdf).into(imageViewTarget);
//
//                    Glide.with(getApplicationContext()).load(R.drawable.ic_pdf).into(img_licence_file);
                } else {
                    Toast.makeText(getApplicationContext(), "This is not a pdf file", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void showData()
    {
     new CustomJSONParser().fireAPIForGetMethod(LicenceActivity.this, AppConstant.liencelist, arrayList, new CustomJSONParser.CustomJSONResponse() {
         @Override
         public void onSuccess(String result) {
             Log.d("result",result);
             myLoader.dismissLoader();
             try {
                 JSONObject job= new JSONObject(result);
                 JSONArray info_arry=job.getJSONArray("info_array");
                 Log.d("infoo", String.valueOf(info_arry));
                 for (int i=0;i<info_arry.length();i++)
                 {
                     JSONObject jo= info_arry.getJSONObject(i);
                     showlicencesetget show= new showlicencesetget();
                     show.setId(jo.getString("id"));
                     show.setPros_id(jo.getString("pros_id"));
                     show.setImage_info(jo.getString("image_info"));
                     show.setLicense_issuer(jo.getString("license_issuer"));
                     show.setLicenses_no(jo.getString("licenses_no"));
                     show.setDate_expire(jo.getString("date_expire"));
                     show.setDate_upload(jo.getString("date_upload"));
                     JSONObject catagory= jo.getJSONObject("category");
                     show.setCategory_id(catagory.getString("id"));
                     show.setCategory_name(catagory.getString("name"));
                     arrayList1.add(show);

                 }
                 if (licenceAdapter==null)
                 {
                     RLEmpty.setVisibility(View.VISIBLE);
                     rcv_licence_list.setVisibility(View.GONE);
                 }
                 else
                 {
                     licenceAdapter = new LicenceAdapter(LicenceActivity.this,arrayList1);
                     rcv_licence_list.setAdapter(licenceAdapter);
                     RLEmpty.setVisibility(View.GONE);
                     rcv_licence_list.setVisibility(View.VISIBLE);
                 }
                  arrlistsize=arrayList1.size();
                 if (arrayList1.size()==2)
                 {
                     img_add_licence.setVisibility(View.GONE);
                 }
                 else
                 {
                     img_add_licence.setVisibility(View.VISIBLE);

                 }





                 Log.d("size", String.valueOf(arrayList1.size()));

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
             CustomAlert_error customAlert = new CustomAlert_error(LicenceActivity.this,"Load Error",""+error, (MyCustomAlertListener) LicenceActivity.this);
             customAlert.getListenerRetryCancelFromNormalAlert("retry","abort",1);

         }

         @Override
         public void onStart() {
           myLoader.showLoader();

         }
     });
    }
    public  void catagory()
    {
        new CustomJSONParser().fireAPIForGetMethod(LicenceActivity.this, AppConstant.catagory, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                // Log.d("responese",result);
                try {
                    JSONObject job= new JSONObject(result);
                    catagory= job.getJSONArray("info_array");
                    Log.d("sjhdfkhskhf", String.valueOf(catagory));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error, String response) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onStart() {

            }
        });

    }
    private void showDialogCategory(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(LicenceActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView =   LicenceActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(LicenceActivity.this));

        custom = new CustomListAdapterDialog_catagory(LicenceActivity.this,PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_service.setText(value.getString("category_name"));
                    pros_contact_service = value.getString("id");
                    Log.d("id",pros_contact_service);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rcv_.setAdapter(custom);
        // some other visual settings
        popupWindow.setFocusable(false);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(dailogView);
        popupWindow.showAsDropDown(v, -5, 0);

    }

    public void savedata()
    {
       String uid=ProApplication.getInstance().getUserId();
       String issuere=tv_issue.getText().toString().trim();
       String license=tv_licence_number.getText().toString().trim();
       String expire=tv_expires.getText().toString().trim();

        if (tv_service.getText().toString().trim().equals(""))
        {
            Toast.makeText(LicenceActivity.this, "Select Category", Toast.LENGTH_SHORT).show();

        }
        else
        {
            if (issuere.equals(""))
            {
                tv_issue.setError("Enter issuer name");
                tv_issue.setFocusable(true);
            }
            else
            {
                if (license.equals(""))
                {
                    tv_licence_number.setError("Enter licence number");
                    tv_licence_number.setFocusable(true);
                }
                else
                {
                    if (expire.equals(""))
                    {
                        Toast.makeText(LicenceActivity.this, "Select Expires date", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Log.d("my save path", String.valueOf(file));
                        Log.d("uid",uid);
                        Log.d("catid",pros_contact_service);
                        Log.d("issuer",issuere);
                        Log.d("license",license);
                        Log.d("expire",expire);

                        ArrayList<SetGetAPIPostData> arrayListPostParamsValues=new ArrayList<>();


                        SetGetAPIPostData setGetAPIPostData=new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("user_id");
                        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData=new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("cat_id");
                        setGetAPIPostData.setValues(pros_contact_service);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData=new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("license_issuer");
                        setGetAPIPostData.setValues(issuere);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData=new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("licenses_no");
                        setGetAPIPostData.setValues(license);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData=new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("date_expire");
                        setGetAPIPostData.setValues(expire);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        ArrayList<File> filesImages=new ArrayList<>();
                        filesImages.add(file);


                        CustomJSONParser.ImageParam="image_info";


                        new CustomJSONParser().APIForWithPhotoPostMethod(LicenceActivity.this, AppConstant.licenseadd, arrayListPostParamsValues, filesImages, new CustomJSONParser.CustomJSONResponse() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("result",result);
                                myLoader.dismissLoader();
                                try {
                                    JSONObject jo= new JSONObject(result);
                                    String msg= jo.getString("message");
                                    Toast.makeText(LicenceActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                    RLAddLicence.setVisibility(View.GONE);
                                    rcv_licence_list.setVisibility(View.VISIBLE);
                                    arrayList1.clear();
                                    showData();

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
                }
            }
        }

    }
    private void setDate(String date1) {
        Log.d("setDate","setDate");
        flag=false;
        tv_expires.setText(date1);
    }

    @Override
    public void callbackForAlert(String result, int i) {

    }
}
