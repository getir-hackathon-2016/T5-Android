package com.getir.getirandroid.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.adapters.NearestMenuAdapter;
import com.getir.getirandroid.extensions.LocationChangedEvent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* Created by guray on 20/02/16.*/
public class MainFragment extends BaseFragment  implements OnMapReadyCallback{


    boolean isMapReady = false;
    GoogleMap googleMap;
    Location lastLocation = new Location("");;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, v);
        prepareView(v);
        setAdapter();
        return v;
    }

    private void setAdapter() {
        recyclerView.setAdapter(new NearestMenuAdapter(getChildFragmentManager()));
    }

  //  SupportMapFragment mapFragment;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private void prepareView(View v) {
  //      mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(MainActivity.activity);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

 /*   @OnClick(R.id.setAddressLL)
    public void setAddressClicked(){
        MainActivity.activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, SaveAddressFragment.newInstance(lastLocation))
                .commit();
    }*/


    @Override
    public void onMapReady(GoogleMap googleMap) {
       /* isMapReady = true;
        this.googleMap = googleMap;
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                lastLocation.setLongitude(cameraPosition.target.longitude);
                lastLocation.setLatitude(cameraPosition.target.latitude);
            }
        });*/
    }

    @Subscribe
    public void onLocationChanged(LocationChangedEvent event){
        if(isMapReady){
            googleMap.clear();
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(event.location.getLatitude(), event.location.getLongitude()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
    }
}
