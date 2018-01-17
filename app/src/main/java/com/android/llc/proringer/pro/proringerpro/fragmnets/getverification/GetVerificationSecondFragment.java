package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetVerificationSecondFragment extends Fragment {
    ProRegularTextView tv3, tv_confier_later_continew, tv_conferm;
    ProLightEditText et_confirmpin;

    public GetVerificationSecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_verification_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv3 = view.findViewById(R.id.tv3);
        et_confirmpin = view.findViewById(R.id.et_confirmpin);
        tv_conferm = view.findViewById(R.id.tv_conferm);
        tv_conferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_confirmpin.getText().toString().trim().equals("") || et_confirmpin.getText().toString().trim().length() < 5) {
                    et_confirmpin.setError("Enter pin number");
                    et_confirmpin.requestFocus();
                } else {
                    ((GetVerificationActivity) getActivity()).callVerificationFragments(3);
                }
            }
        });
        tv_confier_later_continew = view.findViewById(R.id.tv_confier_later_continew);

        ((GetVerificationActivity) getActivity()).increaseStep();

        tv_confier_later_continew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((GetVerificationActivity) getActivity()).callVerificationFragments(3);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv3.setText(Html.fromHtml(getResources().getString(R.string.welcome_text), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv3.setText(Html.fromHtml(getResources().getString(R.string.welcome_text)));
        }

    }
}
