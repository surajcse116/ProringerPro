package com.android.llc.proringer.pro.proringerpro.viewsmod.edittext;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.android.llc.proringer.pro.proringerpro.viewsmod.FontCache;


/**
 * Created by Bodhidipta on 13/06/16.
 */
public class ProRegularEditText extends AppCompatEditText {

    public ProRegularEditText(Context context) {
        super(context);
        init(context);
    }

    public ProRegularEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ProRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        super.setTypeface(FontCache.get("OpenSans-Regular.ttf", context));
    }
}
