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
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogCategory;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogMonthYear;
import com.android.llc.proringer.pro.proringerpro.adapter.PortFolioAddImageAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.PortFolioEditAddImageAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwo;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.ImageTakerActivityCamera;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by su on 12/8/17.
 */

public class PortfolioEditActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 5;
    private static final int PICK_IMAGE = 3;

    String port_id = "", catid = "",
            monthDigit = "", yearDigit = "";
    Dialog dialog = null;

    JSONArray multiple_gallery_image, categoryJsonArray;
    ProRegularTextView tv_category, tv_month, tv_year;
    RelativeLayout relative_category_dropdown, relative_month_dropdown, relative_year_dropdown;
    PopupWindow popupWindow;
    CustomListAdapterDialogMonthYear customListAdapterDialogMonthYear = null;
    CustomListAdapterDialogCategory customListAdapterDialogCategory = null;
    ArrayList<String> portPolioImageGalleryArrayList = null;

    PortFolioEditAddImageAdapter portFolioEditAddImageAdapter = null;

    RecyclerView rcv_add_port_folio;

    MyLoader myLoader;

    private String mCurrentPhotoPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_portfolio_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myLoader = new MyLoader(this);

        rcv_add_port_folio = (RecyclerView) findViewById(R.id.rcv_add_port_folio);
        rcv_add_port_folio.setLayoutManager(new GridLayoutManager(this, 5));

        relative_category_dropdown = (RelativeLayout) findViewById(R.id.relative_category_dropdown);
        relative_month_dropdown = (RelativeLayout) findViewById(R.id.relative_month_dropdown);
        relative_year_dropdown = (RelativeLayout) findViewById(R.id.relative_year_dropdown);

        tv_category = (ProRegularTextView) findViewById(R.id.tv_category);
        tv_month = (ProRegularTextView) findViewById(R.id.tv_month);
        tv_year = (ProRegularTextView) findViewById(R.id.tv_year);

        port_id = getIntent().getExtras().getString("id");
        catid = getIntent().getExtras().getString("cat_id");

        tv_month.setText(getIntent().getExtras().getString("month"));
        tv_year.setText(getIntent().getExtras().getString("year"));
        tv_category.setText(getIntent().getExtras().getString("category_name"));


        portPolioImageGalleryArrayList = new ArrayList<>();

        try {
            multiple_gallery_image = new JSONArray(getIntent().getExtras().getString("multiple_gallery_image"));
            categoryJsonArray = new JSONArray(getIntent().getExtras().getString("categoryJsonArray"));

            for (int i = 0; i < multiple_gallery_image.length(); i++) {
                portPolioImageGalleryArrayList.add(multiple_gallery_image.getString(i));
            }

            Logger.printMessage("imageGalleryCount-->", "" + portPolioImageGalleryArrayList.size());

            portFolioEditAddImageAdapter = new PortFolioEditAddImageAdapter(PortfolioEditActivity.this, portPolioImageGalleryArrayList);
            rcv_add_port_folio.setAdapter(portFolioEditAddImageAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


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

        findViewById(R.id.img_delete_port_folio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(PortfolioEditActivity.this, "Delete", "Are you sure to delete " + tv_category.getText().toString().trim() + " gallery", "YES,DELETE IT", "CANCEL", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {
                        deletePortFolio();
                    }

                    @Override
                    public void callBackCancel() {

                    }
                });

            }
        });

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioEditActivity.this, PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                startActivityForResult(intent, 200);
            }
        });

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

            Intent intent = new Intent();
