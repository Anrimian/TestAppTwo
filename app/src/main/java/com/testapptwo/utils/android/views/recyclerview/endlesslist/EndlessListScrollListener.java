package com.testapptwo.utils.android.views.recyclerview.endlesslist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created on 18.11.2016.
 */

public class EndlessListScrollListener extends RecyclerView.OnScrollListener {

    private EndlessListCallback callback;
    private LinearLayoutManager linearLayoutManager;

    public EndlessListScrollListener(LinearLayoutManager linearLayoutManager, EndlessListCallback callback) {
        this.linearLayoutManager = linearLayoutManager;
        this.callback = callback;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = linearLayoutManager.getItemCount();
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        if (lastVisibleItemPosition > totalItemCount - 10 && callback.canLoad()) {
            callback.loadNextData();
        }
    }
}
