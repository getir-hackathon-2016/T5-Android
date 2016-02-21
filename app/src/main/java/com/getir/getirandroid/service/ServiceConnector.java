package com.getir.getirandroid.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class ServiceConnector
{
    public static final String BASE_URL = "https://enigmatic-chamber-57342.herokuapp.com";
    public static GetirAPI getirAPI;

    public static void init()
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(60, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                //.registerTypeAdapter(Date.class, new DateDeserializer())
                                     //.registerTypeAdapter(FinalDate.class, new FinalDateDeserializer())
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
        getirAPI = restAdapter.create(GetirAPI.class);
    }


}