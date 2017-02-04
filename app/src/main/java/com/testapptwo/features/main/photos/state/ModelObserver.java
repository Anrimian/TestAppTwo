package com.testapptwo.features.main.photos.state;

/**
 * Created on 17.01.2017.
 */

public interface ModelObserver {
    void onProgress();
    void onError();
    void onWait();
    void onEnd();
    void updateList();
    void onDeleteError(String errorMessage);
    void goToCurrentListPosition();
}
