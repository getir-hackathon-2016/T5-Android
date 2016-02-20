package com.getir.getirandroid.fragments;

import android.support.v4.app.Fragment;

import com.getir.getirandroid.utilities.BusProvider;

/* Created by guray on 20/02/16.*/
public class BaseFragment extends Fragment {
    @Override public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
