package com.testapptwo.features.user.state;

/**
 * Created on 17.01.2017.
 */

public enum State {
    WAIT {
        @Override
        public void showState(StateObserver observer) {
            observer.onWait();
        }
    },
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
