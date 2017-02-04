package com.testapptwo.features.main.photos.camera;

import android.database.Observable;

/**
 * Created on 17.01.2017.
 */

public class CameraModelObservable extends Observable<CameraModelObserver> {

    public void onImageSent() {
        for (CameraModelObserver observer : mObservers) {
            observer.onImageSent();
        }
    }

    public void onSentImageError() {
        for (CameraModelObserver observer : mObservers) {
            observer.onSentImageError();
        }
    }
}
