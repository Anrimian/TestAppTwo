package com.testapptwo.features;

import android.app.Application;

import com.testapptwo.database.HelperFactory;

/**
 * Created on 30.01.2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
        Preferences.init(this);
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
