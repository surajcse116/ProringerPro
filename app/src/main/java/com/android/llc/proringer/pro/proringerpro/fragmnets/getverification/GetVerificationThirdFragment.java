package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

/**
 * Created by su on 15/1/18.
 */

public class GetVerificationThirdFragment extends Fragment {
    ProRegularTextView tv_continew;
    RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_verification_third, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((GetVerificationActivity) getActivity()).increaseStep();
        tv_continew = view.findViewById(R.id.tv_continew);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Radio Button", "Radio Button");
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        tv_continew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GetVerificationActivity) getActivity()).callVerificationFirstFragment(4);
            }
        });
    }


}
