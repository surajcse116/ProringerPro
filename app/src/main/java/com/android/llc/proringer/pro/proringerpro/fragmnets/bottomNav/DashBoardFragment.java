package com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.AddServiceAreaActivity;
import com.android.llc.proringer.pro.proringerpro.activities.AddServicesActivity;
import com.android.llc.proringer.pro.proringerpro.activities.CompanyProfileActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LicenceActivity;
import com.android.llc.proringer.pro.proringerpro.activities.PortFolioActivity;
import com.android.llc.proringer.pro.proringerpro.activities.Premium;
import com.android.llc.proringer.pro.proringerpro.activities.Userinformation;
import com.android.llc.proringer.pro.proringerpro.pojo.PortFolio;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProSemiBoldEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;

/**
 * Created by su on 9/22/17.
 */

public class DashBoardFragment extends Fragment {

    ProSemiBoldTextView tv_userinfo,log_in;
    RelativeLayout userInformation,servicearea,service_setting,licence,login_settings,Protofolio;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashborad, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInformation=(RelativeLayout)view.findViewById(R.id.userInformation);
        servicearea=(RelativeLayout)view.findViewById(R.id.servicearea);
        service_setting=(RelativeLayout)view.findViewById(R.id.service_setting);
        licence=(RelativeLayout)view.findViewById(R.id.licence);
        login_settings=(RelativeLayout)view.findViewById(R.id.login_settings);
        Protofolio=(RelativeLayout)view.findViewById(R.id.Protofolio);
        log_in=(ProSemiBoldTextView)view.findViewById(R.id.log_in);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), Premium.class);
                startActivity(i);
            }
        });

        userInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), Userinformation.class);
                startActivity(i);
            }
        });
        servicearea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), AddServiceAreaActivity.class);
                startActivity(i);
            }
        });
        service_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), AddServicesActivity.class);
                startActivity(i);
            }
        });

        licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),LicenceActivity.class);
                startActivity(i);
            }
        });
        login_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), CompanyProfileActivity.class);
                startActivity(i);
            }
        });
        Protofolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), PortFolioActivity.class);
                startActivity(i);
            }
        });
    }
}