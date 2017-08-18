package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.llc.proringer.pro.proringerpro.R;

/**
 * Created by su on 8/18/17.
 */

public class MyProjectDetailsActivity extends AppCompatActivity {
    int screenHeight;
    int screenWidth;

    ImageView img_project, img_map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        img_project = (ImageView) findViewById(R.id.img_project);
        img_map = (ImageView) findViewById(R.id.img_map);


        img_project.getLayoutParams().height = screenWidth - 100;

        img_map.getLayoutParams().height = (screenWidth - 10) / 2;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
