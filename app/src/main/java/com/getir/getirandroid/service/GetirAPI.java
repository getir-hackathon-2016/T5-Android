package com.getir.getirandroid.service;

import com.getir.getirandroid.models.BaseModel;
import com.getir.getirandroid.models.CloseSellers;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserResponse;
import java.util.HashMap;

import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/* Created by guray on 20/02/16.*/
public interface GetirAPI {
    @POST("/getUser")
    public void getSelf(@Body HashMap<String, Object> parameters, SuccessCallback<UserResponse> callback);

    @POST("/register")
    public void register(@Body HashMap<String, Object> parameters, SuccessCallback<UserResponse> callback);

    @POST("/login")
    public void login(@Body HashMap<String,Object> parameters, SuccessCallback<UserResponse> callback);



    /*::::  param  ::::
            userid
            description
            latitude
            longitude
     */
    @POST("/addAddress")
    public void addAddress(@Body HashMap<String,Object> parameters, SuccessCallback<BaseModel> callback);


    /*::::  param  ::::
        latitude
        longitude
 */
    @POST("/getCloseSellers")
    public void getCloseSellers(@Body HashMap<String,Object> parameters, SuccessCallback<CloseSellers> callback);


    //addressid, userid, menuid
    @POST("/addOrder")
    public void addOrder(@Body HashMap<String,Object> parameters, SuccessCallback<BaseModel> callback);

}
