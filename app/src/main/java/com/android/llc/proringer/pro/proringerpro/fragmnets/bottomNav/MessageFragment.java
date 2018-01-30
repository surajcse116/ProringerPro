package com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.IndividualMessageActivity;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.ProjectMessageAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.helper.onItemClick;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetProjectMessage;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;
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
 */

public class MessageFragment extends Fragment {
    private RecyclerView project_message_list;
    private ProjectMessageAdapter adapter;
    ArrayList<SetGetProjectMessage> setGetProjectMessageArrayList;
    ProRegularEditText et_proj_search;
    ArrayList<SetGetAPIPostData> arrayList;
    MyLoader myLoader = null;
    TextWatcher mySearchTextWatcher;
    String search_key="";
    ImageView img_clear;
    private InputMethodManager keyboard;
    ProRegularTextView tv_empty_show;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setGetProjectMessageArrayList = new ArrayList<>();
        et_proj_search=view.findViewById(R.id.et_proj_search);
        myLoader=new MyLoader(getActivity());
       // LoadnadShowData();
        keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        img_clear=view.findViewById(R.id.img_clear);
        project_message_list = (RecyclerView) view.findViewById(R.id.message_list);
        project_message_list.setLayoutManager(new LinearLayoutManager((LandScreenActivity) getActivity()));
        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeypad();
                search_key="";
                et_proj_search.setText("");
                LoadnadShowData();
            }
        });
        et_proj_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (event!=null &&(event.getKeyCode()==KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE))
                {
                    Logger.printMessage("search_category", et_proj_search.getText().toString());
                    closeKeypad();
                    LoadnadShowData();
                }
                else if ((event != null && (actionId == KeyEvent.KEYCODE_DEL))) {
                    if (et_proj_search.getText().toString().equals("")) {
                        Logger.printMessage("search_category", et_proj_search.getText().toString());
                        closeKeypad();
                        LoadnadShowData();
                    }
                }
                return false;
            }
        });
        mySearchTextWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_key=s.toString().trim();
                if (search_key.length()>0)
                {
                    img_clear.setVisibility(View.VISIBLE);
                }
                else
                {
                    img_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        et_proj_search.addTextChangedListener(mySearchTextWatcher);
        tv_empty_show=view.findViewById(R.id.tv_empty_show);
    }

    private void closeKeypad() {

        try {
            keyboard.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadnadShowData() {
        setGetProjectMessageArrayList.clear();

        arrayList=new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData =new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("list_search");
        setGetAPIPostData.setValues(search_key);
        arrayList.add(setGetAPIPostData);


        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_pro_project_message, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {

                Logger.printMessage("onSuccess",result);

                myLoader.dismissLoader();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("info_array"))
                    {
                        if (jsonObject.getJSONArray("info_array").length()>0)
                        {
                            tv_empty_show.setVisibility(View.GONE);

                            project_message_list.setVisibility(View.VISIBLE);
                            for (int i=0;i<jsonObject.getJSONArray("info_array").length();i++)
                            {
                                SetGetProjectMessage setGetProjectMessage = new SetGetProjectMessage();
                                setGetProjectMessage.setProj_id(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("proj_id"));
                                setGetProjectMessage.setProj_image(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("proj_image"));
                                setGetProjectMessage.setProj_name(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("proj_name"));
                                setGetProjectMessage.setProject_applied_date(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("project_applied_date"));
                                setGetProjectMessage.setProject_applied_status(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("project_applied_status"));
                                setGetProjectMessage.setProject_status(jsonObject.getJSONArray("info_array").getJSONObject(i).getString("project_status"));
                                setGetProjectMessageArrayList.add(setGetProjectMessage);

                            }


                            if (adapter==null)
                            {
                                adapter=new ProjectMessageAdapter((LandScreenActivity) getActivity(), setGetProjectMessageArrayList, new onOptionSelected() {
                                    @Override
                                    public void onItemClick(int position, String value) {
                                       // ((LandScreenActivity) getActivity()).transactProjectMessaging(value);
                                        Intent intent=new Intent(getActivity(),IndividualMessageActivity.class);
                                        intent.putExtra("project_id",value);
                                        startActivity(intent);
                                        Logger.printMessage("project_id",value);

                                    }
                                });

                                project_message_list.setAdapter(adapter);
                            }
                            else{
                                adapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            project_message_list.setVisibility(View.GONE);
                            tv_empty_show.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                Logger.printMessage("onError-->",response);
                tv_empty_show.setVisibility(View.VISIBLE);
                project_message_list.setVisibility(View.GONE);
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
    @Override
    public void onResume() {
        super.onResume();
        LoadnadShowData();
    }
}
