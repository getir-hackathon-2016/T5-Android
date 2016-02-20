package com.getir.getirandroid.models;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

/* Created by guray on 20/02/16.*/
public class UserSelf {
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

    public static void Update(){
        Hawk.put("user", current);
    }

    public static void init(){
        current = Hawk.get("user", new UserSelf());
    }


}
