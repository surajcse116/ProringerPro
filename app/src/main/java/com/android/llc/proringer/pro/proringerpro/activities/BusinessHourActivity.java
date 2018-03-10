package com.android.llc.proringer.pro.proringerpro.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;


import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AvailabilityTimeSlotAdapter;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetBusinessHour;

import java.util.ArrayList;

/**
 * Created by su on 8/12/17.
 */

public class BusinessHourActivity extends AppCompatActivity {
    AvailabilityTimeSlotAdapter availabilityTimeSlotAdapter;
    private RecyclerView rcv_;
    RadioButton rb_openselected,rb_alwayopen;
    ArrayList<SetGetBusinessHour> businessHourArrayList=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_hour);
        rb_openselected=(RadioButton)findViewById(R.id.rb_openselected);
        rb_alwayopen=(RadioButton)findViewById(R.id.rb_alwayopen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        businessHourArrayList=new ArrayList<>();



            SetGetBusinessHour setGetBusinessHour=new SetGetBusinessHour();
            setGetBusinessHour.setCheck(false);
            setGetBusinessHour.setDay("Mon");
            setGetBusinessHour.setStartTime("Time");
            setGetBusinessHour.setEndTime("Time");
            businessHourArrayList.add(setGetBusinessHour);



        setGetBusinessHour=new SetGetBusinessHour();
        setGetBusinessHour.setCheck(false);
        setGetBusinessHour.setDay("Tue");
        setGetBusinessHour.setStartTime("Time");
        setGetBusinessHour.setEndTime("Time");
        businessHourArrayList.add(setGetBusinessHour);


        setGetBusinessHour=new SetGetBusinessHour();
        setGetBusinessHour.setCheck(false);
        setGetBusinessHour.setDay("Wed");
        setGetBusinessHour.setStartTime("Time");
        setGetBusinessHour.setEndTime("Time");
        businessHourArrayList.add(setGetBusinessHour);



        setGetBusinessHour=new SetGetBusinessHour();
        setGetBusinessHour.setCheck(false);
        setGetBusinessHour.setDay("Thu");
        setGetBusinessHour.setStartTime("Time");
        setGetBusinessHour.setEndTime("Time");
        businessHourArrayList.add(setGetBusinessHour);


        setGetBusinessHour=new SetGetBusinessHour();
        setGetBusinessHour.setCheck(false);
        setGetBusinessHour.setDay("Fri");
        setGetBusinessHour.setStartTime("Time");
        setGetBusinessHour.setEndTime("Time");
        businessHourArrayList.add(setGetBusinessHour);


        setGetBusinessHour=new SetGetBusinessHour();
        setGetBusinessHour.setCheck(false);
        setGetBusinessHour.setDay("Sat");
        setGetBusinessHour.setStartTime("Time");
        setGetBusinessHour.setEndTime("Time");
        businessHourArrayList.add(setGetBusinessHour);

        setGetBusinessHour=new SetGetBusinessHour();
        setGetBusinessHour.setCheck(false);
        setGetBusinessHour.setDay("Sun");
        setGetBusinessHour.setStartTime("Time");
        setGetBusinessHour.setEndTime("Time");
        businessHourArrayList.add(setGetBusinessHour);



        rcv_ = (RecyclerView) findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(BusinessHourActivity.this));

        availabilityTimeSlotAdapter = new AvailabilityTimeSlotAdapter(BusinessHourActivity.this,businessHourArrayList);

        rcv_.setAdapter(availabilityTimeSlotAdapter);
        rb_openselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcv_.setVisibility(View.VISIBLE);
            }
        });
        rb_alwayopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcv_.setVisibility(View.GONE);
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
}
