package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.IndevidualChatAdapter;
import com.android.llc.proringer.pro.proringerpro.pojo.ChatPojo;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_chat_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        chat_list = (RecyclerView) findViewById(R.id.chat_list);
        chat_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        LinkedList<ChatPojo> chatList = new LinkedList<>();

        img_background = (ImageView) findViewById(R.id.img_background);

        Glide.with(this)
                .load(R.drawable.chat_background)
                .into(img_background);


        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -3);

        ChatPojo pojo1 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                true,
                "Hello- world ",
                true,
                true,
                "");
        chatList.add(pojo1);
        ChatPojo pojo2 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                false,
                "HI there - world ",
                false,
                true,
                "");
        chatList.add(pojo2);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        ChatPojo pojo3 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                true,
                "Blahh... blahhh ... blahhhhhhhhh..... ",
                false,
                true,
                "");
        chatList.add(pojo3);
        ChatPojo pojo4 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                false,
                "",
                false,
                false,
                "http://visuallightbox.com/images/demo/macro1/data/images1/1.jpg");
        chatList.add(pojo4);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        ChatPojo pojo5 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                true,
                "This is latest",
                true,
                true,
                "");
        chatList.add(pojo5);
        ChatPojo pojo6 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                false,
                "Indeed",
                false,
                true,
                "");
        chatList.add(pojo6);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        ChatPojo pojo7 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                true,
                "This is latest",
                true,
                true,
                "");
        chatList.add(pojo7);
        ChatPojo pojo8 = new ChatPojo(
                new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()),
                false,
                "Indeed",
                false,
                true,
                "");
        chatList.add(pojo8);

        Collections.reverse(chatList);
        chat_list.setAdapter(new IndevidualChatAdapter(this, chatList));
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
