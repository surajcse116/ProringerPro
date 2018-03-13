package com.android.llc.proringer.pro.proringerpro.fragmnets.maincontent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.IndividualMessageActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.ProjectDetailedMessageAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetProjectMessageDetails;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by bodhidipta on 13/06/17.
 * <!-- * Copyright (c) 2017, The Proringer-->
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

public class ProjectMessagingFragment extends Fragment {
    RelativeLayout detailed_project_search;
    RecyclerView message_list;
    MyLoader myLoader;
    ArrayList<SetGetProjectMessageDetails> setGetProjectMessageDetailsArrayList;
    ProjectDetailedMessageAdapter projectDetailedMessageAdapter;
    ArrayList<SetGetAPIPostData> arrayList;
    static String mypojectid="";
    ProRegularEditText et_search;

    public static ProjectMessagingFragment getInstance(String mypojectid1){
        mypojectid=mypojectid1;
        ProjectMessagingFragment projectMessagingFragment =new ProjectMessagingFragment();
        return projectMessagingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.project_detailed_messaging, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mypojectid = getArguments().getString("project_id");
        Logger.printMessage("mypojectid------>",mypojectid);
        et_search=view.findViewById(R.id.et_search);
        setGetProjectMessageDetailsArrayList =new ArrayList<>();
        myLoader=new MyLoader(getActivity());
        detailed_project_search = (RelativeLayout) view.findViewById(R.id.detailed_project_search);

        message_list = (RecyclerView) view.findViewById(R.id.message_list);
        message_list.setLayoutManager(new LinearLayoutManager((LandScreenActivity)getActivity()));
        LoadandShowUserMessageList();

    }

    private void LoadandShowUserMessageList() {

        arrayList=new ArrayList<>();
        SetGetAPIPostData setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("project_id");
        setGetAPIPostData.setValues(mypojectid);
        arrayList.add(setGetAPIPostData);

//        setGetAPIPostData=new SetGetAPIPostData();
//        setGetAPIPostData.setPARAMS("list_search");
//        setGetAPIPostData.setValues(et_search.getText().toString().trim());
//        arrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_pro_project_message, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("onSuccess",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.has("info_array"))
                    {
                        if (jsonObject.getJSONArray("info_array").length()>0)
                        {
                            for (int i=0;i<jsonObject.getJSONArray("info_array").length();i++)
                            {
                                SetGetProjectMessageDetails setgetProjectMessageDetails=new SetGetProjectMessageDetails();
                                setgetProjectMessageDetails.setProj_img_active(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("proj_img_active"));
                                setgetProjectMessageDetails.setProject_name_active(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("project_name_active"));
                                Logger.printMessage("name---------->",jsonObject.getJSONArray("info_array").getJSONObject(i).getString("project_name_active"));
                                setgetProjectMessageDetails.setProj_id_active(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("proj_id_active"));
                                setGetProjectMessageDetailsArrayList.add(setgetProjectMessageDetails);
                            }
                            if (projectDetailedMessageAdapter==null)
                            {
                                projectDetailedMessageAdapter=new ProjectDetailedMessageAdapter(getActivity(), setGetProjectMessageDetailsArrayList, new onOptionSelected() {
                                    @Override
                                    public void onItemClick(int position, String value) {
                                        Intent intent=new Intent(getActivity(),IndividualMessageActivity.class);

                                        startActivity(intent);
                                    }
                                });
                                message_list.setAdapter(projectDetailedMessageAdapter);
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
            }

            @Override
            public void onError(String error) {
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });



    }
    public interface onOptionSelected {
        void onItemClick(int position, String value);
    }

}