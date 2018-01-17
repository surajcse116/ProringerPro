package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LocationActivity;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

/**
 * Created by su on 15/1/18.
 */

public class GetVerificationForthFragment extends Fragment{
    @Nullable
    RelativeLayout RL_address;
    ProRegularTextView tv_confirmlater,txt_address,txt_place,txt_state,txt_pincode;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_verification_forth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RL_address=(RelativeLayout)view.findViewById(R.id.RL_address);

        txt_place=(ProRegularTextView)view.findViewById(R.id.txt_place);
        txt_state=(ProRegularTextView)view.findViewById(R.id.txt_state);
        txt_pincode=(ProRegularTextView)view.findViewById(R.id.txt_pincode);
        txt_address=(ProRegularTextView) view.findViewById(R.id.txt_address);
        tv_confirmlater=(ProRegularTextView)view.findViewById(R.id.tv_confirmlater) ;


        RL_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  et_main_address.setBackgroundResource(R.drawable.background_solidorange_border);
                Intent intent= new Intent( getActivity(),LocationActivity.class);
                startActivityForResult(intent,1);

            }
        });
        tv_confirmlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        ((GetVerificationActivity) getActivity()).increaseStep();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.printMessage("dataaction", String.valueOf(data));
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle extras = data.getBundleExtra("data");
                if (extras != null) {

                    Logger.printMessage("selectedPlace", "--->" + extras.getString("selectedPlace"));
                    Logger.printMessage("zip", "--->" + extras.getString("zip"));
                    Logger.printMessage("city", "--->" + extras.getString("city"));
                    Logger.printMessage("state", "--->" + extras.getString("state"));

                    if (!extras.getString("selectedPlace").equals("")) {
                        txt_address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }
                    txt_pincode.setText(extras.getString("zip"));
                    txt_place.setText(extras.getString("city"));
                    txt_state.setText(extras.getString("state"));
//                    String servicearea = extras.getString("city") + ", " + extras.getString("state");
//                    Logger.printMessage("ServiceArea-->", servicearea);
//                    tv_service_area.setText(servicearea);
                }
            }
        }
    }

    public void validation()
    {
        if (txt_address.getText().toString().trim().equals(""))
        {
            Toast.makeText(getActivity(), "Please choose address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (txt_place.getText().toString().trim().equals(""))
            {
                Toast.makeText(getActivity(), "Please select address which have city,state and pin code", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (txt_state.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getActivity(), "Please select address which have city,state and pin code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (txt_pincode.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getActivity(), "Please select address which have city,state and pin code", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                       ///ggggggggggggggggggggggggggggggggg
                    }
                }
            }

        }
    }
}