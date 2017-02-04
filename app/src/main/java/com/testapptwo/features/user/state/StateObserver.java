package com.testapptwo.features.user.state;

/**
 * Created on 17.01.2017.
 */

public interface StateObserver {
    void onWait();
    void onProgress();
    void onError();
    void onComplete();
}
