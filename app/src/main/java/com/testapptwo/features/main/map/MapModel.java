package com.testapptwo.features.main.map;

import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.database.rx.ImageInfoDaoRxWrapper;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created on 03.02.2017.
 */

public class MapModel {

    private List<ImageInfo> imageInfoList = new ArrayList<>();

    private MapModelObservable observable = new MapModelObservable();

    public MapModel() {
        loadImageInfoList();
    }

    public void loadImageInfoList() {
        ImageInfoDaoRxWrapper.getImageInfoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageInfoList -> {
                    this.imageInfoList = imageInfoList;
                    observable.addMarkers(imageInfoList);
                });
    }

    public void registerObserver(MapModelObserver observer) {
        observer.addMarkers(imageInfoList);
        observable.registerObserver(observer);
    }

    public void unregisterObserver(MapModelObserver observer) {
        observable.unregisterObserver(observer);
    }
}
