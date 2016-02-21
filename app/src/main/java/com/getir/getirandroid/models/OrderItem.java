package com.getir.getirandroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/* Created by guray on 20/02/16. */
public class OrderItem {
    @SerializedName("_id")
    public String Id;

    public String readyAt;
    public String createdAt;
    public double totalPrice;
    public ArrayList<String> foodNames; // yemek item isimleri
}
