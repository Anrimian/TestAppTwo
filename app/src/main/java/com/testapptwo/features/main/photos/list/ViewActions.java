package com.testapptwo.features.main.photos.list;

/**
 * Created on 31.01.2017.
 */

public interface ViewActions {
    void onRefresh();

    void tryLoadAgain();

    void onFabClick();

    boolean isListEmpty();
}
