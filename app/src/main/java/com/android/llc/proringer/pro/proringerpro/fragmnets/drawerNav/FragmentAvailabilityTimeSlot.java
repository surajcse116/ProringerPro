package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AvailabilityRecyclerViewAdapter;

/**
 * Created by su on 7/26/17.
 */

public class FragmentAvailabilityTimeSlot extends Fragment {
    RecyclerView rcv_availability;
    AvailabilityRecyclerViewAdapter availabilityRecyclerViewAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_availability, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_availability = (RecyclerView) view.findViewById(R.id.rcv_availability);
        rcv_availability.setLayoutManager(new LinearLayoutManager(getActivity()));
        availabilityRecyclerViewAdapter = new AvailabilityRecyclerViewAdapter(getActivity());

        rcv_availability.setAdapter(availabilityRecyclerViewAdapter);
    }
}
