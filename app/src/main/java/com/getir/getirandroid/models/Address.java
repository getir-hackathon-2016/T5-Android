package com.getir.getirandroid.models;

import com.google.gson.annotations.SerializedName;

/* Created by guray on 20/02/16.*/
public class Address {
    public String description;
    public Location location;
    public String title;

    @SerializedName("_id")
    public String id;
}
