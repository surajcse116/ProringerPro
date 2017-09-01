package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.AddImageAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.utils.ImageTakerActivityCamera;
import com.android.llc.proringer.pro.proringerpro.utils.Logger;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.utils.PermissionController;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by su on 8/17/17.
 */

public class PortFolioActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 5;
    private static final int PICK_IMAGE = 3;
    RecyclerView rcv_port_folio,rcv_add_port_folio;
    RelativeLayout RLAddPortFolio, RLEmpty;
    ProSemiBoldTextView tv_add;
    AddImageAdapter addImageAdapter=null;
    ArrayList<String> portPolioImageGalleryArrayList = null;
    private String mCurrentPhotoPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RLAddPortFolio= (RelativeLayout) findViewById(R.id.RLAddPortFolio);
        RLEmpty = (RelativeLayout) findViewById(R.id.RLEmpty);

        tv_add= (ProSemiBoldTextView) findViewById(R.id.tv_add);

        portPolioImageGalleryArrayList = new ArrayList<>();

        rcv_port_folio= (RecyclerView) findViewById(R.id.rcv_port_folio);
        rcv_port_folio.setLayoutManager(new LinearLayoutManager(PortFolioActivity.this));

        rcv_add_port_folio= (RecyclerView) findViewById(R.id.rcv_add_port_folio);
        rcv_add_port_folio.setLayoutManager(new GridLayoutManager(PortFolioActivity.this,5));

        RLAddPortFolio.setVisibility(View.GONE);
        rcv_port_folio.setVisibility(View.GONE);
        RLEmpty.setVisibility(View.VISIBLE);

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
                Intent intent = new Intent(PortFolioActivity.this, PermissionController.class);
                intent.setAction(PermissionController.ACTION_READ_STORAGE_PERMISSION);
                startActivityForResult(intent, 200);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void showImagePickerOption() {
        final Dialog dialog = new Dialog(PortFolioActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_gallery_camera);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        RelativeLayout RLMain = (RelativeLayout) dialog.findViewById(R.id.RLMain);

        RLMain.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(PortFolioActivity.this)[1]) / 2;
//        RLMain.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        RLMain.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(PortFolioActivity.this)[1]) / 2;


        dialog.findViewById(R.id.img_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                if (intent.resolveActivity((PortFolioActivity.this).getPackageManager()) != null) {
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            }
        });

        dialog.findViewById(R.id.img_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ProConstant.cameraRequested = true;
                startActivityForResult(new Intent(PortFolioActivity.this, ImageTakerActivityCamera.class), REQUEST_IMAGE_CAPTURE);
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.printMessage("resultCode", "requestCode " + requestCode + " &b resultcode :: " + resultCode);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Logger.printMessage("image****", "" + data.getData());
            try {
                Uri uri = data.getData();
                File dataFile = new File(getRealPathFromURI(uri));
                if (!dataFile.exists())
                    Logger.printMessage("image****", "data file does not exists");
                mCurrentPhotoPath = dataFile.getAbsolutePath();

                Logger.printMessage("list_size", "" + portPolioImageGalleryArrayList.size());

                if (portPolioImageGalleryArrayList.size() < 10) {

                    portPolioImageGalleryArrayList.add(mCurrentPhotoPath);

                    if (addImageAdapter == null) {
                        addImageAdapter = new AddImageAdapter(PortFolioActivity.this, portPolioImageGalleryArrayList);
                        rcv_add_port_folio.setAdapter(addImageAdapter);
                    } else {
                        addImageAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(PortFolioActivity.this, "You can't select pictures more than 10", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (data != null) {
                        mCurrentPhotoPath = data.getExtras().get("data").toString();
                        Logger.printMessage("image****", "" + mCurrentPhotoPath);


                        if (portPolioImageGalleryArrayList.size() < 10) {

                            portPolioImageGalleryArrayList.add(mCurrentPhotoPath);

                            if (addImageAdapter == null) {
                                addImageAdapter = new AddImageAdapter(PortFolioActivity.this, portPolioImageGalleryArrayList);
                                rcv_add_port_folio.setAdapter(addImageAdapter);
                            } else {
                                addImageAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(PortFolioActivity.this, "You can't select pictures more than 10", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }, 800);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            showImagePickerOption();
        }
    }


    public String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
