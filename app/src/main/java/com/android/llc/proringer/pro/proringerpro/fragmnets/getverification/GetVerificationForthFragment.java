package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LocationActivity;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import java.util.HashMap;

/**
 * Created by su on 15/1/18.
 */

public class GetVerificationForthFragment extends Fragment{
    String address,city,state,zip;
    @Nullable
    RelativeLayout RL_address;
    ProRegularTextView tv_confirmlater,txt_address,txt_place,txt_state,txt_pincode;
    MyLoader myLoader;

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
        tv_confirmlater=(ProRegularTextView)view.findViewById(R.id.tv_confirmlater);

        myLoader=new MyLoader(getActivity());


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
               // validation();
                ((GetVerificationActivity)getActivity()).callVerificationFragments(5);
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
                    address=extras.getString("selectedPlace");
                    city=extras.getString("city");
                    state= extras.getString("state");
                    zip=extras.getString("zip");

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
                    ///ggggggggggggggggggggggggggggggggg
                    if (txt_pincode.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getActivity(), "Please select address which have city,state and pin code", Toast.LENGTH_SHORT).show();
                    }

                        callVerifiedAddress();
                }
            }

        }
    }

    private void callVerifiedAddress() {
        HashMap<String,String> params=new HashMap<>();
        params.put("user_id", ProApplication.getInstance().getUserId());
        params.put("verify_country",ProConstant.Country);
        params.put("verify_address", address.trim().split(",")[0]);
        params.put("verify_city",city);
        params.put("verify_state",state);
        params.put("verify_zip",zip);
        params.put("verify_latitude",ProConstant.latitude);
        params.put("verify_longitude",ProConstant.longtitude);
        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_verified_address, params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Log.d("resultDoc",result);
                ((GetVerificationActivity)getActivity()).callVerificationFragments(5);
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
        Log.d("user_id",ProApplication.getInstance().getUserId());
        Log.d("verify_country",ProConstant.Country);
        Log.d("verify_address",address);
        Log.d("verify_city",city);
        Log.d("verify_state",state);
        Log.d("verify_zip",zip);
    }
}