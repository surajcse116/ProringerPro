package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogCategory;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImage;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImageView;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwo;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.ImageTakerActivityCamera;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class LicenceEditActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 5;
    private static final int PICK_IMAGE = 3;

    RelativeLayout RLAddLicence, RLEmpty, relative_dropdown;
    ProRegularTextView tv_service, tv_save_licence;
    ProRegularEditText tv_issue, tv_licence_number;
    ProRegularTextView tv_expires;
    File file = null;
    MyLoader myLoader;
    LinearLayout LLEdit;
    ImageView img_licence_file;

    String mycurrentphotopath = "";
    String pros_contact_service = "";
    CustomListAdapterDialogCategory custom = null;
    PopupWindow popupWindow;
    ImageView img_delete_licence;
    JSONArray categoryJsonArray=null;
    private int mYear = 0, mMonth = 0, mDay = 0, mHour, mMinute;
    boolean flag = true;
    int year;
    int month, date;
    String id, photo;
    boolean imgcheeking = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenceedit);
        String name = getIntent().getExtras().getString("category");
        String issue = getIntent().getExtras().getString("issuer");
        String licenseno = getIntent().getExtras().getString("licenseno");
        String expiredate = getIntent().getExtras().getString("expiredate");
        id = getIntent().getExtras().getString("id");
        photo = getIntent().getExtras().getString("photo");
        String catid = getIntent().getExtras().getString("catid");

        Logger.printMessage("name", name);
        Logger.printMessage("id", id);
        Logger.printMessage("catid", catid);

        RLAddLicence = (RelativeLayout) findViewById(R.id.RLAddLicence);
        RLEmpty = (RelativeLayout) findViewById(R.id.RLEmpty);
        tv_save_licence = (ProRegularTextView) findViewById(R.id.tv_save_licence);
        img_licence_file = (ImageView) findViewById(R.id.img_licence_file);
        relative_dropdown = (RelativeLayout) findViewById(R.id.relative_dropdown);
        img_delete_licence = (ImageView) findViewById(R.id.img_delete_licence);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myLoader = new MyLoader(LicenceEditActivity.this);
        relative_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_dropdown.setBackgroundResource(R.drawable.background_solidorange_border);

                if (categoryJsonArray.length()>0) {
                    showDialogCategory(view, categoryJsonArray);
                }else {
                    Toast.makeText(LicenceEditActivity.this,"Please add service in service page",Toast.LENGTH_SHORT).show();
                }
            }
        });

        category();

        file = new File(photo);

        pros_contact_service = catid;
        tv_service = (ProRegularTextView) findViewById(R.id.tv_service);
        tv_service.setText(name);
        tv_issue = (ProRegularEditText) findViewById(R.id.tv_issue);
        tv_issue.setText(issue);
        tv_licence_number = (ProRegularEditText) findViewById(R.id.tv_licence_number);
        tv_licence_number.setText(licenseno);
        tv_expires = (ProRegularTextView) findViewById(R.id.tv_expires);
        tv_expires.setText(expiredate);
        Glide.with(LicenceEditActivity.this).load(photo).into(img_licence_file);
        LLEdit = (LinearLayout) findViewById(R.id.LLEdit);
        findViewById(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LicenceEditActivity.this, PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                startActivityForResult(intent, 200);

            }
        });

        tv_expires.setOnClickListener(new View.OnClickListener() {
            boolean check = true;

            @Override
            public void onClick(View view) {
                tv_expires.setBackgroundResource(R.drawable.background_solidorange_border);
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                Logger.printMessage("year--------->", "" + mYear);


                if (!check) {
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                if (!flag) {
                    mYear = year;
                    mMonth = month;
                    mDay = date;
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(LicenceEditActivity.this, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker datePicker, final int i, final int i1, final int i2) {

                        int selectedDate = datePicker.getDayOfMonth();

                        Logger.printMessage("selectedDate", "" + selectedDate);
                        // datePicker.setMinDate(System.currentTimeMillis() - 1000);
                        Logger.printMessage("onDateSet", "onDateSet");
                        String date1 = "";
                        year = i;
                        month = i1;
                        date = i2;
                        Logger.printMessage("year", "" + year);
                        String s = i + "-" + month + "-" + i2;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, date);
                        Date date2 = calendar.getTime();
                        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                        date1 = format.format(date2);
                        setDate(date1);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Logger.printMessage("Cancel", "Cancel");
                        check = false;
                    }
                });
                datePickerDialog.show();

            }
        });
        onSaveInstanceState(new Bundle());
        tv_save_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgcheeking) {
                    file = null;
                    saveData(file);
                } else {
                    saveData(file);
                }
            }
        });

        img_delete_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlert customAlert = new CustomAlert();

                customAlert.getEventFromNormalAlert(LicenceEditActivity.this, "delete", "Are you sure you want to delete this licence?", "Ok", "Cancel", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        delete();
                    }
                    @Override
                    public void callBackCancel() {

                    }
                });
            }
        });

    }

    private void showImagePickerOption() {
        final Dialog dialog = new Dialog(LicenceEditActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_gallery_camera);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        RelativeLayout RLMain = (RelativeLayout) dialog.findViewById(R.id.RLMain);

        RLMain.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(LicenceEditActivity.this)[1]);
