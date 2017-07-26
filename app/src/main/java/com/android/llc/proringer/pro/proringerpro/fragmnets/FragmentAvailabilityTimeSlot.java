package com.android.llc.proringer.pro.proringerpro.fragmnets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AvailibilityTimeSlotAdapter;

/**
 * Created by su on 7/26/17.
 */

public class FragmentAvailabilityTimeSlot extends Fragment {
    private RecyclerView rcv_;
    AvailibilityTimeSlotAdapter availibilityTimeSlotAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_availablity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_ = (RecyclerView) view.findViewById(R.id.rcv_);
        rcv_.setLayoutManager(new LinearLayoutManager(getActivity()));

        availibilityTimeSlotAdapter=new AvailibilityTimeSlotAdapter(getActivity());
        rcv_.setAdapter(availibilityTimeSlotAdapter);
    }
}
