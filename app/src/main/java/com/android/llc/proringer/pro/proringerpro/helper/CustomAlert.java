package com.android.llc.proringer.pro.proringerpro.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


/**
 * Created by su on 4/10/17.
 */

public class CustomAlert {

    public void getEventFromNormalAlert(Context context, String title, String message, String positiveTitle, String negativeTitle, final MyCustomAlertListener ml) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ml.callBackOk();
                    }
                })
                .setNegativeButton(negativeTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ml.callBackCancel();
                    }
                })
                .create()
                .show();
    }


    public void getOkEventFromNormalAlert(Context context, String title, String message, final MyCustomAlertListener ml) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ml.callBackOk();
                    }
                })
                .create()
                .show();
    }


    public interface MyCustomAlertListener {
        // you can define any parameter as per your requirement
        public void callBackOk();

        public void callBackCancel();
    }


}
