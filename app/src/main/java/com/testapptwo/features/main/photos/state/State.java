package com.testapptwo.features.main.photos.state;

/**
 * Created on 17.01.2017.
 */

public enum State {
    PROGRESS {
        @Override
        public void showState(ModelObserver observer) {
            observer.onProgress();
        }
    },
    ERROR {
        @Override
        public void showState(ModelObserver observer) {
            observer.onError();
        }
    },
    WAIT {
        @Override
        public void showState(ModelObserver observer) {
            observer.onWait();
        }
    },
    END {
        @Override
        public void showState(ModelObserver observer) {
            observer.onEnd();
        }
    };

    public void showState(ModelObserver observer) {

    }
}
