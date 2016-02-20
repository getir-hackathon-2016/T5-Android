package com.getir.getirandroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/* Created by guray on 20/02/16.*/
public class User {

    @SerializedName("_id")
    public String id;

    public String name;
    public String surname;
    public String email;
    public String password;
   // public ArrayList<Address> address;
}
