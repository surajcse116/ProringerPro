package com.android.llc.proringer.pro.proringerpro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

public class SignupCompleteActivity extends AppCompatActivity {

    ProRegularTextView mail_resent, contact, confirm_login;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_complete);
        contact = (ProRegularTextView) findViewById(R.id.contact);
        mail_resent = (ProRegularTextView) findViewById(R.id.mail_resent);
        confirm_login = (ProRegularTextView) findViewById(R.id.confirm_login);
        home = (ImageView) findViewById(R.id.home);
        String contactTextOne = "If you already request your confirmation email to be reset and you have not received that email within 30 minutes and do not see it in your Spam/Junk folder please  ";
        String contactTextClick = "contact us ";
        String contactTextTwo = "and include a phone number.";

        Spannable word1 = new SpannableString(contactTextOne);

        word1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, contactTextOne.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        contact.setText(word1);

        Spannable word2 = new SpannableString(contactTextClick);

        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // There is the OnCLick. put your intent to Register class here
                widget.invalidate();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorAccent));
                ds.setUnderlineText(false);
            }
        };
        word2.setSpan(myClickableSpan, 0, contactTextClick.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        contact.append(word2);

        Spannable word3 = new SpannableString(contactTextTwo);

        word3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, contactTextTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        contact.append(word3);


        /**
         * Mail resent spannable text with click listener
         */
        String mailResentPart1 = "If you do not received confirmation email within 30 minutes you can ";
        String mailResentPart2 = "request it to be resent.";

        Spannable resentWord = new SpannableString(mailResentPart1);

        resentWord.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, mailResentPart1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Spannable resentWordClick = new SpannableString(mailResentPart2);
        mail_resent.setText(resentWord);
        ClickableSpan resentClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // There is the OnCLick. put your intent to Register class here
                widget.invalidate();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorAccent));
                ds.setUnderlineText(false);
            }
        };
        resentWordClick.setSpan(resentClickableSpan, 0, mailResentPart2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mail_resent.append(resentWordClick);

        confirm_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i= new Intent(SignupCompleteActivity.this,LogInActivity.class);
//                startActivity(i);
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i= new Intent(SignupCompleteActivity.this,LogInActivity.class);
//                startActivity(i);
                finish();
            }
        });
    }
}
