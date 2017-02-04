package com.testapptwo.utils.android.views.state;

/**
 * Created on 17.01.2017.
 */

public enum State {
    PROGRESS {
        @Override
        public void showState(StateObserver observer) {
            observer.onProgress();
        }
    },
    ERROR {
        @Override
        public void showState(StateObserver observer) {
            observer.onError();
        }
    },
    COMPLETE {
        @Override
        public void showState(StateObserver observer) {
            observer.onComplete();
        }
    };

    public void showState(StateObserver observer) {

    }
}
