package com.getir.getirandroid.adapters;

import android.location.Location;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;

public class NearestMenuAdapter extends RecyclerView.Adapter<NearestMenuAdapter.ViewHolder> {




    protected static FragmentManager fm;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView); //row ait bilgiler buraya girilecek

        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        protected SupportMapFragment mapFragment;
        public boolean isMapReady = false;
        GoogleMap googleMap;
        Location lastLocation = new Location("");;
        public HeaderViewHolder(View itemView) {
            super(itemView); //row ait bilgiler buraya girilecek
            mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
             mapFragment.getMapAsync(new OnMapReadyCallback() {
                 @Override
                 public void onMapReady(GoogleMap gm) {
                     isMapReady = true;
                     googleMap = gm;
                     googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                         @Override
                         public void onCameraChange(CameraPosition cameraPosition) {
                             lastLocation.setLongitude(cameraPosition.target.longitude);
                             lastLocation.setLatitude(cameraPosition.target.latitude);
                         }
                     });
                 }
             });

        }
    }

    public NearestMenuAdapter(FragmentManager fm){
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        if(viewType==0) { // Header
            View view = MainActivity.inflater.inflate(R.layout.row_main_map, parent, false);
            vh = new ViewHolder(view);
        }else if(viewType==1){ // normal
            View view = MainActivity.inflater.inflate(R.layout.row_menu, parent, false);
            vh = new ViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(holder.getItemViewType()==0) { // header

        }else if(holder.getItemViewType()==1){ // normal

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0; // header
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {

        return 8;
    }


}