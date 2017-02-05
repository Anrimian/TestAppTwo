package com.testapptwo.features.main.photos.comments;

import android.util.Log;

import com.testapptwo.api.ApiWrapper;
import com.testapptwo.api.ErrorMessages;
import com.testapptwo.api.data.CommentInfo;
import com.testapptwo.api.response.ResponseInfo;
import com.testapptwo.database.rx.CommentsDaoRxWrapper;
import com.testapptwo.features.AppExecutors;
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

public class CommentsModel {

    private static final int PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private ListPosition currentPosition;

    private State state = State.WAIT;
    private StateObservable stateObservable = new StateObservable();

    private String errorMessage;
    private boolean refreshing;

    private final List<CommentInfo> commentsList = new ArrayList<>();

    private int imageId;

    public CommentsModel(int imageId) {
        this.imageId = imageId;
        init();
    }

    private void init() {
        loadListValues();
        showState(State.PROGRESS);
        CommentsDaoRxWrapper.getCommentsList(imageId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsList -> {
                    if (commentsList.isEmpty()) {
                        startLoading(false);
                    } else {
                        this.commentsList.addAll(commentsList);
                        stateObservable.updateList();
                        stateObservable.goToCurrentListPosition();
                        showState(State.WAIT);
                    }
                });
    }

    public void startLoading(boolean refresh) {
        showState(State.PROGRESS);
        int page = EndlessListUtils.getNextPage(commentsList, PAGE_SIZE);
        if (refresh) {
            page = 0;
            refreshing = true;
        }
        startLoading(page, refresh);
    }

    private void startLoading(int page, boolean refresh) {
        Log.d(getClass().getSimpleName(), "load page: " + page + ", imageId: " + imageId + ", token: " + UserModel.getToken());
        ApiWrapper.getApi()
                .getComment(imageId, page, UserModel.getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseInfo::getData)
                .subscribe(commentsList -> {
                    Log.d(getClass().getSimpleName(), "commentsList: " + commentsList);
                    Log.d(getClass().getSimpleName(), "size: " + commentsList.size());
                    setListData(commentsList, refresh);
                    stateObservable.updateList();
                    refreshing = false;
                    if (commentsList.isEmpty()) {
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
                    Log.d(getClass().getSimpleName(), "errorMessage: " + errorMessage);
                    showState(State.ERROR);
                    refreshing = false;
                });
    }

    private void setListData(List<CommentInfo> newComments, boolean refresh) {
        if (refresh) {
            deleteListData();
        }
        CommentsDaoRxWrapper.addComments(newComments, imageId);
        for (CommentInfo comment : newComments) {
            if (!commentsList.contains(comment)) {
                commentsList.add(comment);
            }
        }
        //commentsList.addAll(imageResponseList);
    }

    public void deleteListData() {
        CommentsDaoRxWrapper.deleteComments(imageId);
        commentsList.clear();
    }

    public void addComment(CommentInfo commentInfo) {
        commentsList.add(commentInfo);
        stateObservable.updateList();
        showState(state);
        CommentsDaoRxWrapper.addComment(commentInfo, imageId);
    }

    public void deleteComment(CommentInfo commentInfo) {
        Log.d(getClass().getSimpleName(), "deleteComment, imageId: " + imageId + ", commentId: " + commentInfo.getId() + ", token: " + UserModel.getToken());
        ApiWrapper.getApi()
                .deleteComment(imageId, commentInfo.getId(), UserModel.getToken())
                .subscribeOn(AppExecutors.NETWORK_SCHEDULER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deletedCommentInfo -> {
                    Log.d(getClass().getSimpleName(), "deleted: " + deletedCommentInfo);
                    commentsList.remove(commentInfo);
                    stateObservable.updateList();
                    showState(state);
                    CommentsDaoRxWrapper.deleteComment(commentInfo);
                }, throwable -> {
                    String errorMessage = "delete comment failed: " + ErrorMessages.selectErrorMessage(throwable);
                    Log.d(getClass().getSimpleName(), errorMessage);
                    stateObservable.onDeleteError(errorMessage);
                });
    }

    private void showState(State state) {
        this.state = state;
        stateObservable.showState(state);
    }



   public void registerStateObserver(ModelObserver stateObserver) {
        state.showState(stateObserver);
        stateObservable.registerObserver(stateObserver);
    }

    public void unregisterStateObserver(ModelObserver stateObserver) {
        stateObservable.unregisterObserver(stateObserver);
    }



    public List<CommentInfo> getCommentsList() {
        return commentsList;
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

    public void saveListValues() {
    }

    public void loadListValues() {
    }


}
