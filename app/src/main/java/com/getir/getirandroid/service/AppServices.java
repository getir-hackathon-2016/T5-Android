package com.getir.getirandroid.service;

import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.models.BaseModel;
import com.getir.getirandroid.models.CloseSellers;
import com.getir.getirandroid.models.Location;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserResponse;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.utilities.Commons;
import com.getir.getirandroid.utilities.ProgressDialogHelper;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;

import retrofit.client.Response;

/* Created by guray on 20/02/16.*/
public class AppServices {
    public interface UserCallback{
        void onUserReceived(UserSelf user);
    }

    public interface DefaultCallback{
        void onResponse();
    }

    public interface CloseSellersCallback{
        void onReceived(CloseSellers closeSellers);
    }

    public static void addOrder(String addressid, String userid, String menuid, final DefaultCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressid", addressid);
        params.put("userid", userid);
        params.put("menuid", menuid);
        ServiceConnector.getirAPI.addOrder(params, new SuccessCallback<BaseModel>() {
            @Override
            public void success(BaseModel baseModel, Response response) {
                ProgressDialogHelper.dismiss();
                if(baseModel.success){
                    UserSelf.refresh();
                    callback.onResponse();
                }else{
                    Commons.showDialog(MainActivity.activity, baseModel.msg);
                }
            }
        });
    }

    public static void getUser(String userid, final UserCallback callback){
        HashMap<String,Object> params =  new HashMap<String, Object>();
        params.put("userid", userid);
        ServiceConnector.getirAPI.getSelf(params, new SuccessCallback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                ProgressDialogHelper.dismiss();
                if (userResponse.success) {
                    callback.onUserReceived(userResponse.user);
                } else {
                }
            }
        });
    }

    public static void register(final HashMap<String,Object> params, final UserCallback callback){
        ProgressDialogHelper.showCircularProgressDialog(MainActivity.activity);
        ServiceConnector.getirAPI.register(params, new SuccessCallback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                ProgressDialogHelper.dismiss();
                if (userResponse.success) {
                    UserSelf.setInstance(userResponse.user);
                    callback.onUserReceived(userResponse.user);
                } else {
                    // Commons.showDialog(MainActivity.activity, userResponse.msg);
                }
            }
        });
    }

    public static void login(String email, String password, final UserCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        //ProgressDialogHelper.showCircularProgressDialog(MainActivity.activity);
        ServiceConnector.getirAPI.login(params, new SuccessCallback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                ProgressDialogHelper.dismiss();
                if (userResponse.success) {
                    UserSelf.setInstance(userResponse.user);
                    UserSelf.getInstance().isUserLoggedIn = true;
                    if (callback != null)
                        callback.onUserReceived(userResponse.user);
                } else {
                    Hawk.clear();
                    //Commons.showDialog(MainActivity.activity, userResponse.msg);
                }
            }
        });
    }

    public static void getCloseSellers(Location location , final CloseSellersCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("latitude", location.latitude);
        params.put("longitude", location.longitude);
        ServiceConnector.getirAPI.getCloseSellers(params, new SuccessCallback<CloseSellers>() {
            @Override
            public void success(CloseSellers closeSellers, Response response) {
                callback.onReceived(closeSellers);
            }
        });
    }

    public static void addAddress(String userid, String title, String description, Location location, final  DefaultCallback callback ){
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", userid);
        params.put("atitle", title);
        params.put("adescription", description);
        params.put("latitude", location.latitude);
        params.put("longitude", location.longitude);
        ServiceConnector.getirAPI.addAddress(params, new SuccessCallback<BaseModel>() {
            @Override
            public void success(BaseModel baseModel, Response response) {
                if(baseModel.success){
                    UserSelf.refresh();
                    callback.onResponse();
                }
            }
        });
    }
}
