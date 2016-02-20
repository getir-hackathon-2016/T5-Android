package com.getir.getirandroid.utilities;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by guray on 20/02/16.
 */
public class Commons {

    public static void showDialog(Context context,  String content){
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText("ok")
                .show();
    }

}
