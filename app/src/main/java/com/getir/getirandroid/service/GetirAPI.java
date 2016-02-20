package com.getir.getirandroid.service;

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
    @POST("/users")
    public void getSelf(@Body HashMap<String, Object> parameters, SuccessCallback<UserResponse> callback);

    @POST("/register")
    public void register(@Body HashMap<String, Object> parameters, SuccessCallback<UserResponse> callback);

    @POST("/login")
    public void login(@Body HashMap<String,Object> parameters, SuccessCallback<UserResponse> callback);

}
