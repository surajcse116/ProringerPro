package com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav;

import android.content.Intent;
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
import com.android.llc.proringer.pro.proringerpro.activities.MyProjectDetailsActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.ProjectListingAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

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
 * -->
 */

public class MyProjectsFragment extends Fragment {
    RecyclerView rcv_project_list;
    LinearLayout LLNetworkDisconnection, LL_Main;
    ProRegularTextView tv_empty_show;
    ArrayList<SetGetAPI> arrayList = null;
    MyLoader myload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_projects, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LLNetworkDisconnection = (LinearLayout) view.findViewById(R.id.LLNetworkDisconnection);
        LL_Main = (LinearLayout) view.findViewById(R.id.LL_Main);

        rcv_project_list = (RecyclerView) view.findViewById(R.id.rcv_project_list);
        tv_empty_show = (ProRegularTextView) view.findViewById(R.id.tv_empty_show);

        rcv_project_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_empty_show.setVisibility(View.GONE);

        myload = new MyLoader(getActivity());

        showData();
    }


    public interface onOptionSelected {
        void onItemPassed(int position, JSONObject value);
    }

    public void showData() {
        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);

        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_pro_myproject, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myload.dismissLoader();
                Logger.printMessage("Result", result);
                try {
                    if (new JSONObject(result).getJSONArray("info_array").length() > 0) {

                        rcv_project_list.setVisibility(View.VISIBLE);
                        tv_empty_show.setVisibility(View.GONE);

                        rcv_project_list.setAdapter(new ProjectListingAdapter(getActivity(), new JSONObject(result).getJSONArray("info_array"), new onOptionSelected() {
                            @Override
                            public void onItemPassed(int position, JSONObject value) {

                                try {
                                    Intent intent = new Intent(getActivity(), MyProjectDetailsActivity.class);
                                    intent.putExtra("project_id",value.getString("project_id"));
                                    getActivity().startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
                    } else {
                        rcv_project_list.setVisibility(View.GONE);
                        tv_empty_show.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myload.dismissLoader();

//                CustomAlert customAlert = new CustomAlert();
//                customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
//                    @Override
//                    public void callBackOk() {
//
//                    }
//
//                    @Override
//                    public void callBackCancel() {
//
//                    }
//                });
            }

            @Override
            public void onError(String error) {
                myload.dismissLoader();
                if (error.equalsIgnoreCase("No internet connection found. Please check your internet connection.")) {
                    LLNetworkDisconnection.setVisibility(View.VISIBLE);
                    LL_Main.setVisibility(View.GONE);
                }
            }

            @Override
            public void onStart() {
                myload.showLoader();
            }
        });
    }


}
