package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AvailibilityTimeSlotAdapter;

/**
 * Created by su on 8/12/17.
 */

public class AvailabilityActivity extends AppCompatActivity {
    AvailibilityTimeSlotAdapter availibilityTimeSlotAdapter;
    private RecyclerView rcv_;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_availablity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcv_ = (RecyclerView) findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(AvailabilityActivity.this));

        availibilityTimeSlotAdapter = new AvailibilityTimeSlotAdapter(AvailabilityActivity.this);
        rcv_.setAdapter(availibilityTimeSlotAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
