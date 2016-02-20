package com.getir.getirandroid.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getir.getirandroid.R;
import com.getir.getirandroid.models.UserSelf;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* Created by guray on 20/02/16.*/
public class SaveAddressFragment extends BaseFragment  implements OnMapReadyCallback {

    Location location;


    SupportMapFragment mapFragment;
    boolean isMapReady = false;
    GoogleMap googleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_save_address, container, false);
        ButterKnife.inject(this, v);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    @OnClick(R.id.saveTV)
    public void onSaveAdressClicked(){

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        isMapReady = true;
        this.googleMap = googleMap;
        googleMap.clear();

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                location.setLongitude(cameraPosition.target.longitude);
                location.setLatitude(cameraPosition.target.latitude);
            }
        });

        //  googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public static SaveAddressFragment newInstance(Location location) {
        SaveAddressFragment userDetailFragment = new SaveAddressFragment();
        Bundle args = new Bundle();
        args.putString("location", new Gson().toJson(location, Location.class));
        userDetailFragment.setArguments(args);
        return userDetailFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location = new Gson().fromJson(getArguments().getString("location"), Location.class);
    }
}
