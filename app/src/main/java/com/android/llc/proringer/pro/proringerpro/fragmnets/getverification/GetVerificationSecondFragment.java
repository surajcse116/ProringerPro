package com.android.llc.proringer.pro.proringerpro.fragmnets.getverification;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.GetVerificationActivity;
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
 * A simple {@link Fragment} subclass.
 */
public class GetVerificationSecondFragment extends Fragment {
    ProRegularTextView tv3, tv_confier_later_continew, tv_conferm;
    ProLightEditText et_confirmpin;
    MyLoader myload;
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
        myload= new MyLoader(getActivity());
        et_confirmpin = view.findViewById(R.id.et_confirmpin);
        tv_conferm = view.findViewById(R.id.tv_conferm);


        et_confirmpin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_confirmpin.getText().toString().trim().equals("") || et_confirmpin.getText().toString().trim().length() < 6) {
                    tv_conferm.setBackgroundColor(getResources().getColor(R.color.colorDarkGray));
                    tv_confier_later_continew.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else
                {
                    tv_conferm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    tv_confier_later_continew.setBackgroundColor(getResources().getColor(R.color.colorDarkGray));
                }
            }
        });


        tv_conferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_confirmpin.getText().toString().trim().equals("") || et_confirmpin.getText().toString().trim().length() < 5) {
                    et_confirmpin.setError("Enter pin number");
                    et_confirmpin.requestFocus();
                } else {
                    CallVerifiedPin();
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

    private void CallVerifiedPin() {
        Logger.printMessage("CallVerifiedPin","CallVerifiedPin");
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", ProApplication.getInstance().getUserId());
        params.put("verify_pin_code", et_confirmpin.getText().toString().trim());
        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_verified_pin, params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("onSuccess","onSuccess");
                Logger.printMessage("verifyPin------>",result);
                JSONObject object= null;
                try {
                    object = new JSONObject(result);
                    if (object.getString("response").equals("true"))
                    {
                        ((GetVerificationActivity) getActivity()).callVerificationFragments(3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

               //
            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();
                try {
                    Toast.makeText(getActivity(), new JSONObject(response).getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onStart() {
                myload.showLoader();
            }
        });
    }
}
