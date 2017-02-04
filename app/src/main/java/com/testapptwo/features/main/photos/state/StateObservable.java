package com.testapptwo.features.main.photos.state;

import android.database.Observable;

/**
 * Created on 17.01.2017.
 */

public class StateObservable extends Observable<ModelObserver> {

    public void showState(State state) {
        for (ModelObserver observer : mObservers) {
            state.showState(observer);
        }
    }

    public void updateList() {
        for (ModelObserver observer : mObservers) {
            observer.updateList();
        }
    }

    public void onDeleteError(String errorMessage) {
        for (ModelObserver observer : mObservers) {
            observer.onDeleteError(errorMessage);
        }
    }

    public void goToCurrentListPosition() {
        for (ModelObserver observer : mObservers) {
            observer.goToCurrentListPosition();
        }
    }
}
