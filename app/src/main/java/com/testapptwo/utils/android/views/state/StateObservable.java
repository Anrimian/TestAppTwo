package com.testapptwo.utils.android.views.state;

import android.database.Observable;

/**
 * Created on 17.01.2017.
 */

public class StateObservable extends Observable<StateObserver> {

    public void showState(State state) {
        for (StateObserver observer : mObservers) {
            state.showState(observer);
        }
    }

    public void updateList() {
        for (StateObserver observer : mObservers) {
            observer.updateList();
        }
    }
}
