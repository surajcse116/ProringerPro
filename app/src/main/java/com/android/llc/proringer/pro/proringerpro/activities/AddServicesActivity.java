package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ServiceOfferedAdapter;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;

import java.util.ArrayList;


/**
 * Created by su on 8/21/17.
 */

public class AddServicesActivity extends AppCompatActivity {
    RecyclerView rcv_service;
    ServiceOfferedAdapter serviceOfferedAdapter = null;
    ArrayList<String> stringServiceList = null;

    LinearLayout linear_refine_service = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linear_refine_service = (LinearLayout) findViewById(R.id.linear_refine_service);

        rcv_service = (RecyclerView) findViewById(R.id.rcv_service);
        rcv_service.setLayoutManager(new GridLayoutManager(AddServicesActivity.this, 2));

        stringServiceList = new ArrayList<>();

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((ProLightEditText) findViewById(R.id.edt_services)).getText().toString().trim().equalsIgnoreCase("")) {
                    ((ProLightEditText) findViewById(R.id.edt_services)).setError("Please Enter City name");
                } else {
                    createListAndAdapterSet(((ProLightEditText) findViewById(R.id.edt_services)).getText().toString().trim());
                }
            }
        });

        runtimeServiceRefineView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void createListAndAdapterSet(String s) {
        stringServiceList.add(s);
        Logger.printMessage("ListSize-->", "" + stringServiceList.size());

        if (serviceOfferedAdapter == null) {
            serviceOfferedAdapter = new ServiceOfferedAdapter(AddServicesActivity.this, stringServiceList);
            rcv_service.setAdapter(serviceOfferedAdapter);
        } else {
            serviceOfferedAdapter.notifyDataSetChanged();
        }
    }

    public void runtimeServiceRefineView() {


        for (int i = 0; i < 4; i++) {

            final CheckBox checkRefineHeader = new CheckBox(this);
            checkRefineHeader.setText(i + " header");
            checkRefineHeader.setTag(i + " header");

            LinearLayout.LayoutParams L = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            L.setMargins(10, 0, 10, 0);

//            linear_refine_service.addView(checkRefineHeader,i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            linear_refine_service.addView(checkRefineHeader, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linear_refine_service.addView(checkRefineHeader, L);


            final LinearLayout linearLayoutChild = new LinearLayout(AddServicesActivity.this);
            linearLayoutChild.setOrientation(LinearLayout.VERTICAL);
            linearLayoutChild.setPadding(62, 5, 0, 5);

            for (int j = 0; j < 5; j++) {
                CheckBox checkRefineChild = new CheckBox(this);
                checkRefineChild.setText(j + " child");
                checkRefineChild.setTag(j + " child");
//                linearLayoutChild.addView(checkRefineChild,j, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayoutChild.addView(checkRefineChild, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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


}
