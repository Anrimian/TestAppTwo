package com.testapptwo.features.main.photos.comments;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.testapptwo.R;
import com.testapptwo.utils.android.views.ProgressViewBinder;
import com.testapptwo.utils.android.views.recyclerview.ListPosition;
import com.testapptwo.utils.android.views.recyclerview.endlesslist.EndlessListCallback;
import com.testapptwo.utils.android.views.recyclerview.endlesslist.EndlessListScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 31.01.2017.
 */

public class CommentsListView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private View progressStateView;
    private ProgressViewBinder footerProgressViewBinder;
    private LinearLayoutManager layoutManager;

    private ViewActions viewActions;

    public CommentsListView(View view) {
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(() -> viewActions.onRefresh());

        progressStateView = View.inflate(getContext(), R.layout.progress_state_view, null);
        footerProgressViewBinder = new ProgressViewBinder(progressStateView);
        footerProgressViewBinder.setTryAgainButtonOnClickListener((v) -> viewActions.tryLoadAgain());
    }

    public void bind(CommentsListAdapter listAdapter,
                     ViewActions viewActions,
                     EndlessListCallback endlessListCallback,
                     ListPosition listPosition) {
        this.viewActions = viewActions;
        recyclerView.addOnScrollListener(new EndlessListScrollListener(layoutManager, endlessListCallback));
        recyclerView.setAdapter(listAdapter);
        listAdapter.addFooter(progressStateView);
        setCurrentPosition(listPosition);
    }

    public void showProgress(boolean refreshing) {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setEnabled(false);
        }
        if (refreshing) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            footerProgressViewBinder.showProgress();
        }
    }

    public void showError(String errorMessage, boolean wasRefreshing) {
        enableSwipeRefreshLayout();
        footerProgressViewBinder.showMessage(errorMessage, true);
    }

    public void showEnd() {
        enableSwipeRefreshLayout();
        if (isListEmpty()) {
            footerProgressViewBinder.showMessage(R.string.no_comments, false);
        } else {
            footerProgressViewBinder.showMessage(R.string.no_more_comments, false);
        }
    }

    public void showList() {
        enableSwipeRefreshLayout();
        footerProgressViewBinder.hideAll();
    }

    private void enableSwipeRefreshLayout() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    public ListPosition getCurrentPosition() {
        int index = layoutManager.findFirstVisibleItemPosition();
        View v = layoutManager.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();
        return new ListPosition(index, top);
    }

    public void setCurrentPosition(ListPosition listPosition) {
        if (listPosition != null) {
            int index = listPosition.getIndex();
            int top = listPosition.getTop();
            layoutManager.scrollToPositionWithOffset(index, top);
        }
    }

    private boolean isListEmpty() {
        return viewActions.isListEmpty();
    }

    private Context getContext() {
        return recyclerView.getContext();
    }
}
