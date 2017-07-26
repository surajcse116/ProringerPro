package com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProLightTextView;

/**
 * Created by Bodhidipta on 24/07/17.
 * <!-- * Copyright (c) 2017, The ProringerPro-->
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
 */

public class RegistrationTwo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProLightTextView terms_and_condition=(ProLightTextView)view.findViewById(R.id.terms_and_condition);
        String contactTextOne = "By singing up with ProRinger you agree with our  ";
        String contactTextClick = "Terms of Use";
        String contactTextTwo = " and ";
        String privacyPolicies = "Privacy Policy";

        Spannable word1 = new SpannableString(contactTextOne);

        word1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, contactTextOne.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        terms_and_condition.setText(word1);


        Spannable word2 = new SpannableString(contactTextClick);

        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // There is the OnCLick. put your intent to Register class here
                widget.invalidate();
                Logger.printMessage("SpanHello", "click");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorAccent));
                ds.setUnderlineText(false);
            }
        };
        word2.setSpan(myClickableSpan, 0, contactTextClick.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        terms_and_condition.append(word2);

        Spannable word3 = new SpannableString(contactTextTwo);

        word3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextDark)), 0, contactTextTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        terms_and_condition.append(word3);

        Spannable word4 = new SpannableString(privacyPolicies);

        ClickableSpan privacySpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // There is the OnCLick. put your intent to Register class here
                widget.invalidate();
                Logger.printMessage("SpanHello", "click");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorAccent));
                ds.setUnderlineText(false);
            }
        };
        word4.setSpan(privacySpan, 0, privacyPolicies.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        terms_and_condition.append(word4);



    }
}
