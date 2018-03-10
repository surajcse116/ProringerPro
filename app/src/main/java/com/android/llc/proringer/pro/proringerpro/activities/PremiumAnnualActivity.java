package com.android.llc.proringer.pro.proringerpro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.PremiumAdapter;

import java.util.ArrayList;

public class PremiumAnnualActivity extends AppCompatActivity {

    PremiumAdapter premiumAdapter;
    RecyclerView rv_content;
    Spinner expiry_moth,expiry_year;
    ArrayList<String> stringArrayList;
    String data[] = {" Expire month", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String data1[]={"Expire year","2017","2018","2019","2020","2021","2022","2025","2026","2027","2028","2029","2030"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_anual);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_content=(RecyclerView)findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new GridLayoutManager(PremiumAnnualActivity.this,2));

        stringArrayList=new ArrayList<>();

        stringArrayList.add("Unlimited Leads");
        stringArrayList.add("Higher Placement");
        stringArrayList.add("Instant Notification");
        stringArrayList.add("Advance Analytics");
        stringArrayList.add("Verify Account");
        stringArrayList.add("1 Free LogoDesign");
        stringArrayList.add("Messaging System");
        stringArrayList.add("Site Linking");
        stringArrayList.add("Lead Dashboard");
        stringArrayList.add("Social Media Linking");
        stringArrayList.add("Rating & Reviews");
        stringArrayList.add("Vanity URL");
        stringArrayList.add("Directory");
        stringArrayList.add("Custom Profile");


        premiumAdapter = new PremiumAdapter(PremiumAnnualActivity.this,stringArrayList);

        expiry_moth = (Spinner) findViewById(R.id.expiry_moth);
        expiry_year = (Spinner) findViewById(R.id.expiry_year);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PremiumAnnualActivity.this, android.R.layout.simple_list_item_1, data);
        expiry_moth.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(PremiumAnnualActivity.this, android.R.layout.simple_list_item_1, data1);
        expiry_year.setAdapter(adapter1);
        rv_content.setAdapter(premiumAdapter);

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
