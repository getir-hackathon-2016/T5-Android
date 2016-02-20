package com.getir.getirandroid.extensions;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.getir.getirandroid.R;

public class GetirProgressDialog extends ProgressDialog
{
    public static GetirProgressDialog builder(Context activity) {
        GetirProgressDialog dialog = new GetirProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    public GetirProgressDialog(Context activity)
    {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
