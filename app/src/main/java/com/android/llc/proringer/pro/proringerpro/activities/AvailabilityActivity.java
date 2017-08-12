package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AvailabilityRecyclerViewAdapter;

/**
 * Created by su on 8/12/17.
 */

public class AvailabilityActivity extends AppCompatActivity {
    RecyclerView rcv_availability;
    AvailabilityRecyclerViewAdapter availabilityRecyclerViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcv_availability= (RecyclerView) findViewById(R.id.rcv_availability);
        rcv_availability.setLayoutManager(new LinearLayoutManager(AvailabilityActivity.this));
        availabilityRecyclerViewAdapter=new AvailabilityRecyclerViewAdapter(AvailabilityActivity.this);


        rcv_availability.setAdapter(availabilityRecyclerViewAdapter);

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
