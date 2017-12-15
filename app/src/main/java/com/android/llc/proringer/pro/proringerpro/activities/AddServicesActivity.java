package com.android.llc.proringer.pro.proringerpro.activities;

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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.CustomListAdapterServiceListDialogCategory;
import com.android.llc.proringer.pro.proringerpro.adapter.ServiceOfferedAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetServicePojo;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by su on 8/21/17.
 */

public class AddServicesActivity extends AppCompatActivity {

    RecyclerView rcv_service;
    ServiceOfferedAdapter serviceOfferedAdapter = null;
    LinearLayout linear_refine_service = null;
    PopupWindow popupWindow = null;
    String category_Id_parent;
    CustomListAdapterServiceListDialogCategory customListAdapterDialogCategory = null;
    ProRegularTextView tv_service;
    JSONArray CategoryJsonArray = null;

    ArrayList<SetGetServicePojo> setGetServicePojoArrayList;

    MyLoader myLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myLoader = new MyLoader(AddServicesActivity.this);

        linear_refine_service = (LinearLayout) findViewById(R.id.linear_refine_service);
        tv_service = (ProRegularTextView) findViewById(R.id.tv_service);

        rcv_service = (RecyclerView) findViewById(R.id.rcv_service);
        rcv_service.setLayoutManager(new GridLayoutManager(AddServicesActivity.this, 2));

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((ProRegularTextView) findViewById(R.id.tv_service)).getText().toString().trim().equalsIgnoreCase("")) {
                    ((ProRegularTextView) findViewById(R.id.tv_service)).setError("Please Select Category name");
                } else {
                    addCategoryAndSubCategory(tv_service.getText().toString().trim(), category_Id_parent);
                }
            }
        });

        findViewById(R.id.relative_dropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CategoryJsonArray != null) {
                    showDialogServices(v, CategoryJsonArray);
                }
            }
        });

        category();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void runtimeServiceRefineView(ArrayList<SetGetServicePojo> setGetServicePojoArrayList) {

        for (int i = 0; i < setGetServicePojoArrayList.size(); i++) {

            final CheckBox checkRefineHeader = new CheckBox(this);

            checkRefineHeader.setText(setGetServicePojoArrayList.get(i).getCategory_name());
            checkRefineHeader.setTag(setGetServicePojoArrayList.get(i).getParent_category_id());

            LinearLayout.LayoutParams L = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            L.setMargins(10, 0, 10, 0);

//            linear_refine_service.addView(checkRefineHeader,i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            linear_refine_service.addView(checkRefineHeader, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linear_refine_service.addView(checkRefineHeader, L);


            final LinearLayout linearLayoutChild = new LinearLayout(AddServicesActivity.this);
            linearLayoutChild.setTag("sub"+setGetServicePojoArrayList.get(i).getParent_category_id());
            linearLayoutChild.setOrientation(LinearLayout.VERTICAL);
            linearLayoutChild.setPadding(62, 5, 0, 5);

            for (int j = 0; j < setGetServicePojoArrayList.get(i).getService_category_list().length(); j++) {

                try {
                    CheckBox checkRefineChild = new CheckBox(this);
                    checkRefineChild.setText(setGetServicePojoArrayList.get(i).getService_category_list().getJSONObject(j).getString("category_name"));
                    checkRefineChild.setTag(setGetServicePojoArrayList.get(i).getService_category_list().getJSONObject(j).getString("id"));
//                linearLayoutChild.addView(checkRefineChild,j, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayoutChild.addView(checkRefineChild, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            for (int p = 0; p < linearLayoutChild.getChildCount(); p++) {
                final CheckBox checkBox = (CheckBox) linearLayoutChild.getChildAt(p);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Logger.printMessage("isCheckChild", "" + b);
                        if (b) {
                            checkRefineHeader.setChecked(true);
                        } else {
                            int count = 0;
                            for (int k = 0; k < linearLayoutChild.getChildCount(); k++) {
                                if (((CheckBox) linearLayoutChild.getChildAt(k)).isChecked()) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                checkRefineHeader.setChecked(false);
                            }
                        }
                    }
                });
            }

            checkRefineHeader.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //is chkIos checked?
                    if (((CheckBox) v).isChecked()) {
                        //Case 1
                        for (int i = 0; i < linearLayoutChild.getChildCount(); i++) {
                            CheckBox checkBox = (CheckBox) linearLayoutChild.getChildAt(i);
                            checkBox.setChecked(true);
                        }
                    } else {
                        //Case 2
                        for (int i = 0; i < linearLayoutChild.getChildCount(); i++) {
                            CheckBox checkBox = (CheckBox) linearLayoutChild.getChildAt(i);
                            checkBox.setChecked(false);
                        }
                    }
                }
            });

            checkRefineHeader.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Logger.printMessage("isCheck", "" + b);
                }
            });

            linear_refine_service.addView(linearLayoutChild);
        }
    }

    public void runtimeServiceRemoveRefineView(String tagId){

        View parentCheckBox =linear_refine_service.findViewWithTag(tagId);
        View subLinearView=linear_refine_service.findViewWithTag("sub"+tagId);

        linear_refine_service.removeView(subLinearView);
        linear_refine_service.removeView(parentCheckBox);
    }

    private void showDialogServices(View v, JSONArray PredictionsJsonArray) {

        popupWindow = new PopupWindow(AddServicesActivity.this);
        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dailogView = AddServicesActivity.this.getLayoutInflater().inflate(R.layout.dialogcat, null);

        RecyclerView rcv_ = (RecyclerView) dailogView.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(AddServicesActivity.this));

        customListAdapterDialogCategory = new CustomListAdapterServiceListDialogCategory(AddServicesActivity.this, PredictionsJsonArray, new onOptionSelected() {
            @Override
            public void onItemPassed(int position, JSONObject value) {
                popupWindow.dismiss();
                Logger.printMessage("value", "" + value);

                try {
                    tv_service.setText(value.getString("category_name"));
//                    service=value.getString("category_name");
                    category_Id_parent = value.getString("id");

                    Logger.printMessage("id-->", category_Id_parent);

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

    public interface onOptionSelected {
        void onItemPassed(int position, JSONObject value);
    }

    public void category() {
        new CustomJSONParser().fireAPIForGetMethod(AddServicesActivity.this, ProConstant.catagory, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                // Log.d("responese",result);

                try {
                    JSONObject job = new JSONObject(result);
                    CategoryJsonArray = job.getJSONArray("info_array");
                    Logger.printMessage("categoryArray", String.valueOf(CategoryJsonArray));

                    getCategoryAndSubCategory();

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

    public void getCategoryAndSubCategory() {

        setGetServicePojoArrayList = new ArrayList<>();

        ArrayList<SetGetAPI> setGetAPIArrayList = new ArrayList<>();

        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());

        setGetAPIArrayList.add(setGetAPI);

        new CustomJSONParser().fireAPIForGetMethod(AddServicesActivity.this, ProConstant.app_pro_services, setGetAPIArrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray info_array = jsonObject.getJSONArray("info_array");

                    for (int i = 0; i < info_array.length(); i++) {
                        SetGetServicePojo setGetServicePojo = new SetGetServicePojo();
                        setGetServicePojo.setCategory_name(info_array.getJSONObject(i).getString("category_name"));
                        setGetServicePojo.setParent_category_id(info_array.getJSONObject(i).getString("parent_category_id"));
                        setGetServicePojo.setService_category_list(info_array.getJSONObject(i).getJSONArray("service_category_list"));
                        setGetServicePojoArrayList.add(setGetServicePojo);
                    }

                    Logger.printMessage("CatAndSubCatListSize-->", "" + setGetServicePojoArrayList.size());

                    Logger.printMessage("ListSize-->", "" + setGetServicePojoArrayList.size());

                    if (serviceOfferedAdapter == null) {
                        serviceOfferedAdapter = new ServiceOfferedAdapter(AddServicesActivity.this, setGetServicePojoArrayList);
                        rcv_service.setAdapter(serviceOfferedAdapter);
                    } else {
                        serviceOfferedAdapter.notifyDataSetChanged();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                runtimeServiceRefineView(setGetServicePojoArrayList);

                myLoader.dismissLoader();
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

            }
        });
    }

    public void addCategoryAndSubCategory(final String category_name, final String parent_category_id) {

        final ArrayList<SetGetServicePojo> ServicePojoArrayList=new ArrayList<>();

        boolean check = false;
        for (int c = 0; c < setGetServicePojoArrayList.size(); c++) {
            if (setGetServicePojoArrayList.get(c).getParent_category_id().equals(parent_category_id)) {
                check = true;
            }
        }

        if (!check) {

            ArrayList<SetGetAPI> setGetAPIArrayList = new ArrayList<>();

            SetGetAPI setGetAPI = new SetGetAPI();
            setGetAPI.setPARAMS("parent_category");
            setGetAPI.setValues(parent_category_id);

            setGetAPIArrayList.add(setGetAPI);

            new CustomJSONParser().fireAPIForGetMethod(AddServicesActivity.this, ProConstant.app_catrgoryservice_list, setGetAPIArrayList, new CustomJSONParser.CustomJSONResponse() {
                @Override
                public void onSuccess(String result) {
                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray info_array = jsonObject.getJSONArray("info_array");


                        SetGetServicePojo setGetServicePojo = new SetGetServicePojo();
                        setGetServicePojo.setCategory_name(category_name);
                        setGetServicePojo.setParent_category_id(parent_category_id);
                        setGetServicePojo.setService_category_list(info_array);
                        setGetServicePojoArrayList.add(setGetServicePojo);

                        ServicePojoArrayList.add(setGetServicePojo);

                        Logger.printMessage("CatAndSubCatListSize-->", "" + setGetServicePojoArrayList.size());

                        Logger.printMessage("ListSize-->", "" + setGetServicePojoArrayList.size());

                        if (serviceOfferedAdapter == null) {
                            serviceOfferedAdapter = new ServiceOfferedAdapter(AddServicesActivity.this, setGetServicePojoArrayList);
                            rcv_service.setAdapter(serviceOfferedAdapter);
                        } else {
                            serviceOfferedAdapter.notifyDataSetChanged();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    runtimeServiceRefineView(ServicePojoArrayList);

                    myLoader.dismissLoader();
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

                }
            });
        } else {
            Toast.makeText(AddServicesActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
        }
    }

}
