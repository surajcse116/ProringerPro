package com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.ProjectMessageAdapter;
import com.android.llc.proringer.pro.proringerpro.helper.onItemClick;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetProjectMessage;

import java.util.ArrayList;


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
 */

public class MessageFragment extends Fragment {
    private RecyclerView project_message_list;
    private ProjectMessageAdapter adapter;
    ArrayList<SetGetProjectMessage> setGetProjectMessageArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setGetProjectMessageArrayList = new ArrayList<>();


        for (int i = 0; i < 15; i++) {
            SetGetProjectMessage setGetProjectMessage = new SetGetProjectMessage();
            setGetProjectMessage.setIsOpen(false);
            setGetProjectMessage.setTagName("view " + i);
            setGetProjectMessageArrayList.add(setGetProjectMessage);
        }

        project_message_list = (RecyclerView) view.findViewById(R.id.message_list);
        project_message_list.setLayoutManager(new LinearLayoutManager((LandScreenActivity) getActivity()));


        adapter = new ProjectMessageAdapter((LandScreenActivity) getActivity(), setGetProjectMessageArrayList, new onItemClick() {
            @Override
            public void onItemClick(int pos) {
                //((LandScreenActivity) getActivity()).toggleToolBar(true);
                ((LandScreenActivity) getActivity()).transactProjectMessaging();
            }
        });

        project_message_list.setAdapter(adapter);
    }
}