//        RLMain.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        RLMain.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(LicenceEditActivity.this)[1]) / 2;


        dialog.findViewById(R.id.img_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                if (intent.resolveActivity((LicenceEditActivity.this).getPackageManager()) != null) {
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            }
        });

        dialog.findViewById(R.id.img_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ProConstant.cameraRequested = true;
                startActivityForResult(new Intent(LicenceEditActivity.this, ImageTakerActivityCamera.class), REQUEST_IMAGE_CAPTURE);
            }
        });

        dialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
//                                    intent.putExtra("editTextValue", "value_here")
            setResult(RESULT_CANCELED, intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogCategory(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(LicenceEditActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = LicenceEditActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(LicenceEditActivity.this));

        custom = new CustomListAdapterDialogCategory(LicenceEditActivity.this, PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_service.setText(value.getString("category_name"));
                    pros_contact_service = value.getString("id");
                    Logger.printMessage("id", pros_contact_service);
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
        popupWindow.showAsDropDown(v, 0, 0);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Logger.printMessage("resultCode", "requestCode " + requestCode + " &b resultcode :: " + resultCode);
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//            Logger.printMessage("image****", "" + data.getData());
//            try {
//                imgcheeking=false;
//                Uri uri = data.getData();
//                File dataFile = new File(getRealPathFromURI(uri));
//                if (!dataFile.exists())
//                    Logger.printMessage("image****", "data file does not exists");
//                mycurrentphotopath = dataFile.getAbsolutePath();
//
//                file=new File(mycurrentphotopath);
//
//                if (file.getAbsolutePath().contains(".jpeg") || file.getAbsolutePath().contains(".png")
//                        || file.getAbsolutePath().contains(".jpg")) {
//                    mycurrentphotopath = file.getAbsolutePath();
//
//                    Logger.printMessage("photoPath-->", mycurrentphotopath);
//                    LLEdit.setVisibility(View.VISIBLE);
//                    img_licence_file.setImageResource(android.R.color.transparent);
//                    ((ProRegularTextView) findViewById(R.id.tv_file_name)).setText(file.getName());
//                    Glide.with(getApplicationContext()).load(file).into(img_licence_file);
//                } else {
//                    Toast.makeText(getApplicationContext(), "This is not an image", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (data != null) {
//                        imgcheeking=false;
//                        mycurrentphotopath = data.getExtras().get("data").toString();
//                        file=new File(mycurrentphotopath);
//                        Logger.printMessage("image****", "" + mycurrentphotopath);
//
//                        Logger.printMessage("photoPath-->", mycurrentphotopath);
//                        LLEdit.setVisibility(View.VISIBLE);
//                        img_licence_file.setImageResource(android.R.color.transparent);
//                        ((ProRegularTextView) findViewById(R.id.tv_file_name)).setText(file.getName());
//                        Glide.with(getApplicationContext()).load(file).into(img_licence_file);
//
//                    }
//                }
//            }, 800);
//        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imgcheeking=false;
                mycurrentphotopath = result.getUri().toString();
                Logger.printMessage("path-->", mycurrentphotopath);
                file=new File(mycurrentphotopath);
                LLEdit.setVisibility(View.VISIBLE);
                img_licence_file.setImageResource(android.R.color.transparent);
                Glide.with(getApplicationContext()).load(mycurrentphotopath).into(img_licence_file);
//                ((ProRegularTextView) findViewById(R.id.tv_file_name)).setText(file.getName());
                //Toast.makeText(LicenceEditActivity.this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(LicenceEditActivity.this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode == 200 && resultCode == RESULT_OK) {
//            showImagePickerOption();
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
                .setAspectRatio(1, 1)
                .getIntent(LicenceEditActivity.this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }



    public String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    private void setDate(String date) {
        Logger.printMessage("setDate", "setDate");
        flag = false;
        tv_expires.setText(date);
    }

    public void category() {

        ArrayList<SetGetAPIPostData> setGetAPIArrayList = new ArrayList<>();

        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());

        setGetAPIArrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(LicenceEditActivity.this, ProConstant.app_pro_services, setGetAPIArrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                // Logger.printMessage("responese",result);
                myLoader.dismissLoader();
                try {
                    JSONObject job = new JSONObject(result);

                    categoryJsonArray=new JSONArray();

                    for (int i=0;i<job.getJSONArray("info_array").length();i++){

                        categoryJsonArray.put(job.getJSONArray("info_array").getJSONObject(i));

//                        for (int j=0;j<job.getJSONArray("info_array").getJSONObject(i).getJSONArray("getSubcategory").length();j++){
//
//                            if (job.getJSONArray("info_array").getJSONObject(i).getJSONArray("getSubcategory").getJSONObject(j).getInt("cat_selected")==1){
//
//                                categoryJsonArray.put(job.getJSONArray("info_array").getJSONObject(i).getJSONArray("getSubcategory").getJSONObject(j));
//                            }
//
//                        }
                    }
                    //categoryJsonArray = job.getJSONArray("info_array");
                    Logger.printMessage("CategoryArray", String.valueOf(categoryJsonArray));

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

    public void saveData(File file) {
        String uid = ProApplication.getInstance().getUserId();
        String issuere = tv_issue.getText().toString().trim();
        String license = tv_licence_number.getText().toString().trim();
        String expire = tv_expires.getText().toString().trim();

        if (tv_service.getText().toString().trim().equals("")) {
            Toast.makeText(LicenceEditActivity.this, "Select Category", Toast.LENGTH_SHORT).show();

        } else {
            if (issuere.equals("")) {
                tv_issue.setError("Enter issuer name");
                tv_issue.requestFocus();
            } else {
                tv_issue.setError(null);
                tv_issue.clearFocus();
                if (license.equals("")) {
                    tv_licence_number.setError("Enter licence number");
                    tv_licence_number.requestFocus();
                } else {
                    tv_licence_number.setError(null);
                    tv_licence_number.clearFocus();
                    if (expire.equals("")) {
                        Toast.makeText(LicenceEditActivity.this, "Select Expires date", Toast.LENGTH_SHORT).show();
                    } else {
                        Logger.printMessage("uid", uid);
                        Logger.printMessage("license_id", id);
                        Logger.printMessage("catid", pros_contact_service);
                        Logger.printMessage("issuer", issuere);
                        Logger.printMessage("license", license);
                        Logger.printMessage("expire", expire);
                        if (file != null) {
                            Logger.printMessage("my save path", String.valueOf(file));
                        }
                        ArrayList<SetGetAPIPostData> arrayListPostParamsValues = new ArrayList<>();


                        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("user_id");
                        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData = new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("license_id");
                        setGetAPIPostData.setValues(id);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData = new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("cat_id");
                        setGetAPIPostData.setValues(pros_contact_service);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData = new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("license_issuer");
                        setGetAPIPostData.setValues(issuere);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData = new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("licenses_no");
                        setGetAPIPostData.setValues(license);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        setGetAPIPostData = new SetGetAPIPostData();
                        setGetAPIPostData.setPARAMS("date_expire");
                        setGetAPIPostData.setValues(expire);
                        arrayListPostParamsValues.add(setGetAPIPostData);

                        ArrayList<File> filesImages=null;
                        if (file!=null) {
                            filesImages = new ArrayList<>();
                            filesImages.add(file);
                        }



                        CustomJSONParser.ImageParam = "image_info";


                        new CustomJSONParser().APIForWithPhotoPostMethod(LicenceEditActivity.this, ProConstant.app_prolicense_edit, arrayListPostParamsValues, filesImages, new CustomJSONParser.CustomJSONResponse() {
                            @Override
                            public void onSuccess(String result) {
                                Logger.printMessage("result", result);
                                myLoader.dismissLoader();
                                try {
                                    JSONObject jo = new JSONObject(result);
                                    String msg = jo.getString("message");
                                    Toast.makeText(LicenceEditActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent();
//                                    intent.putExtra("editTextValue", "value_here")
                                    setResult(RESULT_OK, intent);
                                    finish();

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

    public void delete() {
        String licenseid = id;
        Logger.printMessage("lincense", licenseid);
        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id", ProApplication.getInstance().getUserId());
        Params.put("license_id", licenseid);
        new CustomJSONParser().fireAPIForPostMethod(LicenceEditActivity.this, ProConstant.app_prolicense_delete, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("result", result);
                try {
                    JSONObject job = new JSONObject(result);
                    String msg = job.getString("message");
                    Toast.makeText(LicenceEditActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
//                                    intent.putExtra("editTextValue", "value_here")
                    setResult(RESULT_OK, intent);
                    finish();

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

    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
