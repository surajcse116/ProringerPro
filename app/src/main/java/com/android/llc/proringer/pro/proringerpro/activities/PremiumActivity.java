package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.PremiumAdapter;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;

import java.util.ArrayList;

public class PremiumActivity extends AppCompatActivity {

   TextView tv_data;
    ProSemiBoldTextView tv_go_premium;

    ArrayList<String> stringArrayList;
    RecyclerView rv_content;
    PremiumAdapter premiumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_data=(TextView) findViewById(R.id.tv_data);
        tv_go_premium=(ProSemiBoldTextView)findViewById(R.id.tv_go_premium);

        rv_content=(RecyclerView)findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new GridLayoutManager(PremiumActivity.this,2));

        stringArrayList=new ArrayList<>();

        stringArrayList.add("Unlimited Leads");
        stringArrayList.add("Higher Placement");
        stringArrayList.add("Instant Notification");
        stringArrayList.add("Advance Analytics");
        stringArrayList.add("Verify Account");
        stringArrayList.add("1 Free Logo Design");
        stringArrayList.add("Messaging System");
        stringArrayList.add("Site Linking");
        stringArrayList.add("Lead Dashboard");
        stringArrayList.add("Social Media Linking");
        stringArrayList.add("Rating & Reviews");
        stringArrayList.add("Vanity URL");
        stringArrayList.add("Directory");
        stringArrayList.add("Custom Profile");


        premiumAdapter = new PremiumAdapter(PremiumActivity.this,stringArrayList);
        rv_content.setAdapter(premiumAdapter);

        tv_go_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(PremiumActivity.this,PremiumAnnualActivity.class);
                startActivity(i);
            }
        });
        String text1=" We get you in fornt of new customers without costing a fortune.unlimited leads giving you the ablity to pic the leads you want without having to worry about paying for adead lead.";

        Spannable word1 = new SpannableString(text1);

        word1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, text1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_data.setText(word1);


    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
