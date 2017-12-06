package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;


/**
 * Created by su on 7/19/17.
 */

public class ResendConfirmation extends AppCompatActivity {
    ProRegularTextView tv_resend;
    ProLightEditText email;
    ProRegularTextView submit_email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend_confirmation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_resend= (ProRegularTextView) findViewById(R.id.tv_resend);
        email=(ProLightEditText)findViewById(R.id.email);
        submit_email=(ProRegularTextView)findViewById(R.id.submit_email);
        submit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            validation();


            }
        });


        if (Build.VERSION.SDK_INT >= 24)
        {
            tv_resend.setText(Html.fromHtml(getString(R.string.resend_contact_us), Html.FROM_HTML_MODE_LEGACY));
        }
        else
        {
            tv_resend.setText(Html.fromHtml(getString(R.string.resend_contact_us)));
        }

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResendConfirmation.this, ContactUsActivity.class));
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
    public void validation()
    {
        if (email.getText().toString().trim().equals(""))
        {
            email.setError("please enter email");
        }
        else
        {
            if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
            {
                Toast.makeText(this, "email sent", Toast.LENGTH_SHORT).show();
            }
            else
            {
                email.setError("please entar valid email");
            }
        }
    }
}
