package com.android.llc.proringer.pro.proringerpro.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.adapter.ProsDetailsPortfolioImageAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.graphics.ImageViewTouch;
import com.android.llc.proringer.pro.proringerpro.graphics.ImageViewTouchBase;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.utils.MethodsUtils;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 2/21/18.
 */

public class ProsProjectGalleryActivity extends AppCompatActivity {
    MyLoader myLoader = null;
    RecyclerView rcv_portfolio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pros_project_gallery);
        rcv_portfolio = (RecyclerView) findViewById(R.id.rcv_portfolio);
//        rcv_portfolio.setLayoutManager(new GridLayoutManager(ProsProjectGalleryActivity.this,3));


        Display display = ProsProjectGalleryActivity.this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = size.x;
        x = (int) x / 3;

        GridLayoutManager linearLayoutManager = new GridLayoutManager(ProsProjectGalleryActivity.this, 3);
        rcv_portfolio.setLayoutManager(linearLayoutManager);

        myLoader = new MyLoader(this);
        loadImagePortFolio(getIntent().getStringExtra("portfolio_id"), x);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent();
                setResult(RESULT_CANCELED, backIntent);
                finish();
            }
        });
    }

    public void loadImagePortFolio(String portfolio_id, final int x) {


        ArrayList<SetGetAPIPostData> apiPostDataArrayList=new ArrayList<>();
        SetGetAPIPostData setGetAPIPostData=new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("portfolio_id");
        setGetAPIPostData.setValues(portfolio_id);
        apiPostDataArrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(ProsProjectGalleryActivity.this, ProConstant.app_individual_portfolio_image, apiPostDataArrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("Result", result);

                try {
                    JSONObject portfolioObj = new JSONObject(result);
                    JSONArray portfolioInfoArray = portfolioObj.getJSONArray("info_array");

                    Logger.printMessage("portfolioInfoArray-->", "" + portfolioInfoArray);

                    ProsDetailsPortfolioImageAdapter prosDetailsPortfolioImageAdapter = new ProsDetailsPortfolioImageAdapter(ProsProjectGalleryActivity.this, portfolioInfoArray, new onOptionSelected() {
                        @Override
                        public void onItemPassed(int position, String value) {
                            showImagePortFolioDialog(value);
                        }
                    }, x);
                    rcv_portfolio.setAdapter(prosDetailsPortfolioImageAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (myLoader != null && myLoader.isMyLoaderShowing())
                        myLoader.dismissLoader();
                }

            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();
            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(ProsProjectGalleryActivity.this, "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }

                    @Override
                    public void callBackCancel() {

                    }
                });
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }

    public interface onOptionSelected {
        void onItemPassed(int position, String value);
    }

    public void showImagePortFolioDialog(String url) {

        Logger.printMessage("url", url);
        final Dialog dialog = new Dialog(ProsProjectGalleryActivity.this, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialogbox_portfolio);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.findViewById(R.id.RelativeMainLL).getLayoutParams().width = (MethodsUtils.getScreenHeightAndWidth(ProsProjectGalleryActivity.this)[1]);
        dialog.findViewById(R.id.RelativeMainLL).getLayoutParams().height = MethodsUtils.getScreenHeightAndWidth(ProsProjectGalleryActivity.this)[0];


        dialog.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        ImageViewTouch mImage = (ImageViewTouch) dialog.findViewById(R.id.imageview_dialog);

        // set the default image display type
        mImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_WIDTH);
        Glide.with(ProsProjectGalleryActivity.this).load(url).into(mImage);


        mImage.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        Logger.printMessage("onSingleTapConfirmed", "onSingleTapConfirmed");
                    }
                }
        );


        mImage.setDoubleTapListener(
                new ImageViewTouch.OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        Logger.printMessage("onDoubleTap", "onDoubleTap");
                    }
                }
        );

        mImage.setOnDrawableChangedListener(
                new ImageViewTouchBase.OnDrawableChangeListener() {

                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        Logger.printMessage("onBitmapChanged", "onBitmapChanged: " + drawable);
                    }
                }
        );


        dialog.show();
    }

}
