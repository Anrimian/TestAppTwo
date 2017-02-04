package com.testapptwo.utils.android.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.testapptwo.R;

/**
 * Created on 01.02.2017.
 */

public class SimpleLocationManager {

    private static final long LOCATION_REFRESH_TIME = 10000;
    private static final long LOCATION_REFRESH_DISTANCE = 10;

    public static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private SimpleLocationListener simpleLocationListener;

    private Activity activity;

    public SimpleLocationManager(Activity activity, SimpleLocationListener simpleLocationListener) {
        this.activity = activity;
        this.simpleLocationListener = simpleLocationListener;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
    }

    public void bind() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, ACCESS_FINE_LOCATION_REQUEST_CODE);
        } else {
            Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location lastLocation = getBestLocation(locationGPS, locationNet);
            if (lastLocation != null) {
                simpleLocationListener.onShowLocation(lastLocation);
            } else {
                simpleLocationListener.onLocationError(R.string.error_getting_location);
            }
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener);
            } catch (Exception e) {
                simpleLocationListener.onLocationError(R.string.gps_accessing_error);
                e.printStackTrace();
            }
        }
    }

    @Nullable
    private Location getBestLocation(Location locationGPS, Location locationNet) {
        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }
        long NetLocationTime = 0;
        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }
        if (0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            simpleLocationListener.onShowLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
