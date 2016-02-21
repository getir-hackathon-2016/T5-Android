package com.getir.getirandroid.activities;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getir.getirandroid.R;
import com.getir.getirandroid.adapters.NearestMenuAdapter;
import com.getir.getirandroid.extensions.HidingScrollListener;
import com.getir.getirandroid.extensions.LocationChangedEvent;
import com.getir.getirandroid.fragments.SetAddressDialog;
import com.getir.getirandroid.models.Address;
import com.getir.getirandroid.models.CloseSellers;
import com.getir.getirandroid.models.OrderItem;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.service.AppServices;
import com.getir.getirandroid.utilities.BusProvider;
import com.getir.getirandroid.utilities.Commons;
import com.getir.getirandroid.utilities.ProgressDialogHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.yayandroid.locationmanager.LocationBaseActivity;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* Created by guray on 20/02/16.*/
public class MapActivity extends LocationBaseActivity implements OnMapReadyCallback {
    boolean isMapReady = false;
    GoogleMap googleMap;
    public Location lastLocation = new Location("");;

    public static LayoutInflater inflater;
    public static MapActivity activity;




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
    public void onLocationFailed(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        onLocationChangedEvent(new LocationChangedEvent(location));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);
        activity = this;
        prepareView();
        LocationManager.setLogType(LogType.GENERAL);
        getLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onLocationChangedEvent(LocationChangedEvent event){
        if(isMapReady){
            googleMap.clear();
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(event.location.getLatitude(), event.location.getLongitude()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
            com.getir.getirandroid.models.Location loc = new com.getir.getirandroid.models.Location();
            loc.latitude = event.location.getLatitude();
            loc.longitude = event.location.getLongitude();
            AppServices.getCloseSellers(loc, new AppServices.CloseSellersCallback() {
                @Override
                public void onReceived(CloseSellers closeSellers) {
                    NearestMenuAdapter adapter = new NearestMenuAdapter(inflater, closeSellers);
                    adapter.setOnItemClickListener(new NearestMenuAdapter.AdapterItemClickListener() {
                        @Override
                        public void onClicked(UserSelf neigbour) {
                            Intent intent = new Intent(MapActivity.activity, MainActivity.class);
                            intent.putExtra("target", "AddCardFragment");
                            intent.putExtra("orderItem", new Gson().toJson(neigbour.activeMenu, OrderItem.class).toString());
                            MapActivity.activity.startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    noFoodContainerLL.setVisibility(closeSellers.data.size()==0 ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    SupportMapFragment mapFragment;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LinearLayout mapContainerLL;
    TextView addressTV;
    LinearLayout setAddressLL;
    LinearLayout noFoodContainerLL;
    LinearLayout allAddressLL;
    private void prepareView() {
        allAddressLL = (LinearLayout) findViewById(R.id.allAddressLL);
        noFoodContainerLL = (LinearLayout) findViewById(R.id.noFoodContainerLL);
        setAddressLL = (LinearLayout) findViewById(R.id.setAddressLL);
        addressTV = (TextView) findViewById(R.id.addressTV);
        mapContainerLL = (LinearLayout) findViewById(R.id.mapContainerLL);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                mapContainerLL.animate().translationY(-Commons.convertDpToPixel(250, MapActivity.this));
            }

            @Override
            public void onShow() {
                mapContainerLL.animate().translationY(0);
            }
        });
    }

    @OnClick(R.id.ordersIV)
    public void onOrdersClicked(){
        Intent intent = new Intent(MapActivity.this, MainActivity.class);
        intent.putExtra("target","orders");
        startActivity(intent);
    }


    @OnClick(R.id.allAddressLL)
    public void onAllAddressesClicked(){
        Intent intent = new Intent(MapActivity.this, MainActivity.class);
        intent.putExtra("target","alladdress");
        startActivity(intent);
    }

    @OnClick(R.id.setAddressLL)
    public void setAddressClicked(){
        SetAddressDialog setAddressDialog = new SetAddressDialog();
        setAddressDialog.setOnSaveClickListener(new SetAddressDialog.ClickListener() {
            @Override
            public void onClicked(Dialog dialog, String title, String address) {
                ProgressDialogHelper.showCircularProgressDialog(MapActivity.this);
                com.getir.getirandroid.models.Location loc = new com.getir.getirandroid.models.Location();
                loc.longitude = lastLocation.getLongitude();
                loc.latitude = lastLocation.getLatitude();
                AppServices.addAddress(UserSelf.getInstance().id, title,  address, loc, new AppServices.DefaultCallback() {
                    @Override
                    public void onResponse() {
                        ProgressDialogHelper.dismiss();
                        //setAddressLL.setVisibility(View.INVISIBLE);
                        addressTV.setText("Yeni Adres Ekle");
                        allAddressLL.setVisibility(View.VISIBLE);
                        UserSelf.refresh();
                        Commons.showDialog(MapActivity.this, "Adresiniz başarıyla kaydedildi!");

                    }
                });
                dialog.dismiss();
            }
        });
        setAddressDialog.show(getSupportFragmentManager(),"");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        isMapReady = true;
        this.googleMap = googleMap;

        if(UserSelf.getInstance().user.address!=null && UserSelf.getInstance().user.address.size()>0){
            Address currentAddress = UserSelf.getInstance().user.address.get(UserSelf.getInstance().user.address.size() - 1);
            addressTV.setText(currentAddress.description);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(currentAddress.location.latitude, currentAddress.location.longitude));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
            addressTV.setText("Yeni Adres Ekle");
            allAddressLL.setVisibility(View.VISIBLE);

        }else{
            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    lastLocation.setLongitude(cameraPosition.target.longitude);
                    lastLocation.setLatitude(cameraPosition.target.latitude);
                }
            });
        }
    }

}
