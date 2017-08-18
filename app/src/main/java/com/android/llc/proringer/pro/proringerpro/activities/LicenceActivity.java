package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;

/**
 * Created by su on 8/18/17.
 */

public class LicenceActivity extends AppCompatActivity {

    RecyclerView rcv_licence_list;
    RelativeLayout RLAddLicence, RLEmpty;

    LicenceAdapter licenceAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RLAddLicence = (RelativeLayout) findViewById(R.id.RLAddLicence);
        RLEmpty = (RelativeLayout) findViewById(R.id.RLEmpty);

        rcv_licence_list = (RecyclerView) findViewById(R.id.rcv_licence_list);
        rcv_licence_list.setLayoutManager(new LinearLayoutManager(LicenceActivity.this));

        RLAddLicence.setVisibility(View.GONE);
        rcv_licence_list.setVisibility(View.GONE);
        RLEmpty.setVisibility(View.VISIBLE);


        findViewById(R.id.img_add_licence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RLAddLicence.getVisibility() == View.VISIBLE) {
                    RLAddLicence.setVisibility(View.GONE);
                    rcv_licence_list.setVisibility(View.VISIBLE);
                } else {
                    RLAddLicence.setVisibility(View.VISIBLE);
                    rcv_licence_list.setVisibility(View.GONE);
                }
            }
        });
        loadListAndSetAdapter();
    }

    public void loadListAndSetAdapter() {
        if (licenceAdapter == null) {
            licenceAdapter = new LicenceAdapter(LicenceActivity.this);
            rcv_licence_list.setAdapter(licenceAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
