package com.testapptwo.features.main.photos.comments.addcomment;

import android.database.Observable;

/**
 * Created on 17.01.2017.
 */

public class ModelObservable extends Observable<ModelObserver> {

    public void onComplete() {
        for (ModelObserver observer : mObservers) {
            observer.onComplete();
        }
    }

    public void onError() {
        for (ModelObserver observer : mObservers) {
            observer.onError();
        }
    }
}
