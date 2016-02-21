package com.getir.getirandroid.utilities;

import com.orhanobut.hawk.Hawk;

/**
 * Created by guray on 21/02/16.
 */
public class AppData {
    static AppData data;
    public String selectedAddressId;

    public static void init(){
        data = Hawk.get("data", new AppData());
    }

    public static void setSelectedAddres(String id){
        data.selectedAddressId = id;
        Hawk.put("data", data);
    }

    public static AppData getInstance(){
        return data;
    }
}
