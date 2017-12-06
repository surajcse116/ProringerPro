package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ServiceAreaAdapter;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;

import java.util.ArrayList;

/**
 * Created by su on 8/17/17.
 */

public class AddServiceAreaActivity extends AppCompatActivity {

    RecyclerView rcv_service_area;
    ServiceAreaAdapter serviceAreaAdapter=null;
    ArrayList<String> stringArrayList=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_area);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcv_service_area= (RecyclerView) findViewById(R.id.rcv_service_area);
        rcv_service_area.setLayoutManager(new GridLayoutManager(AddServiceAreaActivity.this, 2));

        stringArrayList=new ArrayList<>();

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((ProLightEditText)findViewById(R.id.edt_cities)).getText().toString().trim().equalsIgnoreCase("")){
                    ((ProLightEditText)findViewById(R.id.edt_cities)).setError("Please Enter City name");
                }else {
                    createListAndAdapterSet(((ProLightEditText)findViewById(R.id.edt_cities)).getText().toString().trim());
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createListAndAdapterSet(String s){

        stringArrayList.add(s);
        Logger.printMessage("ListSize-->",""+stringArrayList.size());

        if (serviceAreaAdapter==null){
            serviceAreaAdapter = new ServiceAreaAdapter(AddServiceAreaActivity.this, stringArrayList);
            rcv_service_area.setAdapter(serviceAreaAdapter);
        }else {
            serviceAreaAdapter.notifyDataSetChanged();
        }
    }
}
