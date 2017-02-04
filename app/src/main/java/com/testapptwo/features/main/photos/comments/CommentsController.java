package com.testapptwo.features.main.photos.comments;

import android.app.Activity;
import android.widget.Toast;

import com.testapptwo.features.main.photos.state.State;
import com.testapptwo.features.main.photos.state.ModelObserver;
import com.testapptwo.utils.android.views.recyclerview.endlesslist.EndlessListCallback;

/**
 * Created on 31.01.2017.
 */

public class CommentsController implements ViewActions, EndlessListCallback, ModelObserver {

    private CommentsListView view;
    private CommentsModel model;
    private CommentsListAdapter adapter;

    private Activity activity;

    public CommentsController(CommentsListView view, CommentsModel model, CommentsListAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.view = view;
        this.model = model;
        this.activity = activity;
    }

    public void bind() {
        view.bind(adapter, this, this, model.getCurrentPosition());
        model.registerStateObserver(this);
    }

    public void unbind() {
        model.setCurrentPosition(view.getCurrentPosition());
        model.saveListValues();
        model.unregisterStateObserver(this);
    }

    @Override
    public void onProgress() {
        view.showProgress(model.isRefreshing());
    }

    @Override
    public void onError() {
        view.showError(model.getErrorMessage(), model.isRefreshing());
    }

    @Override
    public void onWait() {
        view.showList();
    }

    @Override
    public void onEnd() {
        view.showEnd();
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteError(String errorMessage) {
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToCurrentListPosition() {
        view.setCurrentPosition(model.getCurrentPosition());
    }

    @Override
    public void onRefresh() {
        model.startLoading(true);
    }

    @Override
    public void tryLoadAgain() {
        model.startLoading(false);
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getCount() == 0;
    }

    @Override
    public boolean canLoad() {
        return model.getState() == State.WAIT;
    }

    @Override
    public void loadNextData() {
        model.startLoading(false);
    }


}
