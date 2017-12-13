package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.PortFolioAddImageAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogCategory;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogMonthYear;
import com.android.llc.proringer.pro.proringerpro.adapter.PortFolioAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwo;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetShowPortFolio;
import com.android.llc.proringer.pro.proringerpro.utils.ImageTakerActivityCamera;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by su on 8/17/17.
 */

public class PortFolioActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 5;
    private static final int PICK_IMAGE = 3;
    ProRegularTextView tv_category, tv_month, tv_year;
    RecyclerView rcv_port_folio, rcv_add_port_folio;
    RelativeLayout RLAddPortFolio, RLEmpty;
    PortFolioAddImageAdapter portFolioAddImageAdapter = null;
    MyLoader myLoader;
    String catid = "", monthDigit = "", monthName = "", yearName = "", yearDigit = "";
    public JSONArray categoryJsonArray;
    PopupWindow popupWindow;
    CustomListAdapterDialogCategory customListAdapterDialogCategory = null;
    CustomListAdapterDialogMonthYear customListAdapterDialogMonthYear = null;
    PortFolioAdapter portfolio;
    ArrayList<String> portPolioImageGalleryArrayList = null;
    ArrayList<SetGetAPI> arrayList = null;
    ArrayList<SetGetShowPortFolio> showPortFolioArrayList = null;
    private String mCurrentPhotoPath = "";
    RelativeLayout relative_category_dropdown, relative_month_dropdown, relative_year_dropdown;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RLAddPortFolio = (RelativeLayout) findViewById(R.id.RLAddPortFolio);
        RLEmpty = (RelativeLayout) findViewById(R.id.RLEmpty);

        relative_category_dropdown = (RelativeLayout) findViewById(R.id.relative_category_dropdown);
        relative_month_dropdown = (RelativeLayout) findViewById(R.id.relative_month_dropdown);
        relative_year_dropdown = (RelativeLayout) findViewById(R.id.relative_year_dropdown);

        tv_category = (ProRegularTextView) findViewById(R.id.tv_category);
        tv_month = (ProRegularTextView) findViewById(R.id.tv_month);
        tv_year = (ProRegularTextView) findViewById(R.id.tv_year);

        myLoader = new MyLoader(PortFolioActivity.this);
        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        setGetAPI.setPARAMS("user_id");
        arrayList.add(setGetAPI);
        showPortFolioArrayList = new ArrayList<>();
        portPolioImageGalleryArrayList = new ArrayList<>();

        rcv_port_folio = (RecyclerView) findViewById(R.id.rcv_port_folio);
        rcv_port_folio.setLayoutManager(new LinearLayoutManager(PortFolioActivity.this));
        rcv_add_port_folio = (RecyclerView) findViewById(R.id.rcv_add_port_folio);
        rcv_add_port_folio.setLayoutManager(new GridLayoutManager(PortFolioActivity.this, 5));

        RLAddPortFolio.setVisibility(View.GONE);
        rcv_port_folio.setVisibility(View.GONE);
        //RLEmpty.setVisibility(View.VISIBLE);

        findViewById(R.id.img_add_port_folio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (RLAddPortFolio.getVisibility() == View.VISIBLE) {
                    RLAddPortFolio.setVisibility(View.GONE);
                    rcv_port_folio.setVisibility(View.VISIBLE);
                    findViewById(R.id.img_add_port_folio).setVisibility(View.VISIBLE);
                } else {
                    RLAddPortFolio.setVisibility(View.VISIBLE);
                    rcv_port_folio.setVisibility(View.GONE);
                    findViewById(R.id.img_add_port_folio).setVisibility(View.GONE);
                }

            }
        });

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PortFolioActivity.this, PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                startActivityForResult(intent, 200);
            }
        });

        relative_month_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_category_dropdown.setBackgroundResource(R.drawable.edit_text_selecter);
                relative_month_dropdown.setBackgroundResource(R.drawable.background_solidorange_border);
                relative_year_dropdown.setBackgroundResource(R.drawable.edit_text_selecter);

                JSONArray jsonArray = new JSONArray();
                JSONObject question = new JSONObject();
                try {
                    question.put("name", "January");
                    question.put("digit", "1");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "February");
                    question.put("digit", "2");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "March");
                    question.put("digit", "3");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "April");
                    question.put("digit", "4");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "May");
                    question.put("digit", "5");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "June");
                    question.put("digit", "6");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "July");
                    question.put("digit", "7");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "August");
                    question.put("digit", "8");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "September");
                    question.put("digit", "9");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "October");
                    question.put("digit", "10");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "November");
                    question.put("digit", "11");
                    jsonArray.put(question);

                    question = new JSONObject();
                    question.put("name", "December");
                    question.put("digit", "12");
                    jsonArray.put(question);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                showMonthDialog(view, jsonArray);
            }
        });

        relative_category_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_category_dropdown.setBackgroundResource(R.drawable.background_solidorange_border);
                relative_month_dropdown.setBackgroundResource(R.drawable.edit_text_selecter);
                relative_year_dropdown.setBackgroundResource(R.drawable.edit_text_selecter);
                showDialogCategory(view, categoryJsonArray);
            }
        });

        relative_year_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_category_dropdown.setBackgroundResource(R.drawable.edit_text_selecter);
                relative_month_dropdown.setBackgroundResource(R.drawable.edit_text_selecter);
                relative_year_dropdown.setBackgroundResource(R.drawable.background_solidorange_border);

                int year = Calendar.getInstance().get(Calendar.YEAR);

                JSONArray jsonArray = new JSONArray();

                for (int i = 0; i < 50; i++) {
                    try {
                        JSONObject question = new JSONObject();
                        question.put("name", "" + year);
                        question.put("digit", "" + year);
                        jsonArray.put(question);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    year = year - 1;
                }
                showYearDialog(view, jsonArray);
            }
        });

        category();

        findViewById(R.id.tv_save_port_folio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationForPortFolio();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (RLAddPortFolio.getVisibility() == View.VISIBLE) {
                RLAddPortFolio.setVisibility(View.GONE);
                rcv_port_folio.setVisibility(View.VISIBLE);
                findViewById(R.id.img_add_port_folio).setVisibility(View.VISIBLE);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void showImagePickerOption() {
        final Dialog dialog = new Dialog(PortFolioActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_gallery_camera);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        RelativeLayout RLMain = (RelativeLayout) dialog.findViewById(R.id.RLMain);

        RLMain.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(PortFolioActivity.this)[1]);
//        RLMain.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        RLMain.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(PortFolioActivity.this)[1]) / 2;


        dialog.findViewById(R.id.img_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                if (intent.resolveActivity((PortFolioActivity.this).getPackageManager()) != null) {
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
                startActivityForResult(new Intent(PortFolioActivity.this, ImageTakerActivityCamera.class), REQUEST_IMAGE_CAPTURE);
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.printMessage("resultCode", "requestCode " + requestCode + " &b resultcode :: " + resultCode);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Logger.printMessage("image****", "" + data.getData());
            try {
                Uri uri = data.getData();
                File dataFile = new File(getRealPathFromURI(uri));
                if (!dataFile.exists())
                    Logger.printMessage("image****", "data file does not exists");
                mCurrentPhotoPath = dataFile.getAbsolutePath();

                Logger.printMessage("list_size", "" + portPolioImageGalleryArrayList.size());

                portPolioImageGalleryArrayList.add(mCurrentPhotoPath);

                if (portFolioAddImageAdapter == null) {
                    portFolioAddImageAdapter = new PortFolioAddImageAdapter(PortFolioActivity.this, portPolioImageGalleryArrayList);
                    rcv_add_port_folio.setAdapter(portFolioAddImageAdapter);
                } else {
                    portFolioAddImageAdapter.notifyDataSetChanged();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (data != null) {
                        mCurrentPhotoPath = data.getExtras().get("data").toString();

                        Uri selectedDataURI = data.getData();
                        Logger.printMessage("selectedDataURI", "" + selectedDataURI);

                        Logger.printMessage("image****", "" + mCurrentPhotoPath);
                        portPolioImageGalleryArrayList.add(mCurrentPhotoPath);
                        if (portFolioAddImageAdapter == null) {
                            portFolioAddImageAdapter = new PortFolioAddImageAdapter(PortFolioActivity.this, portPolioImageGalleryArrayList);
                            rcv_add_port_folio.setAdapter(portFolioAddImageAdapter);
                        } else {
                            portFolioAddImageAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }, 800);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            if (portPolioImageGalleryArrayList.size() < 10) {
                showImagePickerOption();
            } else {
                Toast.makeText(PortFolioActivity.this, "You can't select pictures more than 10", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==111){
            if(resultCode == RESULT_OK) {
//                String strEditText = data.getStringExtra("TextValue");
                showPortFolioArrayList.clear();
                showData();
            }
        }
    }


    public void category() {
        new CustomJSONParser().fireAPIForGetMethod(PortFolioActivity.this, ProConstant.catagory, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                // Log.d("responese",result);
                myLoader.dismissLoader();
                try {
                    JSONObject job = new JSONObject(result);
                    categoryJsonArray = job.getJSONArray("info_array");
                    Logger.printMessage("CategoryArray", String.valueOf(categoryJsonArray));
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

    public void showMonthDialog(View v, JSONArray PredictionsJsonArray) {
        popupWindow = new PopupWindow(PortFolioActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = PortFolioActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(PortFolioActivity.this));

        customListAdapterDialogMonthYear = new CustomListAdapterDialogMonthYear(PortFolioActivity.this, PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_month.setText(value.getString("name"));
                    monthName = value.getString("name");
                    monthDigit = value.getString("digit");
                    Logger.printMessage("monthDigit-->", monthDigit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rcv_.setAdapter(customListAdapterDialogMonthYear);
        // some other visual settings
        popupWindow.setFocusable(false);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(dailogView);
        popupWindow.showAsDropDown(v, -5, 0);
    }

    public void showYearDialog(View v, JSONArray PredictionsJsonArray) {
        popupWindow = new PopupWindow(PortFolioActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = PortFolioActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(PortFolioActivity.this));

        customListAdapterDialogMonthYear = new CustomListAdapterDialogMonthYear(PortFolioActivity.this, PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_year.setText(value.getString("name"));
                    yearName = value.getString("name");
                    yearDigit = value.getString("digit");
                    Logger.printMessage("yearDigit-->", yearDigit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rcv_.setAdapter(customListAdapterDialogMonthYear);
        // some other visual settings
        popupWindow.setFocusable(false);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(dailogView);
        popupWindow.showAsDropDown(v, -5, 0);
    }

    private void showDialogCategory(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(PortFolioActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = PortFolioActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(PortFolioActivity.this));

        customListAdapterDialogCategory = new CustomListAdapterDialogCategory(PortFolioActivity.this, PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_category.setText(value.getString("category_name"));
                    catid = value.getString("id");
                    Logger.printMessage("catid-->", catid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rcv_.setAdapter(customListAdapterDialogCategory);
        // some other visual settings
        popupWindow.setFocusable(false);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(dailogView);
        popupWindow.showAsDropDown(v, -5, 0);

    }

    public void showData() {
        new CustomJSONParser().fireAPIForGetMethod(PortFolioActivity.this, ProConstant.portfoliolist, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                try {
                    JSONObject info = new JSONObject(result);
                    JSONArray infoarray = info.getJSONArray("info_array");
                    for (int i = 0; i < infoarray.length(); i++) {
                        JSONObject jo = infoarray.getJSONObject(i);
                        SetGetShowPortFolio show = new SetGetShowPortFolio();
                        show.setId(jo.getString("id"));
                        show.setPros_id(jo.getString("pros_id"));
                        show.setGallery_image(jo.getString("gallery_images"));
                        show.setProject_month(jo.getString("project_month"));
                        show.setProject_year(jo.getString("project_year"));
                        show.setCount_images(jo.getString("count_images"));
                        show.setMultiple_gallery_image(jo.getJSONArray("multiple_gallery_image"));
                        JSONObject cat = jo.getJSONObject("category");
                        show.setCategory_id(cat.getString("category_id"));
                        show.setCategory_name(cat.getString("category_name"));
                        showPortFolioArrayList.add(show);
                    }
                    Logger.printMessage("arrlist", String.valueOf(showPortFolioArrayList.size()));
                    if (showPortFolioArrayList.size() == 0) {
                        RLEmpty.setVisibility(View.VISIBLE);
                        rcv_port_folio.setVisibility(View.GONE);
                    } else {
                        RLEmpty.setVisibility(View.GONE);
                        rcv_port_folio.setVisibility(View.VISIBLE);

                        if (portfolio == null) {
                            portfolio = new PortFolioAdapter(PortFolioActivity.this, showPortFolioArrayList);
                            rcv_port_folio.setAdapter(portfolio);
                        } else {
                            portfolio.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myLoader.dismissLoader();
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();

                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(PortFolioActivity.this, getResources().getString(R.string.text_location_permission), getResources().getString(R.string.text_location_permission), "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
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

    public void validationForPortFolio(){

        if (tv_category.getText().toString().equals("")){
            Toast.makeText(PortFolioActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();
        }else {

            if (tv_month.getText().toString().equals("")){
                Toast.makeText(PortFolioActivity.this, "Please Select Month", Toast.LENGTH_SHORT).show();
            }else {
                if (tv_year.getText().toString().equals("")){
                    Toast.makeText(PortFolioActivity.this, "Please Select Year", Toast.LENGTH_SHORT).show();
                }else {
                    if (portPolioImageGalleryArrayList.size()<1)
                    {
                        Toast.makeText(PortFolioActivity.this, "Please select at least one image", Toast.LENGTH_SHORT).show();
                    }else {
                        firePortFolioData();
                    }

                }
            }
        }
    }

    public void firePortFolioData(){

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
        setGetAPIPostData.setPARAMS("month");
        setGetAPIPostData.setValues(monthName);
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("year");
        setGetAPIPostData.setValues(yearName);
        arrayListPostParamsValues.add(setGetAPIPostData);

        ArrayList<File> filesImages = new ArrayList<>();

        for (int i=0;i<portPolioImageGalleryArrayList.size();i++){
            filesImages.add(new File(portPolioImageGalleryArrayList.get(i)));
        }

        CustomJSONParser.ImageParam = "gallery_image";

        new CustomJSONParser().APIForWithPhotosMultiplePostMethod(PortFolioActivity.this, ProConstant.proportfolio_add, arrayListPostParamsValues, filesImages, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                myLoader.dismissLoader();
                try {
                    JSONObject jo = new JSONObject(result);
                    String msg = jo.getString("message");
                    Toast.makeText(PortFolioActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    RLAddPortFolio.setVisibility(View.GONE);
                    rcv_port_folio.setVisibility(View.VISIBLE);
                    findViewById(R.id.img_add_port_folio).setVisibility(View.VISIBLE);


                    portPolioImageGalleryArrayList.clear();
                    if (portFolioAddImageAdapter != null) {
                        portFolioAddImageAdapter.notifyDataSetChanged();
                    }
                    tv_category.setText("");
                    tv_month.setText("");
                    tv_year.setText("");

                    catid="";
                    monthName="";
                    yearName="";

                    if (showPortFolioArrayList.size() > 0) {
                        showPortFolioArrayList.clear();
                    }
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
