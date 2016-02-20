package com.getir.getirandroid.models;

import com.google.gson.annotations.SerializedName;

/* Created by guray on 20/02/16.*/
public class UserResponse extends BaseModel{
    @SerializedName("data")
    public UserSelf user;
}
