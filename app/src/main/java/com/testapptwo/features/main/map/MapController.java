package com.testapptwo.features.main.map;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.testapptwo.api.data.ImageInfo;

import java.util.List;

/**
 * Created on 19.01.2017.
 */

public class MapController implements OnMapReadyCallback, MapModelObserver {

    private MapView mapView;
    private MapModel mapModel;

    private GoogleMap googleMap;
    private Activity activity;

    public MapController(MapView mapView, MapModel mapModel, Activity activity) {
        this.mapView = mapView;
        this.mapModel = mapModel;
        this.activity = activity;
    }

    public void bind() {
        mapView.bindView(this);
    }

    public void unbind() {
        mapModel.unregisterObserver(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        mapModel.registerObserver(this);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 8.0f));
    }

    @Override
    public void addMarkers(List<ImageInfo> imageInfoList) {
        for (ImageInfo imageInfo : imageInfoList) {
            double lng = imageInfo.getLatitude();
            double lat = imageInfo.getLongitude();
            LatLng latLng = new LatLng(lng, lat);
            googleMap.addMarker(new MarkerOptions().position(latLng));
        }
    }
}
