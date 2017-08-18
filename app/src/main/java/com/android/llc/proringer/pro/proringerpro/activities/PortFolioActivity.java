package com.android.llc.proringer.pro.proringerpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AddImageAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.PortFolioAdapter;
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;

import java.util.ArrayList;

/**
 * Created by su on 8/17/17.
 */

public class PortFolioActivity extends AppCompatActivity {

    PortFolioAdapter portFolioAdapter=null;
    RecyclerView rcv_port_folio,rcv_add_port_folio;
    RelativeLayout RLAddPortFolio;
    ProSemiBoldTextView tv_add;

    AddImageAdapter addImageAdapter=null;

    ArrayList<String> stringAddImageArrayList=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RLAddPortFolio= (RelativeLayout) findViewById(R.id.RLAddPortFolio);

        tv_add= (ProSemiBoldTextView) findViewById(R.id.tv_add);

        stringAddImageArrayList=new ArrayList<>();

        rcv_port_folio= (RecyclerView) findViewById(R.id.rcv_port_folio);
        rcv_port_folio.setLayoutManager(new LinearLayoutManager(PortFolioActivity.this));

        rcv_add_port_folio= (RecyclerView) findViewById(R.id.rcv_add_port_folio);
        rcv_add_port_folio.setLayoutManager(new GridLayoutManager(PortFolioActivity.this,5));

        RLAddPortFolio.setVisibility(View.GONE);
        rcv_port_folio.setVisibility(View.VISIBLE);

        findViewById(R.id.img_add_port_folio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (RLAddPortFolio.getVisibility()==View.VISIBLE){
                    RLAddPortFolio.setVisibility(View.GONE);
                    rcv_port_folio.setVisibility(View.VISIBLE);
                }else {
                    RLAddPortFolio.setVisibility(View.VISIBLE);
                    rcv_port_folio.setVisibility(View.GONE);
                }

            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addImageInAdapter();
            }
        });

        loadListAndSetAdapter();
    }

    public void loadListAndSetAdapter(){
        if (portFolioAdapter==null){

            portFolioAdapter=new PortFolioAdapter(PortFolioActivity.this);
            rcv_port_folio.setAdapter(portFolioAdapter);
        }
    }
    public void addImageInAdapter(){

        Logger.printMessage("stringAddImageArrayListSize",""+stringAddImageArrayList.size());

        if(stringAddImageArrayList.size()<10){

            stringAddImageArrayList.add("");

            if (addImageAdapter==null){
                addImageAdapter=new AddImageAdapter(PortFolioActivity.this,stringAddImageArrayList);
                rcv_add_port_folio.setAdapter(addImageAdapter);
            }
            else {
                addImageAdapter.notifyDataSetChanged();
            }
        }
    }
}