//                                    intent.putExtra("editTextValue", "value_here")
            setResult(RESULT_CANCELED, intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void showMonthDialog(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(PortfolioEditActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = PortfolioEditActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(PortfolioEditActivity.this));

        customListAdapterDialogMonthYear = new CustomListAdapterDialogMonthYear(PortfolioEditActivity.this, PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_month.setText(value.getString("name"));
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
        popupWindow = new PopupWindow(PortfolioEditActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = PortfolioEditActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(PortfolioEditActivity.this));

        customListAdapterDialogMonthYear = new CustomListAdapterDialogMonthYear(PortfolioEditActivity.this, PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_year.setText(value.getString("name"));
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

        popupWindow = new PopupWindow(PortfolioEditActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = PortfolioEditActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(PortfolioEditActivity.this));

        customListAdapterDialogCategory = new CustomListAdapterDialogCategory(PortfolioEditActivity.this, PredictionsJsonArray, new RegistrationTwo.onOptionSelected() {
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

    public void deletePortFolio() {
        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id", ProApplication.getInstance().getUserId());
        Params.put("portfolio_id", port_id);
        Logger.printMessage("PARAMS", String.valueOf(Params));
        new CustomJSONParser().fireAPIForPostMethod(PortfolioEditActivity.this, ProConstant.deleteportfolio, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                JSONObject mainResponseObj = null;
                try {
                    mainResponseObj = new JSONObject(result);
                    Logger.printMessage("message", mainResponseObj.getString("message"));
                    Toast.makeText(PortfolioEditActivity.this, mainResponseObj.getString("message"), Toast.LENGTH_SHORT).show();

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
                new MYAlert(PortfolioEditActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                    @Override
                    public void OnOk(boolean res) {

                    }
                });
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                new MYAlert(PortfolioEditActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
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

    public void deletePortFolioImage(final int position, String image_id) {

        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id", ProApplication.getInstance().getUserId());
        Params.put("image_id", image_id);
        Logger.printMessage("PARAMS", String.valueOf(Params));
        new CustomJSONParser().fireAPIForPostMethod(PortfolioEditActivity.this, ProConstant.deleteportfolioImage, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                JSONObject mainResponseObj = null;
                try {
                    mainResponseObj = new JSONObject(result);
                    Logger.printMessage("message", mainResponseObj.getString("message"));
                    Toast.makeText(PortfolioEditActivity.this, mainResponseObj.getString("message"), Toast.LENGTH_SHORT).show();

                    portPolioImageGalleryArrayList.remove(position);
                    portFolioEditAddImageAdapter.notifyDataSetChanged();

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
                new MYAlert(PortfolioEditActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                    @Override
                    public void OnOk(boolean res) {

                    }
                });
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                new MYAlert(PortfolioEditActivity.this).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
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

    public void fireEditPortFolio() {

        for (int p = 0; p < portPolioImageGalleryArrayList.size(); p++) {
            Logger.printMessage("Image_path[" + p + "]-->", "" + portPolioImageGalleryArrayList.get(p));
        }


        Logger.printMessage("user_id", ProApplication.getInstance().getUserId());
        Logger.printMessage("catid", catid);
        Logger.printMessage("month", tv_month.getText().toString().trim());
        Logger.printMessage("year", tv_year.getText().toString().trim());
        Logger.printMessage("port_id", port_id);

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
        setGetAPIPostData.setValues(tv_month.getText().toString().trim());
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("year");
        setGetAPIPostData.setValues(tv_year.getText().toString().trim());
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("port_id");
        setGetAPIPostData.setValues(port_id);
        arrayListPostParamsValues.add(setGetAPIPostData);

        ArrayList<File> filesImages = new ArrayList<>();

        for (int i = 0; i < portPolioImageGalleryArrayList.size(); i++) {
            if (!portPolioImageGalleryArrayList.get(i).trim().startsWith("http")) {
                filesImages.add(new File(portPolioImageGalleryArrayList.get(i)));
                Logger.printMessage("ImagePostFile[" + i + "]-->", "" + portPolioImageGalleryArrayList.get(i));
            }
        }


        CustomJSONParser.ImageParam = "gallery_image";

        new CustomJSONParser().APIForWithPhotosMultiplePostMethod(PortfolioEditActivity.this, ProConstant.proportfolio_edit, arrayListPostParamsValues, filesImages, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result", result);
                myLoader.dismissLoader();
                try {
                    JSONObject jo = new JSONObject(result);
                    String msg = jo.getString("message");
                    Toast.makeText(PortfolioEditActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

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

    private void showPhotoDialog() {
        dialog = new Dialog(PortfolioEditActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_image_or_pdf);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels / 2;
        int width = displayMetrics.widthPixels / 2;

        ImageView img_gallery = (ImageView) dialog.findViewById(R.id.img_gallery);
        ImageView img_camera = (ImageView) dialog.findViewById(R.id.img_camera);
        ProRegularTextView TXTTitle = (ProRegularTextView) dialog.findViewById(R.id.Title);

        LinearLayout LLMain = (LinearLayout) dialog.findViewById(R.id.LLMain);

        // LLMain.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(LicenceListActivity.this)[1]) /2;
//        RLMain.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        LLMain.setMinimumWidth(height);
        LLMain.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(PortfolioEditActivity.this)[1]) / 2;

        TXTTitle.setText("Licence");

        img_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                if (intent.resolveActivity((PortfolioEditActivity.this).getPackageManager()) != null) {
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            }
        });

        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ProConstant.cameraRequested = true;
                startActivityForResult(new Intent(PortfolioEditActivity.this, ImageTakerActivityCamera.class), REQUEST_IMAGE_CAPTURE);
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

                if (portFolioEditAddImageAdapter == null) {
                    portFolioEditAddImageAdapter = new PortFolioEditAddImageAdapter(PortfolioEditActivity.this, portPolioImageGalleryArrayList);
                    rcv_add_port_folio.setAdapter(portFolioEditAddImageAdapter);
                } else {
                    portFolioEditAddImageAdapter.notifyDataSetChanged();
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
                        if (portFolioEditAddImageAdapter == null) {
                            portFolioEditAddImageAdapter = new PortFolioEditAddImageAdapter(PortfolioEditActivity.this, portPolioImageGalleryArrayList);
                            rcv_add_port_folio.setAdapter(portFolioEditAddImageAdapter);
                        } else {
                            portFolioEditAddImageAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }, 800);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            if (portPolioImageGalleryArrayList.size() < 10) {
                showPhotoDialog();
            } else {
                Toast.makeText(PortfolioEditActivity.this, "You can't select pictures more than 10", Toast.LENGTH_SHORT).show();
            }
        }
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
            Toast.makeText(PortfolioEditActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();
        }else {

            if (tv_month.getText().toString().equals("")){
                Toast.makeText(PortfolioEditActivity.this, "Please Select Month", Toast.LENGTH_SHORT).show();
            }else {
                if (tv_year.getText().toString().equals("")){
                    Toast.makeText(PortfolioEditActivity.this, "Please Select Year", Toast.LENGTH_SHORT).show();
                }else {
                    if (portPolioImageGalleryArrayList.size()<1)
                    {
                        Toast.makeText(PortfolioEditActivity.this, "Please select at least one image", Toast.LENGTH_SHORT).show();
                    }else {
                        fireEditPortFolio();
                    }

                }
            }
        }
    }


}
