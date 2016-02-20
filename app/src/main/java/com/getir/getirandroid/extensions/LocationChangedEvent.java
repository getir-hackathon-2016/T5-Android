package com.getir.getirandroid.extensions;

import android.location.Location;

/**
 * Created by guray on 20/02/16.
 */
public class LocationChangedEvent {
    public Location location;
    public LocationChangedEvent(Location location){
        this.location = location;
    }
}
