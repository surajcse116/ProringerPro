package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by su on 28/2/18.
 */

public class GetVerifactionFifthverifyph extends Fragment {
    ProLightEditText et_pin_number;
    ProRegularTextView tv_confirm,tv_close;
    MyLoader myLoader;
    boolean val=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.get_verified_fifth_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_pin_number=view.findViewById(R.id.et_pin_number);
        tv_confirm=view.findViewById(R.id.tv_confirm_fifth);
        Log.d("hgdhsjjs", String.valueOf(val));
        tv_close=view.findViewById(R.id.tv_close);
        myLoader=new MyLoader(getActivity());
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandScreenActivity) getActivity()).transactDashBoard();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_pin_number.getText().toString().trim().equals(""))
                {
                    et_pin_number.setError("Please Enter Pin");
                    et_pin_number.requestFocus();
                    closeKeyboard();
                }
                else
                {
                    callVerifiedConfirmedpin();
                    closeKeyboard();
                }
            }
        });
    }

    private void callVerifiedConfirmedpin() {
        HashMap<String,String> params=new HashMap<>();
        params.put("user_id", ProApplication.getInstance().getUserId());
        params.put("phone_pin",et_pin_number.getText().toString());
        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_verified_confirm, params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("appproverifiedconfirm->",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if (object.getString("response").equals("true"))
                    {
                        Toast.makeText(getActivity(),object.getString("message"),Toast.LENGTH_SHORT).show();
                        ((LandScreenActivity) getActivity()).transactDashBoard();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
                try {
                    Toast.makeText(getActivity(), new JSONObject(response).getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
