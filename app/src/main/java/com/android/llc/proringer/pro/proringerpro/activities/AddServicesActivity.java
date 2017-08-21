package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ServiceOfferedAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.ServiceRefineAdapter;
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by su on 8/21/17.
 */

public class AddServicesActivity extends AppCompatActivity {
    RecyclerView rcv_service;
    ServiceOfferedAdapter serviceOfferedAdapter = null;
    ServiceRefineAdapter serviceRefineAdapter = null;
    ArrayList<String> stringServiceList = null;


    ExpandableListView Expandable_service_refine;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Expandable_service_refine = (ExpandableListView) findViewById(R.id.Expandable_service_refine);

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

        prepareListData();
        serviceRefineAdapter = new ServiceRefineAdapter(AddServicesActivity.this, listDataHeader, listDataChild);
        Expandable_service_refine.setAdapter(serviceRefineAdapter);
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

    /*
     * Preparing the list data
     */
    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Home Inspector");

        // Adding child data
        List<String> child1 = new ArrayList<String>();
        child1.add("Termite Inspection");
        child1.add("Radon Testing");
        child1.add("Energy Audits");
        child1.add("Mold Inspection");


        listDataChild.put(listDataHeader.get(0), child1); // Header, Child data

    }
}
