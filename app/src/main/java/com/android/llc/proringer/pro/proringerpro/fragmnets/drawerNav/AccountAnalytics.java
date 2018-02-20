package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;

/**
 * Created by su on 2/20/18.
 */

public class AccountAnalytics extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transact_analytics, container, false);
    }
}
