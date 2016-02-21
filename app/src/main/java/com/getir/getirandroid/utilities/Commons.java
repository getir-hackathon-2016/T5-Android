package com.getir.getirandroid.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.afollestad.materialdialogs.MaterialDialog;

/* Created by guray on 20/02/16.*/
public class Commons {

    public static void showDialog(Context context,  String content){
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText("ok")
                .show();
    }
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
