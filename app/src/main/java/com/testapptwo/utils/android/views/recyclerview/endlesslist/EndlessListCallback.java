package com.testapptwo.utils.android.views.recyclerview.endlesslist;

/**
 * Created on 18.11.2016.
 */

public interface EndlessListCallback {
    boolean canLoad();

    void loadNextData();
}
