package com.getir.getirandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.adapters.AddressAdapter;
import com.getir.getirandroid.models.UserSelf;

/**
 * Created by guray on 20/02/16.
 */
public class AllAddressFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_addresses, container, false);
        prepareView(v);
        setAdapter();
        return v;
    }

    private void setAdapter() {
        recyclerView.setAdapter(new AddressAdapter(MainActivity.inflater, UserSelf.getInstance().user.address));
    }

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private void prepareView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(MainActivity.activity);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
