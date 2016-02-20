package com.getir.getirandroid.service;

import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.getir.getirandroid.utilities.ProgressDialogHelper;

import retrofit.Callback;
import retrofit.RetrofitError;

public abstract class SuccessCallback<T> implements Callback<T>
{
    @Override
    public void failure(RetrofitError retrofitError)
    {
        Crashlytics.log(Log.ASSERT, "xError in Callback!", retrofitError.getKind().toString() + " " + retrofitError.toString());
        ProgressDialogHelper.dismiss();
    }
}
