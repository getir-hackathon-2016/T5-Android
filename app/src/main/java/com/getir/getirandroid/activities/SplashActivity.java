package com.getir.getirandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.getir.getirandroid.R;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.service.AppServices;

/*Created by guray on 20/02/16.*/
public class SplashActivity extends FragmentActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(UserSelf.getInstance()!=null && UserSelf.getInstance().user!=null && UserSelf.getInstance().user.email!=null && UserSelf.getInstance().user.password!=null){
            UserSelf.getInstance().isUserLoggedIn = true;
            AppServices.getUser(UserSelf.getInstance().id, new AppServices.UserCallback() {
                @Override
                public void onUserReceived(UserSelf user) {
                    UserSelf.setInstance(user);
                    UserSelf.getInstance().isUserLoggedIn = true;
                    handler.postDelayed(mapRunnable, 1000);
                }
            });
        }else{
            handler.postDelayed(registeRunnable, 1000);
        }
    }

    Runnable mapRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MapActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    };

    Runnable registeRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("target","Register");
            startActivity(intent);
            SplashActivity.this.finish();
        }
    };
}
