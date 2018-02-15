package com.android.llc.proringer.pro.proringerpro.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImage;
import com.android.llc.proringer.pro.proringerpro.cropImagePackage.CropImageView;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetChatPojo;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProLightEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    SetGetChatPojo chatPojo;
    ArrayList<SetGetAPIPostData> arrayList;
    ArrayList<SetGetChatPojo> prochatList;
    IndividualChatAdapter individualChatAdapter;
    ImageView iv_pic, msg_send_button,imv_selectpic;
    ProRegularTextView msg_homeowner_name, tv_toolbar;
    ProLightEditText et_send_msg;
    String mycurrentphotopath = "";
    File file = null;
    String reciver_id="";

    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
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
        imv_selectpic=(ImageView)findViewById(R.id.imv_selectpic);

        project_id = getIntent().getExtras().getString("project_id");
        Logger.printMessage("project_id--->", project_id);
        arrayList = new ArrayList<>();

        chat_list = (RecyclerView) findViewById(R.id.chat_list);
        chat_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));


        imv_selectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndividualMessageActivity.this, PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                startActivityForResult(intent, 200);
            }
        });

        img_background = (ImageView) findViewById(R.id.img_background);

        Glide.with(this)
                .load(R.drawable.chat_background)
                .into(img_background);


        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -3);

        LoadChatDetails();


        msg_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_send_msg.getText().toString().trim().equals(""))
                {
                    Toast.makeText(IndividualMessageActivity.this, "Please type your message", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendTextMessage();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mycurrentphotopath = result.getUri().toString();
                Logger.printMessage("path-->", mycurrentphotopath);
                if (mycurrentphotopath==null)
                {
                    Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    file=new File(mycurrentphotopath);
                    Log.d("File", String.valueOf(file));
                    senmessageimage();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(IndividualMessageActivity.this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
//            showImagePickerOption();
            startCropImageActivity(null);
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        Intent intent = CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(false)
                .setAspectRatio(1, 1)
                .getIntent(IndividualMessageActivity.this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }
    private void LoadChatDetails() {
        prochatList.clear();
        arrayList = new ArrayList<>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("project_id");
        setGetAPIPostData.setValues(project_id);
        arrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(this, ProConstant.app_pro_project_message, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("chat_result", result);
                JSONObject object = null;
                et_send_msg.setText("");
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

                                            chatPojo = new SetGetChatPojo();
                                            if (k == 0) {
                                                chatPojo.setDate(msg_list.getJSONObject(j).getString("date"));
                                            } else {
                                                chatPojo.setDate("");
                                            }


                                            // Log.d("infoarrsize",""+object.getJSONArray(msg_list.getJSONObject(j).getJSONArray("info").length());
                                            chatPojo.setUser(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("user"));

                                            chatPojo.setSender_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("sender_id"));
                                            Logger.printMessage("sender_id", chatPojo.getSender_id());
                                            chatPojo.setReceiver_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("receiver_id"));
                                            Logger.printMessage("Receiver_id", chatPojo.getReceiver_id());
                                            chatPojo.setMessage_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("message_id"));
                                            Logger.printMessage("Message_id", chatPojo.getMessage_id());
                                            chatPojo.setProject_id(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("project_id"));
                                            Logger.printMessage("Project_id", chatPojo.getProject_id());
                                            chatPojo.setMessage_info(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("message_info"));
                                            Logger.printMessage("Message_info", chatPojo.getMessage_info());
                                            chatPojo.setOther_file_type(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("other_file_type"));
                                            Logger.printMessage("Other_file_type", chatPojo.getOther_file_type());
                                            chatPojo.setMsg_attachment(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("msg_attachment"));
                                            Logger.printMessage("Msg_attachment", chatPojo.getMsg_attachment());
                                            chatPojo.setTime_status(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("time_status"));
                                            Logger.printMessage("Time_status", chatPojo.getTime_status());
                                            chatPojo.setTime_show(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("time_show"));
                                            Logger.printMessage("Time_show", chatPojo.getTime_show());
                                            chatPojo.setUser_name(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("user_name"));
                                            Logger.printMessage("user_name", chatPojo.getUser_name());
                                            chatPojo.setUsersimage(msg_list.getJSONObject(j).getJSONArray("info").getJSONObject(k).getString("usersimage"));
                                            Logger.printMessage("userimage", chatPojo.getUsersimage());
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
    public void sendTextMessage()
    {
        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id",chatPojo.getSender_id());
        Params.put("project_id",chatPojo.getProject_id());
        Params.put("receiver_users_id",chatPojo.getReceiver_id());
        Params.put("message",et_send_msg.getText().toString().trim());
        Params.put("attach_file","");
        Params.put("ios_mod","");

        new CustomJSONParser().fireAPIForPostMethod(IndividualMessageActivity.this, ProConstant.send_message, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {


                Logger.printMessage("Result",result);

                try {
                    JSONObject job= new JSONObject(result);
                    LoadChatDetails();
                    Toast.makeText(IndividualMessageActivity.this, ""+job.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                Toast.makeText(IndividualMessageActivity.this, ""+response, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onStart() {

            }
        });
    }
    public void senmessageimage()
    {
        ArrayList<SetGetAPIPostData> arrayListPostParamsValues = new ArrayList<>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(chatPojo.getSender_id());
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("project_id");
        setGetAPIPostData.setValues(chatPojo.getProject_id());
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("receiver_users_id");
        setGetAPIPostData.setValues(chatPojo.getReceiver_id());
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("message");
        setGetAPIPostData.setValues("");
        arrayListPostParamsValues.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("ios_mod");
        setGetAPIPostData.setValues("");
        arrayListPostParamsValues.add(setGetAPIPostData);

        ArrayList<File> filesImages = new ArrayList<>();
        filesImages.add(file);

        CustomJSONParser.ImageParam = "attach_file";
        new CustomJSONParser().APIForWithPhotoPostMethod(IndividualMessageActivity.this, ProConstant.send_message, arrayListPostParamsValues, filesImages, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("Respose",result);

                JSONObject job= null;
                try {
                    job = new JSONObject(result);
                    LoadChatDetails();
                    Toast.makeText(IndividualMessageActivity.this, ""+job.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                Toast.makeText(IndividualMessageActivity.this, ""+response, Toast.LENGTH_SHORT).show();
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


    public boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 5000);
            return false;
        }
        return true;
    }

}