package com.getir.getirandroid.models;

import java.util.ArrayList;

/**
 * Created by guray on 20/02/16.
 */
public class OrderItem {
    public Integer Id;
    public String readyAt;
    public String createdAt;
    public double totalPrice;
    public ArrayList<String> box; // yemek item isimleri
}
