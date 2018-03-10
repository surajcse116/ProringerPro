package com.android.llc.proringer.pro.proringerpro.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;


/**
 * Created by su on 31/10/17.
 */

public class MYAlert {
    Context mContext;
    Dialog dialog;

    public interface OnlyMessage {
        public void OnOk(boolean res);
    }


    public MYAlert(Context mContext) {
        this.mContext = mContext;
        dialog = new Dialog(mContext);
    }

    public void AlertOkCancel(String title, String message, final OnlyMessage onlyMessage) {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View LayView = inflater.inflate(R.layout.alert_dialog_ok_cancel, null);

        ProSemiBoldTextView tv_title = (ProSemiBoldTextView) LayView.findViewById(R.id.tv_title);
        tv_title.setText(title);

        ProRegularTextView tv_message = (ProRegularTextView) LayView.findViewById(R.id.tv_message);
        tv_message.setText(message);


        ProSemiBoldTextView tv_abort = (ProSemiBoldTextView) LayView.findViewById(R.id.tv_abort);
        ProSemiBoldTextView tv_retry = (ProSemiBoldTextView) LayView.findViewById(R.id.tv_retry);

        tv_abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        alertbuilder.setView(LayView);
        dialog = alertbuilder.create();
        dialog.show();
    }

}
