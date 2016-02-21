package com.getir.getirandroid;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.service.ServiceConnector;
import com.getir.getirandroid.utilities.AppData;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/* Created by guray on 20/02/16.*/
public class GetirApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Hawk.init(this).setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION).setStorage(HawkBuilder.newSharedPrefStorage(this)).build();
        ServiceConnector.init();
        UserSelf.init();
        AppData.init();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/GloberRegular.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

    }
}
