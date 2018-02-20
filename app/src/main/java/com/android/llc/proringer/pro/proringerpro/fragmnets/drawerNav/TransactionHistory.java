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
import com.android.llc.proringer.pro.proringerpro.adapter.AdapterTransactionHistory;

/**
 * Created by su on 2/20/18.
 */

public class TransactionHistory extends Fragment{
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView) view.findViewById(R.id.rcv_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdapterTransactionHistory adapterTransactionHistory=new AdapterTransactionHistory(getActivity());
        recyclerView.setAdapter(adapterTransactionHistory);
    }
}
