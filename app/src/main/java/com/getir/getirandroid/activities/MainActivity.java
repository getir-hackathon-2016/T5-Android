package com.getir.getirandroid.activities;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.crashlytics.android.Crashlytics;
import com.getir.getirandroid.R;
import com.getir.getirandroid.extensions.LocationChangedEvent;
import com.getir.getirandroid.fragments.AddToCartFragment;
import com.getir.getirandroid.fragments.AllAddressFragment;
import com.getir.getirandroid.fragments.OrdersFragment;
import com.getir.getirandroid.fragments.RegisterFragment;
import com.getir.getirandroid.models.OrderItem;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.utilities.BusProvider;
import com.google.gson.Gson;
import com.yayandroid.locationmanager.LocationBaseActivity;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;


public class MainActivity extends LocationBaseActivity {
    public static LayoutInflater inflater;
    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_main);
        activity = this;
        if(UserSelf.getInstance().isUserLoggedIn){
            String target = getIntent().getStringExtra("target");
            replaceWithTarget(target);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new RegisterFragment()).commit();
        }
        LocationManager.setLogType(LogType.GENERAL);
        getLocation();
    }

    private void replaceWithTarget(String target){
        if(target.equals("AddCardFragment")){
            OrderItem item = new Gson().fromJson(getIntent().getStringExtra("orderItem"), OrderItem.class);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, AddToCartFragment.newInstance(item)).commit();
        }else if(target.equals("Register")){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new RegisterFragment()).commit();
        }else if(target.equals("alladdress")){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new AllAddressFragment()).commit();
        }else if(target.equals("orders")){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new OrdersFragment()).commit();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        BusProvider.getInstance().post(new LocationChangedEvent(location));
        Crashlytics.log(Log.ASSERT, "latitude:"+location.getLatitude()+" - - "+"longitude:"+location.getLongitude(),"");
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return new LocationConfiguration()
                .keepTracking(true)
                .askForGooglePlayServices(true)
                .setMinAccuracy(200.0f)
                .setWaitPeriod(ProviderType.GOOGLE_PLAY_SERVICES, 5 * 1000)
                .setWaitPeriod(ProviderType.GPS, 10 * 1000)
                .setWaitPeriod(ProviderType.NETWORK, 5 * 1000)
                .setGPSMessage("Would you mind to turn GPS on?")
                .setRationalMessage("Gimme the permission!");
    }

    @Override
    public void onLocationFailed(int failType) {
        switch (failType) {
            case FailType.PERMISSION_DENIED: {
                Crashlytics.log(Log.ASSERT, "PERMISSION_DENIED", "Couldn't get location, because user didn't give permission!");
                break;
            }
            case FailType.GP_SERVICES_NOT_AVAILABLE:
            case FailType.GP_SERVICES_CONNECTION_FAIL: {
                Crashlytics.log(Log.ASSERT, "GP_SERVICES FAIL", "Couldn't get location, because Google Play Services not available!");
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                Crashlytics.log(Log.ASSERT, "NETWORK_NOT_AVAILABLE", "Couldn't get location, because NETWORK_NOT_AVAILABLE!");
                break;
            }
            case FailType.TIMEOUT: {
                Crashlytics.log(Log.ASSERT, "TIMEOUT", "Couldn't get location, because timeout!");
                break;
            }
        }
    }
}
