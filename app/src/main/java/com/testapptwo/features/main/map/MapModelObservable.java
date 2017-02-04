package com.testapptwo.features.main.map;

import android.database.Observable;

import com.testapptwo.api.data.ImageInfo;

import java.util.List;

/**
 * Created on 17.01.2017.
 */

public class MapModelObservable extends Observable<MapModelObserver> {

    public void addMarkers(List<ImageInfo> imageInfoList) {
        for (MapModelObserver observer : mObservers) {
            observer.addMarkers(imageInfoList);
        }
    }
}
