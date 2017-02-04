package com.testapptwo.features.main.photos.list;

import android.content.SharedPreferences;
import android.util.Log;

import com.testapptwo.api.ApiWrapper;
import com.testapptwo.api.ErrorMessages;
import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.api.response.ResponseInfo;
import com.testapptwo.database.rx.CommentsDaoRxWrapper;
import com.testapptwo.database.rx.ImageInfoDaoRxWrapper;
import com.testapptwo.features.AppExecutors;
import com.testapptwo.features.Preferences;
import com.testapptwo.features.main.photos.state.ModelObserver;
import com.testapptwo.features.main.photos.state.State;
import com.testapptwo.features.main.photos.state.StateObservable;
import com.testapptwo.features.user.UserModel;
import com.testapptwo.utils.android.views.recyclerview.ListPosition;
import com.testapptwo.utils.android.views.recyclerview.endlesslist.EndlessListUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 31.01.2017.
 */

public class PhotosModel {

    private static final int PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private ListPosition currentPosition;

    private State state = State.WAIT;
    private StateObservable stateObservable = new StateObservable();

    private String errorMessage;
    private boolean refreshing;

    private final List<ImageInfo> imageInfoList = new ArrayList<>();

    public PhotosModel() {
        init();
    }

    private void init() {
        loadListValues();
        showState(State.PROGRESS);
        ImageInfoDaoRxWrapper.getImageInfoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageInfoList -> {
                    if (imageInfoList.isEmpty()) {
                        startLoading(false);
                    } else {
                        this.imageInfoList.addAll(imageInfoList);
                        stateObservable.updateList();
                        stateObservable.goToCurrentListPosition();
                        showState(State.WAIT);
                    }
                });
    }

    public void startLoading(boolean refresh) {
        showState(State.PROGRESS);
        int page = EndlessListUtils.getNextPage(imageInfoList, PAGE_SIZE);
        if (refresh) {
            page = 0;
            refreshing = true;
        }
        startLoading(page, refresh);
    }

    private void startLoading(int page, boolean refresh) {
        Log.d(getClass().getSimpleName(), "load page: " + page);
        ApiWrapper.getApi()
                .getImage(page, UserModel.getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseInfo::getData)
                .subscribe(imageResponseList -> {
                    Log.d(getClass().getSimpleName(), "imageResponseList: " + imageResponseList);
                    Log.d(getClass().getSimpleName(), "size: " + imageResponseList.size());
                    setListData(imageResponseList, refresh);
                    stateObservable.updateList();
                    refreshing = false;
                    if (imageResponseList.isEmpty()) {
                        showState(State.END);
                    } else {
                        if (page == DEFAULT_PAGE_NUMBER) {
                            startLoading(false);
                        } else {
                            showState(State.WAIT);
                        }
                    }
                }, throwable -> {
                    errorMessage = ErrorMessages.selectErrorMessage(throwable);
                    showState(State.ERROR);
                    refreshing = false;
                });
    }

    private void setListData(List<ImageInfo> imageResponseList, boolean refresh) {
        if (refresh) {
            deleteListData();
        }
        ImageInfoDaoRxWrapper.addImageInfo(imageResponseList);
        for (ImageInfo newImageInfo : imageResponseList) {
            if (!imageInfoList.contains(newImageInfo)) {
                imageInfoList.add(newImageInfo);
            }
        }
        //imageInfoList.addAll(imageResponseList);
    }

    public void deleteListData() {
        ImageInfoDaoRxWrapper.deleteAll();
        imageInfoList.clear();
    }

    public void deleteImage(ImageInfo imageInfo) {
        ApiWrapper.getApi()
                .deleteImage(imageInfo.getId(), UserModel.getToken())
                .subscribeOn(AppExecutors.NETWORK_SCHEDULER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deletedImageInfo -> {
                    Log.d(getClass().getSimpleName(), "deleted: " + deletedImageInfo);
                    imageInfoList.remove(imageInfo);
                    stateObservable.updateList();
                    showState(state);
                    ImageInfoDaoRxWrapper.deleteImageInfo(imageInfo);
                    CommentsDaoRxWrapper.deleteComments(imageInfo.getId());
                }, throwable -> {
                    String errorMessage = "delete image failed: " + ErrorMessages.selectErrorMessage(throwable);
                    Log.d(getClass().getSimpleName(), errorMessage);
                    stateObservable.onDeleteError(errorMessage);
                });
    }

    public void registerStateObserver(ModelObserver stateObserver) {
        state.showState(stateObserver);
        stateObservable.registerObserver(stateObserver);
    }

    public void unregisterStateObserver(ModelObserver stateObserver) {
        stateObservable.unregisterObserver(stateObserver);
    }

    private void showState(State state) {
        this.state = state;
        stateObservable.showState(state);
    }

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public State getState() {
        return state;
    }

    public ListPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(ListPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private static final String IMAGE_LIST_POSITION_INDEX = "image_list_position_index";
    private static final String IMAGE_LIST_POSITION_TOP = "image_list_position_top";

    public void saveListValues() {
        SharedPreferences.Editor editor = Preferences.get().edit();
        editor.putInt(IMAGE_LIST_POSITION_INDEX, currentPosition.getIndex());
        editor.putInt(IMAGE_LIST_POSITION_TOP, currentPosition.getTop());
        editor.apply();
    }

    public void loadListValues() {
        SharedPreferences pref = Preferences.get();
        int index = pref.getInt(IMAGE_LIST_POSITION_INDEX, 0);
        int top = pref.getInt(IMAGE_LIST_POSITION_TOP, 0);
        currentPosition = new ListPosition(index, top);
    }
}
