package com.getir.getirandroid.service;

import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserResponse;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.utilities.Commons;
import com.getir.getirandroid.utilities.ProgressDialogHelper;

import java.util.HashMap;

import retrofit.client.Response;

/* Created by guray on 20/02/16.*/
public class AppServices {
    public interface UserCallback{
        void onUserReceived(UserSelf user);
    }

    public static void getUser(Integer userid, final UserCallback callback){
        HashMap<String,Object> params =  new HashMap<String, Object>();
        params.put("userid", userid);
        ProgressDialogHelper.showCircularProgressDialog(MainActivity.activity);
        ServiceConnector.getirAPI.getSelf(params, new SuccessCallback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                ProgressDialogHelper.dismiss();
                if (userResponse.success) {
                    callback.onUserReceived(userResponse.user);
                } else {
                    Commons.showDialog(MainActivity.activity, userResponse.msg);
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
                    callback.onUserReceived(userResponse.user);
                }else{
                    Commons.showDialog(MainActivity.activity, userResponse.msg);
                }
            }
        });
    }

    public static void login(String email, String password, final UserCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        ProgressDialogHelper.showCircularProgressDialog(MainActivity.activity);
        ServiceConnector.getirAPI.login(params, new SuccessCallback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                ProgressDialogHelper.dismiss();
                if(userResponse.success){
                    callback.onUserReceived(userResponse.user);
                }else{
                    Commons.showDialog(MainActivity.activity, userResponse.msg);
                }
            }
        });
    }
}
