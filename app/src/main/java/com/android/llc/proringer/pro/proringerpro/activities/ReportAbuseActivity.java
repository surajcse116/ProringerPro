package com.android.llc.proringer.pro.proringerpro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;

public class ReportAbuseActivity extends AppCompatActivity {

    ProRegularEditText pro_review_description_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportabuse);
        pro_review_description_text=(ProRegularEditText)findViewById(R.id.pro_review_description_text);
        pro_review_description_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pro_review_description_text.setBackgroundResource(R.drawable.background_solidorange_border);
            }
        });
    }
}
