package com.getir.getirandroid.utilities;

import android.app.ProgressDialog;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getir.getirandroid.extensions.GetirProgressDialog;

public class ProgressDialogHelper
{
    public static ProgressDialog progressDialog;
    public static MaterialDialog progressDialogWithTitle;



    public static void showCircularProgressDialog(Context activity)
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            return;
        }
        progressDialog = GetirProgressDialog.builder(activity);
        progressDialog.show();
    }

    public static void dismiss()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (progressDialogWithTitle != null && progressDialogWithTitle.isShowing())
        {
            progressDialogWithTitle.dismiss();
            progressDialogWithTitle = null;
        }
    }
}
