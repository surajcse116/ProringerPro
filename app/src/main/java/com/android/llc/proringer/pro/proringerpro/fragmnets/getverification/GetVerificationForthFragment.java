package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LocationActivity;

import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;

import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by su on 15/1/18.
 */

public class GetVerificationForthFragment extends Fragment {

    @Nullable
    RelativeLayout RL_address;
    ProRegularTextView tv_confirmlater, txt_address, txt_city, txt_state, txt_pincode;
    MyLoader myLoader;
    boolean confirmOrNot = false;
    String country="",lattitude="",longttitude="";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_verification_forth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RL_address = (RelativeLayout) view.findViewById(R.id.RL_address);
        txt_city = (ProRegularTextView) view.findViewById(R.id.txt_city);
        txt_state = (ProRegularTextView) view.findViewById(R.id.txt_state);
        txt_pincode = (ProRegularTextView) view.findViewById(R.id.txt_pincode);
        txt_address = (ProRegularTextView) view.findViewById(R.id.txt_address);
        tv_confirmlater = (ProRegularTextView) view.findViewById(R.id.tv_confirmlater);


        txt_city.setText(((GetVerificationActivity) getActivity()).city);
        txt_state.setText(((GetVerificationActivity) getActivity()).state);
        txt_pincode.setText(((GetVerificationActivity) getActivity()).zip);
        txt_address.setText(((GetVerificationActivity) getActivity()).address);


        if (!txt_city.getText().toString().trim().equals("")
                && !txt_state.getText().toString().trim().equals("")
                && !txt_pincode.getText().toString().trim().equals("")
                && !txt_address.getText().toString().trim().equals("")) {

            tv_confirmlater.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            tv_confirmlater.setText("continue to confirm your account");

            confirmOrNot = true;
        } else {

            tv_confirmlater.setBackgroundColor(getResources().getColor(R.color.colorDarkGray));
            tv_confirmlater.setText("confirm later and continue");
            confirmOrNot = false;
        }


        myLoader = new MyLoader(getActivity());

        RL_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  et_main_address.setBackgroundResource(R.drawable.background_solidorange_border);
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivityForResult(intent, 1);

            }
        });


        tv_confirmlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmOrNot) {
                    validation();
                } else {
                    if (!((GetVerificationActivity) getActivity()).verifyPin) {
                        ((GetVerificationActivity) getActivity()).callVerificationFragments(5);
                    } else {
                        Intent intent = new Intent(getActivity(), LandScreenActivity.class);
                        // set the new task and clear flags
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });

        ((GetVerificationActivity) getActivity()).increaseStep();
    }
    public void getAddress(Double lat, Double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address obj = addresses.get(0);
        String add = obj.getAddressLine(0);
        String currentAddress = obj.getSubAdminArea() + ","
                + obj.getAdminArea();
        String latitude = String.valueOf(obj.getLatitude());
        String longitude = String.valueOf(obj.getLongitude());
        String  currentCity= obj.getSubAdminArea();
        String currentState= obj.getAdminArea();
        add = add + "\n" + obj.getCountryName();
        add = add + "\n" + obj.getCountryCode();
        country= obj.getCountryCode();
        Logger.printMessage("countryCode",country);
        add = add + "\n" + obj.getAdminArea();
        add = add + "\n" + obj.getPostalCode();
        add = add + "\n" + obj.getSubAdminArea();
        add = add + "\n" + obj.getLocality();
        add = add + "\n" + obj.getSubThoroughfare();

        Log.v("IGA", "Address" + add);
        // Toast.makeText(this, "Address=>" + add,
        // Toast.LENGTH_SHORT).show();

        // TennisAppActivity.showDialog(add);

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
                    Logger.printMessage("lattitude", "--->" + extras.getString("lattitude"));
                    Logger.printMessage("longttitude", "--->" + extras.getString("longttitude"));


                    txt_city.setText(extras.getString("city"));
                    txt_state.setText(extras.getString("state"));
                    txt_pincode.setText(extras.getString("zip"));
                    txt_address.setText(extras.getString("selectedPlace"));
                    Double lat,lon;
                    lat= Double.valueOf(extras.getString("lattitude"));
                    lon=Double.valueOf(extras.getString("longttitude"));
                    lattitude=extras.getString("lattitude");
                    longttitude=extras.getString("longttitude");
                    getAddress(lat,lon);



                    if (!extras.getString("selectedPlace").equals("")) {
                        txt_address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
//                        address.setText(extras.getString("selectedPlace").substring(0, extras.getString("selectedPlace").indexOf(",")));
                    }
                    txt_pincode.setText(extras.getString("zip"));
                    txt_city.setText(extras.getString("city"));
                    txt_state.setText(extras.getString("state"));
//                    String servicearea = extras.getString("city") + ", " + extras.getString("state");
//                    Logger.printMessage("ServiceArea-->", servicearea);
//                    tv_service_area.setText(servicearea);


                    if (!txt_address.getText().toString().trim().equals("") ||
                            !txt_city.getText().toString().trim().equals("")
                            || !txt_state.getText().toString().equals("")
                            || !txt_pincode.getText().toString().equals("")
                            ) {
                        tv_confirmlater.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        tv_confirmlater.setText("continue to confirm your account");
                        confirmOrNot = true;
                    } else {
                        tv_confirmlater.setBackgroundColor(getResources().getColor(R.color.colorDarkGray));
                        tv_confirmlater.setText("confirm later and continue");
                        confirmOrNot = false;
                    }
                }
            }
        }
    }

    public void validation() {
        if (txt_address.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "Please choose address", Toast.LENGTH_SHORT).show();
        } else {
            if (txt_city.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "Please select address which have city,state and pin code", Toast.LENGTH_SHORT).show();
            } else {
                if (txt_state.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please select address which have city,state and pin code", Toast.LENGTH_SHORT).show();
                } else {
                    ///ggggggggggggggggggggggggggggggggg
                    if (txt_pincode.getText().toString().trim().equals("")) {
                        Toast.makeText(getActivity(), "Please select address which have city,state and pin code", Toast.LENGTH_SHORT).show();
                    } else {
                        callVerifiedAddress();
                    }
                }
            }
        }
    }

    private void callVerifiedAddress() {

        Logger.printMessage("user_id", ProApplication.getInstance().getUserId());
        Logger.printMessage("verify_country", country);
        Logger.printMessage("verify_address", txt_address.getText().toString().trim());
        Logger.printMessage("verify_city", txt_city.getText().toString().trim());
        Logger.printMessage("verify_state", txt_state.getText().toString().trim());
        Logger.printMessage("verify_zip", txt_pincode.getText().toString().trim());
        Logger.printMessage("lat",lattitude);
        Logger.printMessage("lon",longttitude);

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", ProApplication.getInstance().getUserId());
        params.put("verify_country",  country);
        params.put("verify_address", txt_address.getText().toString().trim().split(",")[0]);
        params.put("verify_city", txt_city.getText().toString().trim());
        params.put("verify_state", txt_state.getText().toString().trim());
        params.put("verify_zip", txt_pincode.getText().toString().trim());
        params.put("verify_latitude", lattitude);
        params.put("verify_longitude", longttitude);

        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_verified_address, params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("resultDoc", result);
                ((GetVerificationActivity) getActivity()).callVerificationFragments(5);
//                if (!((GetVerificationActivity) getActivity()).verifyPin) {
//                    ((GetVerificationActivity) getActivity()).callVerificationFragments(5);
//                } else {
//                    Intent intent = new Intent(getActivity(), LandScreenActivity.class);
//                    // set the new task and clear flags
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                }
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

    }
}