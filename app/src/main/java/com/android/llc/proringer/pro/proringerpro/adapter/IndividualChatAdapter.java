package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.IndividualMessageActivity;
import com.android.llc.proringer.pro.proringerpro.helper.Downloadandshowfile;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetChatPojo;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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

public class IndividualChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private ArrayList<SetGetChatPojo> dataList;
    String MsgItemTouch;

    public IndividualChatAdapter(Context mcontext, ArrayList<SetGetChatPojo> list) {
        this.mcontext = mcontext;
        dataList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ////change////////
        if (viewType == 1) {
            return new ViewHolderReceiver(LayoutInflater.from(mcontext).inflate(R.layout.indevidual_list_row_receiver, parent, false));

        } else {
            return new ViewHolderSender(LayoutInflater.from(mcontext).inflate(R.layout.indevidual_list_row_sender, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case 1:
                ViewHolderReceiver viewHolderReceiver = (ViewHolderReceiver) holder;
                //header date
                if (!dataList.get(position).getDate().trim().equals(""))
                {
                    viewHolderReceiver.date_header.setVisibility(View.VISIBLE);
                    if (dataList.get(position).getDate().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
                        viewHolderReceiver.date_header.setText("Today");
                    } else {
                        viewHolderReceiver.date_header.setText(dataList.get(position).getDate());
                    }
                }
                else {
                    viewHolderReceiver.date_header.setVisibility(View.GONE);
                }

                ///for message
                if (dataList.get(position).getMsg_attachment().equals(""))
                {
                    viewHolderReceiver.receiver_image_cont.setVisibility(View.GONE);
                    viewHolderReceiver.receiver_message.setVisibility(View.VISIBLE);
                    viewHolderReceiver.receiver_message.setText(dataList.get(position).getMessage_info());
                    viewHolderReceiver.recever_msg_time.setText(dataList.get(position).getTime_show());

                }
                else {
                    viewHolderReceiver.receiver_message.setVisibility(View.GONE);
                    viewHolderReceiver.receiver_image_cont.setVisibility(View.VISIBLE);
                    Glide.with(mcontext).load(dataList.get(position).getMsg_attachment()).centerCrop().into(viewHolderReceiver.receiver_image);
                    viewHolderReceiver.imv_download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dataList.get(position).getMsg_attachment().equals(""))
                            {
                                Log.d("This is text","This is text");
                            }
                            else {
                                MsgItemTouch = "";
                                MsgItemTouch = dataList.get(position).getMsg_attachment();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    Logger.printMessage("mshdg",MsgItemTouch);
                                    if (((IndividualMessageActivity)mcontext).checkPermissions())
                                        // ((IndividualMessageActivity)mcontext).DownloadImage(dataList.get(position).getMsg_attachment());
                                        Log.d("This is message", "This is Image");
                                    // viewHolderSender.download_image.setVisibility(View.GONE);
                                    //((IndividualMessageActivity)mcontext).showImagePortFolioDialog(dataList.get(position).getMsg_attachment());
                                    DownloadAndShow();
                                } else {
                                    DownloadAndShow();
                                }

                            }


                        }
                    });

                }

                break;

            case 2:
                ViewHolderSender viewHolderSender = (ViewHolderSender) holder;

                if (!dataList.get(position).getDate().trim().equals(""))
                {
                    viewHolderSender.date_header.setVisibility(View.VISIBLE);
                    if (dataList.get(position).getDate().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
                        viewHolderSender.date_header.setText("Today");
                    } else {
                        viewHolderSender.date_header.setText(dataList.get(position).getDate());
                    }

                }
                else {
                    viewHolderSender.date_header.setVisibility(View.GONE);
                }

                if (dataList.get(position).getMsg_attachment().equals(""))
                {
                    viewHolderSender.sender_message.setVisibility(View.VISIBLE);
                    viewHolderSender.sender_image_cont.setVisibility(View.GONE);
                    viewHolderSender.sender_message.setText(dataList.get(position).getMessage_info());
                    viewHolderSender.sender_msg_time.setText(dataList.get(position).getTime_show());
                }
                else
                {
                    viewHolderSender.sender_image_cont.setVisibility(View.VISIBLE);
                    viewHolderSender.sender_message.setVisibility(View.GONE);
                    Glide.with(mcontext).load(dataList.get(position).getMsg_attachment()).centerCrop().into(viewHolderSender.sender_image);
                    viewHolderSender.imv_download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dataList.get(position).getMsg_attachment().equals(""))
                            {
                                Log.d("This is text","This is text");
                            }
                            else {
                                MsgItemTouch = "";
                                MsgItemTouch = dataList.get(position).getMsg_attachment();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    Logger.printMessage("mshdg",MsgItemTouch);
                                    if (((IndividualMessageActivity)mcontext).checkPermissions())
                                        // ((IndividualMessageActivity)mcontext).DownloadImage(dataList.get(position).getMsg_attachment());
                                        Log.d("This is message", "This is Image");
                                    // viewHolderSender.download_image.setVisibility(View.GONE);
                                    //((IndividualMessageActivity)mcontext).showImagePortFolioDialog(dataList.get(position).getMsg_attachment());
                                    DownloadAndShow();
                                } else {
                                    DownloadAndShow();
                                }

                            }
                        }
                    });
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolderReceiver extends RecyclerView.ViewHolder {
        ProRegularTextView date_header, receiver_message, receiver_image_date,recever_msg_time;
        ImageView receiver_image;
        ImageView imv_download;
        RelativeLayout receiver_image_cont;

        public ViewHolderReceiver(View itemView) {
            super(itemView);
            recever_msg_time=itemView.findViewById(R.id.recever_msg_time);
            date_header = (ProRegularTextView) itemView.findViewById(R.id.date_header);
            receiver_message = (ProRegularTextView) itemView.findViewById(R.id.receiver_message);
            receiver_image_date = (ProRegularTextView) itemView.findViewById(R.id.receiver_image_date);
            imv_download=(ImageView)itemView.findViewById(R.id.imv_download);
            receiver_image = (ImageView) itemView.findViewById(R.id.receiver_image);
            receiver_image_cont = (RelativeLayout) itemView.findViewById(R.id.receiver_image_cont);
        }
    }

    class ViewHolderSender extends RecyclerView.ViewHolder {
        ProRegularTextView date_header, sender_message, sender_image_time,sender_msg_time;
        ImageView sender_image;
        ImageView imv_download;
        RelativeLayout sender_image_cont;

        public ViewHolderSender(View itemView) {
            super(itemView);
            sender_msg_time=itemView.findViewById(R.id.sender_msg_time);
            date_header = (ProRegularTextView) itemView.findViewById(R.id.date_header);
            sender_message = (ProRegularTextView) itemView.findViewById(R.id.sender_message);
            sender_image_time = (ProRegularTextView) itemView.findViewById(R.id.sender_image_time);
            imv_download=(ImageView)itemView.findViewById(R.id.imv_download);
            sender_image = (ImageView) itemView.findViewById(R.id.sender_image);
            sender_image_cont = (RelativeLayout) itemView.findViewById(R.id.sender_image_cont);

        }
    }
    public void DownloadAndShow(){
        Downloadandshowfile.downloadAndOpenPDF(mcontext,MsgItemTouch);

//        final String filename = MsgItemTouch.substring( MsgItemTouch.lastIndexOf( "/" ) + 1 );
//
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(MsgItemTouch));
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//        request.setAllowedOverRoaming(false);
//        request.setTitle("Attached File Downloading " + filename);
//        request.setDescription("Downloading " + filename);
//        request.setVisibleInDownloadsUi(true);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/HappyWanNyan/"  + "/" + filename);
//
//
//        refid = downloadManager.enqueue(request);
//
//        Loger.MSG("OUT", "" + refid);
//
//        list.add(refid);
    }


    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).getUser().equals("S")) {
            return 2;
        } else {
            return 1;
        }
    }
}