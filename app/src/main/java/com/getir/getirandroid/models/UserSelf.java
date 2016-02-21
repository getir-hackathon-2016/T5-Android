package com.getir.getirandroid.models;

import com.getir.getirandroid.service.AppServices;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

/* Created by guray on 20/02/16.*/
public class UserSelf {
    @SerializedName("_id")
    public String id;

    public User user;
    public ArrayList<OrderItem> basket;
    public ArrayList<OrderItem> orders;
    public OrderItem activeMenu;
    public ArrayList<OrderItem> allMenu;

    public boolean isUserLoggedIn = false;

    static UserSelf current;

    public static UserSelf getInstance(){
        return current;
    }

    public static void setInstance(UserSelf userSelf){
        current = userSelf;
        Hawk.put("user", current);
    }

    public static void refresh(){
        AppServices.getUser(current.id, new AppServices.UserCallback() {
            @Override
            public void onUserReceived(UserSelf user) {
                current.isUserLoggedIn = true;
                user.isUserLoggedIn = true;
                setInstance(user);
            }
        });
    }

    public static void Update(){
        Hawk.put("user", current);
    }

    public static void init(){
        current = Hawk.get("user", new UserSelf());
    }

}
