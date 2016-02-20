package com.getir.getirandroid.activities;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.crashlytics.android.Crashlytics;
import com.getir.getirandroid.R;
import com.getir.getirandroid.extensions.LocationChangedEvent;
import com.getir.getirandroid.fragments.LoginFragment;
import com.getir.getirandroid.fragments.MainFragment;
import com.getir.getirandroid.fragments.RegisterFragment;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.utilities.BusProvider;
import com.yayandroid.locationmanager.LocationBaseActivity;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;


public class MainActivity extends LocationBaseActivity {
    //AIzaSyDa89eUVg9lkrrr_MJovEvj7f-12JZYZx4
    public static LayoutInflater inflater;
    public static MainActivity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_main);
        activity = this;
        prepareView();
        if(UserSelf.getInstance().isUserLoggedIn){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new MainFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new RegisterFragment()).commit();
        }

        LocationManager.setLogType(LogType.GENERAL);
        getLocation();

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
        //dismissProgress();

        switch (failType) {
            case FailType.PERMISSION_DENIED: {
                //locationText.setText("Couldn't get location, because user didn't give permission!");
                break;
            }
            case FailType.GP_SERVICES_NOT_AVAILABLE:
            case FailType.GP_SERVICES_CONNECTION_FAIL: {
               // locationText.setText("Couldn't get location, because Google Play Services not available!");
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
               // locationText.setText("Couldn't get location, because network is not accessible!");
                break;
            }
            case FailType.TIMEOUT: {
               // locationText.setText("Couldn't get location, and timeout!");
                break;
            }
        }
    }

    // MaterialMenuView materialMenuView;
    private void prepareView() {
     //   materialMenuView = (MaterialMenuView) findViewById(R.id.menuButton);
       // materialMenuView.setState(MaterialMenuDrawable.IconState.BURGER);
    }

}
