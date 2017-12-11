package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogCategory;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterDialogMonthYear;
import com.android.llc.proringer.pro.proringerpro.adapter.PortfolioEditImageAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwo;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by su on 12/8/17.
 */

public class PortfolioEditActivity extends AppCompatActivity {
    String port_id = "", catid = "", month = "", year = "", category_name = "",
            monthDigit = "", monthName = "", yearName = "", yearDigit = "";

    JSONArray multiple_gallery_image, categoryJsonArray;
    ProRegularTextView tv_category, tv_month, tv_year;
    RelativeLayout relative_category_dropdown, relative_month_dropdown, relative_year_dropdown;
    PopupWindow popupWindow;
    CustomListAdapterDialogMonthYear customListAdapterDialogMonthYear = null;
    CustomListAdapterDialogCategory customListAdapterDialogCategory = null;
    ArrayList<String> portPolioImageGalleryArrayList = null;

    PortfolioEditImageAdapter portfolioEditImageAdapter = null;

    RecyclerView rcv_add_port_folio;

    MyLoader myLoader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_portfolio_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myLoader=new MyLoader(this);

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
        month = getIntent().getExtras().getString("month");
        year = getIntent().getExtras().getString("year");
        category_name = getIntent().getExtras().getString("category_name");

        portPolioImageGalleryArrayList = new ArrayList<>();

        try {
            multiple_gallery_image = new JSONArray(getIntent().getExtras().getString("multiple_gallery_image"));
            categoryJsonArray = new JSONArray(getIntent().getExtras().getString("categoryJsonArray"));

            for (int i = 0; i < multiple_gallery_image.length(); i++) {
                portPolioImageGalleryArrayList.add(multiple_gallery_image.getString(i));
            }

            portfolioEditImageAdapter = new PortfolioEditImageAdapter(PortfolioEditActivity.this, portPolioImageGalleryArrayList);
            rcv_add_port_folio.setAdapter(portfolioEditImageAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        tv_category.setText(category_name);
        tv_month.setText(month);
        tv_year.setText(year);


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

                CustomAlert customAlert=new CustomAlert();
                customAlert.getEventFromNormalAlert(PortfolioEditActivity.this, "Delete", "Are you sure to delete "+category_name+" gallery", "YES,DELETE IT", "CANCEL", new CustomAlert.MyCustomAlertListener() {
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

    public void deletePortFolio(){
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
                    Toast.makeText(PortfolioEditActivity.this,mainResponseObj.getString("message"),Toast.LENGTH_SHORT).show();

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

}
