package com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.WatchListAdapter;

/**
 * Created by bodhidipta on 12/06/17.
 * <!-- * Copyright (c) 2017, Proringer-->
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -->
 */

public class WatchListFragment extends Fragment {
    private RecyclerView rcv_watch_list;
    WatchListAdapter watchListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.watch_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcv_watch_list = (RecyclerView) view.findViewById(R.id.rcv_watch_list);
        rcv_watch_list.setLayoutManager(new LinearLayoutManager((LandScreenActivity)getActivity()));

        watchListAdapter=new WatchListAdapter(getActivity(), new onOptionSelected() {
            @Override
            public void onItemPassed(int position, String value) {

            }
        });


        rcv_watch_list.setAdapter(watchListAdapter);

    }
    public interface onOptionSelected {
        void onItemPassed(int position, String value);
    }

}
