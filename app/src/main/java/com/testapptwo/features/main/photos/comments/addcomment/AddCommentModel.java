package com.testapptwo.features.main.photos.comments.addcomment;


import android.util.Log;

import com.testapptwo.api.ApiWrapper;
import com.testapptwo.api.ErrorMessages;
import com.testapptwo.api.request.TextRequestInfo;
import com.testapptwo.api.response.ResponseInfo;
import com.testapptwo.features.AppExecutors;
import com.testapptwo.features.main.photos.comments.CommentsModelContainer;
import com.testapptwo.features.user.UserModel;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created on 03.02.2017.
 */

public class AddCommentModel {

    private ModelObservable modelObservable = new ModelObservable();
    private int imageId;

    private String text;
    private String errorMessage;

    public AddCommentModel(int imageId) {
        this.imageId = imageId;
    }

    public void send() {
        ApiWrapper.getApi()
                .postComment(imageId, new TextRequestInfo(text), UserModel.getToken())
                .subscribeOn(AppExecutors.NETWORK_SCHEDULER)
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseInfo::getData)
                .subscribe(commentInfo -> {
                    Log.d(getClass().getSimpleName(), "post comment answer: " + commentInfo);
                    CommentsModelContainer.getInstance(imageId).addComment(commentInfo);
                    modelObservable.onComplete();
                }, throwable -> {
                    errorMessage = "post comment error: " + ErrorMessages.selectErrorMessage(throwable);
                    Log.d(getClass().getSimpleName(), errorMessage);
                    modelObservable.onError();
                });
    }

    public void registerObserver(ModelObserver observer) {
        modelObservable.registerObserver(observer);
    }

    public void unregisterObserver(ModelObserver observer) {
        modelObservable.unregisterObserver(observer);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
