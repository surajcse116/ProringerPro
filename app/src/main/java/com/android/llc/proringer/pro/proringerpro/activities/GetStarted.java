package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.GetStartedTutorial;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;


/**
 * Created by bodhidipta on 10/06/17.
 * <!-- * Copyright (c) 2017, Proringer-->
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -->
 */

public class GetStarted extends AppCompatActivity {

    private ViewPager get_started_pager;
    private GetStartedTutorial adapter;
    private ImageView pager_dot_one, pager_dot_two, pager_dot_three, pager_dot_four, slide_left, slide_right;
    private ProRegularTextView get_started, sign_in;
    public static final int LOG_IN_REQUEST = 1;
    public static final int SIGN_UP_REQUEST = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_started);

        pager_dot_one = (ImageView) findViewById(R.id.pager_dot_one);
        pager_dot_two = (ImageView) findViewById(R.id.pager_dot_two);
        pager_dot_three = (ImageView) findViewById(R.id.pager_dot_three);
        pager_dot_four = (ImageView) findViewById(R.id.pager_dot_four);


        get_started_pager = (ViewPager) findViewById(R.id.get_started_pager);
        get_started = (ProRegularTextView) findViewById(R.id.get_started);
        sign_in = (ProRegularTextView) findViewById(R.id.sign_in);

        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(GetStarted.this, SignUp.class), SIGN_UP_REQUEST);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(GetStarted.this, LogIn.class), LOG_IN_REQUEST);
            }
        });


        adapter = new GetStartedTutorial(getSupportFragmentManager());
        get_started_pager.setAdapter(adapter);

        get_started_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                manageDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void manageDots(int position) {
        switch (position) {
            case 0:
                pager_dot_one.setBackgroundResource(R.drawable.circle_orenge_border);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
            case 1:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_orenge_border);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
            case 2:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_orenge_border);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
            case 3:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_orenge_border);
                break;
            case 4:
                pager_dot_one.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_two.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_three.setBackgroundResource(R.drawable.circle_dark);
                pager_dot_four.setBackgroundResource(R.drawable.circle_dark);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP_REQUEST) {
            if (resultCode == RESULT_OK) {
//                startActivity(new Intent(GetStarted.this, LogIn.class));
//                finish();
            }

        } else if (requestCode == LOG_IN_REQUEST) {
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(GetStarted.this, LandScreenActivity.class));
                finish();
            }
        }
    }
}
