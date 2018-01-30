package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.IndividualChatAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetChatPojo;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by bodhidipta on 20/06/17.
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

public class IndividualMessageActivity extends AppCompatActivity {
    RecyclerView chat_list;
    ImageView img_background;
    String project_id = "";
    ArrayList<SetGetAPI> arrayList;
    ArrayList<SetGetChatPojo> prochatList;
    IndividualChatAdapter individualChatAdapter;
    ImageView iv_pic, msg_send_button;
    ProRegularTextView msg_homeowner_name, tv_toolbar;
    ProLightEditText et_send_msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_chat_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prochatList = new ArrayList<>();
        iv_pic = findViewById(R.id.iv_pic);
        msg_homeowner_name = findViewById(R.id.msg_homeowner_name);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        msg_send_button = findViewById(R.id.msg_send_button);
        et_send_msg = findViewById(R.id.et_send_msg);

        project_id = getIntent().getExtras().getString("project_id");
        Log.d("project_id--->", project_id);
        arrayList = new ArrayList<>();

        chat_list = (RecyclerView) findViewById(R.id.chat_list);
        chat_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));


        img_background = (ImageView) findViewById(R.id.img_background);

      /*  Glide.with(this)
                .load(R.drawable.chat_background)
                .into(img_background);
*/

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -3);
        msg_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_send_msg.getText().toString().trim().equals("")) {

                    LoadChatDetails();
                } else {
                    Toast.makeText(IndividualMessageActivity.this, "Enter Message", Toast.LENGTH_SHORT).show();
                }

            }
        });

        LoadChatDetails();
    }

    private void LoadChatDetails() {
        prochatList.clear();
        arrayList = new ArrayList<>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);

        setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("project_id");
        setGetAPI.setValues(project_id);
        arrayList.add(setGetAPI);

        new CustomJSONParser().fireAPIForGetMethod(this, ProConstant.app_pro_project_message, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Log.d("chat_result", result);
                JSONObject object = null;

                try {
                    object = new JSONObject(result);

                    if (object.has("info_array")) {
                        if (object.getJSONArray("info_array").length() > 0) {


//                            tv_toolbar.setText(object.getJSONArray("info_array").getJSONObject(0).getString("project_name_active"));
                            msg_homeowner_name.setText(object.getJSONArray("info_array").getJSONObject(0).getString("homeowner_name"));
                            Glide.with(IndividualMessageActivity.this).load(object.getJSONArray("info_array").getJSONObject(0).getString("homeowner_user_image")).fitCenter().into(iv_pic);

                            JSONArray info_array = object.getJSONArray("info_array");
                            if (info_array.getJSONObject(0).has("msg_list")) {
                                if (info_array.getJSONObject(0).getJSONArray("msg_list").length() > 0) {
                                    //  Log.d("msglistsize",""+object.getJSONArray("info_array").getJSONObject(i).getJSONArray("msg_list").length());
                                    JSONArray msg_list = info_array.getJSONObject(0).getJSONArray("msg_list");
                                    for (int j = 0; j < msg_list.length(); j++) {
                                        //chatPojo.setDate(object.getJSONArray("info_array").getJSONObject(i).getJSONArray("msg_list").getJSONObject(j).getString("date"));
                                        for (int k = 0; k < msg_list.getJSONObject(j).getJSONArray("info").length(); k++) {

                                            SetGetChatPojo chatPojo = new SetGetChatPojo();
                                            if (k == 0) {
                                                chatPojo.setDate(msg_list.getJSONObject(j).getString("date"));
                                            } else {
                                                chatPojo.setDate("");
                                            }


                                            // Log.d("infoarrsize",""+object.getJSONArray(msg_list.getJSONObject(j).getJSONArray("info").length());
                                            chatPojo.setUser(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("user"));

                                            chatPojo.setSender_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("sender_id"));
                                            Log.d("sender_id", chatPojo.getSender_id());
                                            chatPojo.setReceiver_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("receiver_id"));
                                            Log.d("Receiver_id", chatPojo.getReceiver_id());
                                            chatPojo.setMessage_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("message_id"));
                                            Log.d("Message_id", chatPojo.getMessage_id());
                                            chatPojo.setProject_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("project_id"));
                                            Log.d("Project_id", chatPojo.getProject_id());
                                            chatPojo.setMessage_info(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("message_info"));
                                            Log.d("Message_info", chatPojo.getMessage_info());
                                            chatPojo.setOther_file_type(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("other_file_type"));
                                            Log.d("Other_file_type", chatPojo.getOther_file_type());
                                            chatPojo.setMsg_attachment(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("msg_attachment"));
                                            Log.d("Msg_attachment", chatPojo.getMsg_attachment());
                                            chatPojo.setTime_status(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("time_status"));
                                            Log.d("Time_status", chatPojo.getTime_status());
                                            chatPojo.setTime_show(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("time_show"));
                                            Log.d("Time_show", chatPojo.getTime_show());
                                            chatPojo.setUser_name(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("user_name"));
                                            Log.d("user_name", chatPojo.getUser_name());
                                            chatPojo.setUsersimage(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("usersimage"));
                                            Log.d("userimage", chatPojo.getUsersimage());
                                            prochatList.add(chatPojo);


                                        }
                                    }


                                    Collections.reverse(prochatList);
                                    if (individualChatAdapter == null) {
                                        individualChatAdapter = new IndividualChatAdapter(IndividualMessageActivity.this, prochatList);
                                        chat_list.setAdapter(individualChatAdapter);
                                    } else {
                                        individualChatAdapter.notifyDataSetChanged();
                                    }
                                }
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

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}