package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.utils.ImageFilePath;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by su on 8/18/17.
 */

public class LicenceActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_PERMISSION1 = 3000;
    private static final int REQUEST_WRITE_PERMISSION2 = 4000;
    RecyclerView rcv_licence_list;
    RelativeLayout RLAddLicence, RLEmpty;
    LicenceAdapter licenceAdapter;
    File photofile = null;
    Dialog dialog = null;
    LinearLayout LLEdit, LLUpload;
    ImageView img_licence_file;
    private int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RLAddLicence = (RelativeLayout) findViewById(R.id.RLAddLicence);
        RLEmpty = (RelativeLayout) findViewById(R.id.RLEmpty);

        rcv_licence_list = (RecyclerView) findViewById(R.id.rcv_licence_list);
        rcv_licence_list.setLayoutManager(new LinearLayoutManager(LicenceActivity.this));

        RLAddLicence.setVisibility(View.GONE);
        rcv_licence_list.setVisibility(View.GONE);
        RLEmpty.setVisibility(View.VISIBLE);

        LLUpload = (LinearLayout) findViewById(R.id.LLUpload);
        LLEdit = (LinearLayout) findViewById(R.id.LLEdit);
        img_licence_file = (ImageView) findViewById(R.id.img_licence_file);


        LLUpload.setVisibility(View.VISIBLE);
        LLEdit.setVisibility(View.GONE);


        findViewById(R.id.img_add_licence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RLAddLicence.getVisibility() == View.VISIBLE) {
                    RLAddLicence.setVisibility(View.GONE);
                    rcv_licence_list.setVisibility(View.VISIBLE);
                } else {
                    RLAddLicence.setVisibility(View.VISIBLE);
                    rcv_licence_list.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });
        loadListAndSetAdapter();
    }

    public void loadListAndSetAdapter() {
        if (licenceAdapter == null) {
            licenceAdapter = new LicenceAdapter(LicenceActivity.this);
            rcv_licence_list.setAdapter(licenceAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPhotoDialog() {
        dialog = new Dialog(LicenceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_image_or_pdf);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ImageView img_gallery = (ImageView) dialog.findViewById(R.id.img_gallery);
        ImageView img_pdf = (ImageView) dialog.findViewById(R.id.img_pdf);
        ProRegularTextView TXTTitle = (ProRegularTextView) dialog.findViewById(R.id.Title);

        LinearLayout LLMain = (LinearLayout) dialog.findViewById(R.id.LLMain);

        LLMain.getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(LicenceActivity.this)[1]) / 2;
//        RLMain.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        LLMain.getLayoutParams().height = (MethodsUtils.getScreenHeightAndWidth(LicenceActivity.this)[1]) / 3;

        TXTTitle.setText("Licence");

        img_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION1);
                } else {
                    openImageGallery();
                }

            }
        });

        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION2);
                } else {
                    openPDFGallery();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImageGallery();
        }
        if (requestCode == REQUEST_WRITE_PERMISSION2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openPDFGallery();
        }
    }

    private void openImageGallery() {
        try {
            photofile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        dialog.dismiss();
    }

    private void openPDFGallery() {

    }

    File createImageFile() throws IOException {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());///new approach
        String imagefilename = "IMAGE_" + timestamp + "_";///new approach
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imagefilename, ".jpg", storageDirectory);/////new approach
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && photofile != null) {
            Uri selectedImageURI = data.getData();
            try {

                LLUpload.setVisibility(View.GONE);
                LLEdit.setVisibility(View.VISIBLE);

                photofile = new File(ImageFilePath.getPath(getApplicationContext(), selectedImageURI));
                Glide.with(getApplicationContext()).load(photofile).into(img_licence_file);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
