package com.testapptwo.features.main.map;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.testapptwo.R;

/**
 * Created on 19.01.2017.
 */

public class MapView {

    private SupportMapFragment mapFragment;

    public MapView(View view, FragmentManager fm) {
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            fm.beginTransaction().replace(R.id.map_container, mapFragment).commit();
        }
    }

    public void bindView(OnMapReadyCallback onMapReadyCallback) {
        mapFragment.getMapAsync(onMapReadyCallback);
    }
}
