package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwoFragment;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.ImageTakerActivityCamera;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
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

/**
 * Created by su on 12/7/17.
 */

public class LicenceAddActivity extends AppCompatActivity {

    String mycurrentphotopath = "";
    String catid = "";
    LinearLayout LLEdit, LLUpload;
    ImageView img_licence_file;
    boolean flag = true;

    private static final int REQUEST_IMAGE_CAPTURE = 5;
    private static final int PICK_IMAGE = 3;

    File file = null;

    RelativeLayout relative_dropdown;

    ProRegularTextView tv_service, tv_save_licence,tv_expires;
    ProRegularEditText tv_issuer,tv_licence_number;

    private int mYear = 0, mMonth = 0, mDay = 0, mHour, mMinute;
    int month, date,year;

    MyLoader myLoader;

    JSONArray categoryJsonArray=null;
    PopupWindow popupWindow;
    CustomListAdapterDialogCategory custom = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_licence);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_save_licence = (ProRegularTextView) findViewById(R.id.tv_save_licence);
        tv_service = (ProRegularTextView) findViewById(R.id.tv_service);
        tv_issuer = (ProRegularEditText) findViewById(R.id.tv_issuer);
        tv_licence_number = (ProRegularEditText) findViewById(R.id.tv_licence_number);
        tv_expires = (ProRegularTextView) findViewById(R.id.tv_expires);
        img_licence_file = (ImageView) findViewById(R.id.img_licence_file);
        LLUpload = (LinearLayout) findViewById(R.id.LLUpload);
        LLEdit = (LinearLayout) findViewById(R.id.LLEdit);


        LLUpload.setVisibility(View.VISIBLE);
        LLEdit.setVisibility(View.GONE);

        myLoader = new MyLoader(LicenceAddActivity.this);

        tv_save_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LicenceAddActivity.this, PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                startActivityForResult(intent, 200);
            }
        });


        relative_dropdown = (RelativeLayout) findViewById(R.id.relative_dropdown);


        relative_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_dropdown.setBackgroundResource(R.drawable.background_solidorange_border);
                if (categoryJsonArray.length()>0) {
                    showDialogCategory(view, categoryJsonArray);
                }else {
                    Toast.makeText(LicenceAddActivity.this,"Please add service in service page",Toast.LENGTH_SHORT).show();
                }
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(LicenceAddActivity.this, new DatePickerDialog.OnDateSetListener() {


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

        category();
    }


    private void showImagePickerOption() {
        final Dialog dialog = new Dialog(LicenceAddActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_gallery_camera);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        RelativeLayout RLMain = (RelativeLayout) dialog.findViewById(R.id.RLMain);

        RLMain.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(LicenceAddActivity.this)[1]);
//        RLMain.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        RLMain.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(LicenceAddActivity.this)[1]) / 2;


        dialog.findViewById(R.id.img_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                if (intent.resolveActivity((LicenceAddActivity.this).getPackageManager()) != null) {
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
                startActivityForResult(new Intent(LicenceAddActivity.this, ImageTakerActivityCamera.class), REQUEST_IMAGE_CAPTURE);
            }
        });

        dialog.show();
    }

    public void saveData() {

        String expire = tv_expires.getText().toString().trim();

        if (tv_service.getText().toString().trim().equals("")) {
            Toast.makeText(LicenceAddActivity.this, "Select Category", Toast.LENGTH_SHORT).show();

        } else {
            if (tv_issuer.getText().toString().trim().equals("")) {
                tv_issuer.setError("Enter issuer name");
                tv_issuer.requestFocus();
            } else {
                tv_issuer.setError(null);
                tv_issuer.clearFocus();
                if (tv_licence_number.getText().toString().trim().equals("")) {
                    tv_licence_number.setError("Enter licence number");
                    tv_licence_number.requestFocus();
                } else {
                    tv_licence_number.setError(null);
                    tv_licence_number.clearFocus();
                    if (expire.equals("")) {
                        Toast.makeText(LicenceAddActivity.this, "Select Expires date", Toast.LENGTH_SHORT).show();
                    } else {

                        if (file == null) {
                            Toast.makeText(LicenceAddActivity.this, "please choose file to upload", Toast.LENGTH_SHORT).show();
                        } else {
                            Logger.printMessage("my save path", String.valueOf(file));
                            Logger.printMessage("uid", ProApplication.getInstance().getUserId());
                            Logger.printMessage("catid", catid);
                            Logger.printMessage("issuer", tv_issuer.getText().toString().trim());
                            Logger.printMessage("license", tv_licence_number.getText().toString().trim());
                            Logger.printMessage("expire", expire);

                            ArrayList<SetGetAPIPostData> arrayListPostParamsValues = new ArrayList<>();


                            SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
                            setGetAPIPostData.setPARAMS("user_id");
                            setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
                            arrayListPostParamsValues.add(setGetAPIPostData);

                            setGetAPIPostData = new SetGetAPIPostData();
                            setGetAPIPostData.setPARAMS("cat_id");
                            setGetAPIPostData.setValues(catid);
                            arrayListPostParamsValues.add(setGetAPIPostData);

                            setGetAPIPostData = new SetGetAPIPostData();
                            setGetAPIPostData.setPARAMS("license_issuer");
                            setGetAPIPostData.setValues(tv_issuer.getText().toString().trim());
                            arrayListPostParamsValues.add(setGetAPIPostData);

                            setGetAPIPostData = new SetGetAPIPostData();
                            setGetAPIPostData.setPARAMS("licenses_no");
                            setGetAPIPostData.setValues(tv_licence_number.getText().toString().trim());
                            arrayListPostParamsValues.add(setGetAPIPostData);

                            setGetAPIPostData = new SetGetAPIPostData();
                            setGetAPIPostData.setPARAMS("date_expire");
                            setGetAPIPostData.setValues(expire);
                            arrayListPostParamsValues.add(setGetAPIPostData);

                            ArrayList<File> filesImages = new ArrayList<>();
                            filesImages.add(file);


                            CustomJSONParser.ImageParam = "image_info";

                            new CustomJSONParser().APIForWithPhotoPostMethod(LicenceAddActivity.this, ProConstant.app_prolicense_add, arrayListPostParamsValues, filesImages, new CustomJSONParser.CustomJSONResponse() {
                                @Override
                                public void onSuccess(String result) {
                                    Logger.printMessage("result", result);
                                    myLoader.dismissLoader();
                                    try {
                                        JSONObject jo = new JSONObject(result);
                                        String msg = jo.getString("message");
                                        Toast.makeText(LicenceAddActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent();
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

    }

    private void showDialogCategory(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(LicenceAddActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = LicenceAddActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(LicenceAddActivity.this));

        custom = new CustomListAdapterDialogCategory(LicenceAddActivity.this, PredictionsJsonArray, new RegistrationTwoFragment.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_service.setText(value.getString("category_name"));
                    catid = value.getString("id");
                    Logger.printMessage("catid-->", catid);
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

    public void category() {

        ArrayList<SetGetAPIPostData> setGetAPIArrayList = new ArrayList<>();

        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());

        setGetAPIArrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(LicenceAddActivity.this, ProConstant.app_pro_services, setGetAPIArrayList, new CustomJSONParser.CustomJSONResponse() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Logger.printMessage("resultCode", "requestCode " + requestCode + " &b resultcode :: " + resultCode);
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//            Logger.printMessage("image****", "" + data.getData());
//            try {
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
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mycurrentphotopath = result.getUri().toString();
                Logger.printMessage("path-->", mycurrentphotopath);
                file=new File(mycurrentphotopath);
                LLEdit.setVisibility(View.VISIBLE);
                img_licence_file.setImageResource(android.R.color.transparent);
                Glide.with(getApplicationContext()).load(mycurrentphotopath).into(img_licence_file);
//                ((ProRegularTextView) findViewById(R.id.tv_file_name)).setText(file.getName());
                //Toast.makeText(LicenceAddActivity.this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(LicenceAddActivity.this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
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
                .getIntent(LicenceAddActivity.this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    private void setDate(String date) {
        Logger.printMessage("setDate", "" + date);
        flag = false;
        tv_expires.setText(date);
//        String array[]=date.split("/");
//        tv_expires.setText(array[0]+"-"+array[1]+"-"+array[2]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
//            intent.putExtra("Value", "value_here");
            setResult(RESULT_CANCELED, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
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
}
