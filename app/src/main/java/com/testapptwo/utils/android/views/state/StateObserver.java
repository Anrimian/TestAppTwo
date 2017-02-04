package com.testapptwo.utils.android.views.state;

/**
 * Created on 17.01.2017.
 */

public interface StateObserver {
    void onProgress();
    void onError();
    void onComplete();
    void updateList();
}
