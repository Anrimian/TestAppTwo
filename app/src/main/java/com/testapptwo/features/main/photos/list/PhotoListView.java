package com.testapptwo.features.main.photos.list;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.testapptwo.R;
import com.testapptwo.utils.android.views.ProgressViewBinder;
import com.testapptwo.utils.android.views.recyclerview.HideFabScrollListener;
import com.testapptwo.utils.android.views.recyclerview.ListPosition;
import com.testapptwo.utils.android.views.recyclerview.endlesslist.EndlessListCallback;
import com.testapptwo.utils.android.views.recyclerview.endlesslist.EndlessListScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 31.01.2017.
 */

public class PhotoListView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private View progressStateView;
    private ProgressViewBinder footerProgressViewBinder;
    private GridLayoutManager gridLayoutManager;

    private ViewActions viewActions;

    public PhotoListView(View view) {
        ButterKnife.bind(this, view);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new FooterSpanSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new HideFabScrollListener(fab));
        swipeRefreshLayout.setOnRefreshListener(() -> viewActions.onRefresh());
        fab.setOnClickListener(v -> viewActions.onFabClick());

        progressStateView = View.inflate(getContext(), R.layout.progress_state_view, null);
        footerProgressViewBinder = new ProgressViewBinder(progressStateView);
        footerProgressViewBinder.setTryAgainButtonOnClickListener((v) -> viewActions.tryLoadAgain());
    }

    public void bind(PhotosListAdapter photosListAdapter,
                     ViewActions viewActions,
                     EndlessListCallback endlessListCallback,
                     ListPosition listPosition) {
        this.viewActions = viewActions;
        recyclerView.addOnScrollListener(new EndlessListScrollListener(gridLayoutManager, endlessListCallback));
        recyclerView.setAdapter(photosListAdapter);
        photosListAdapter.addFooter(progressStateView);
        setCurrentPosition(listPosition);
    }

    public void showProgress(boolean refreshing) {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setEnabled(false);
        }
        if (isListEmpty()) {
            tvError.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            footerProgressViewBinder.hideAll();
        } else {
            tvError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            if (refreshing) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                swipeRefreshLayout.setRefreshing(false);
                footerProgressViewBinder.showProgress();
            }
        }
    }

    public void showError(String errorMessage, boolean wasRefreshing) {
        enableSwipeRefreshLayout();
        if (isListEmpty()) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(errorMessage);
            progressBar.setVisibility(View.GONE);
            footerProgressViewBinder.hideAll();
        } else {
            footerProgressViewBinder.showMessage(errorMessage, true);
            tvError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            if (wasRefreshing) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showEnd() {
        enableSwipeRefreshLayout();
        if (isListEmpty()) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(R.string.no_photos);
            progressBar.setVisibility(View.GONE);
            footerProgressViewBinder.hideAll();
        } else {
            tvError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            footerProgressViewBinder.showMessage(R.string.no_more_photos, false);
        }
    }

    public void showList() {
        enableSwipeRefreshLayout();
        tvError.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        footerProgressViewBinder.hideAll();
    }

    private void enableSwipeRefreshLayout() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    public ListPosition getCurrentPosition() {
        int index = gridLayoutManager.findFirstVisibleItemPosition();
        View v = gridLayoutManager.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();
        return new ListPosition(index, top);
    }

    public void setCurrentPosition(ListPosition listPosition) {
        if (listPosition != null) {
            int index = listPosition.getIndex();
            int top = listPosition.getTop();
            gridLayoutManager.scrollToPositionWithOffset(index, top);
        }
    }

    private class FooterSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            if (gridLayoutManager.getItemCount()-1 == position) {
                return gridLayoutManager.getSpanCount();
            }
            return 1;
        }
    }

    private boolean isListEmpty() {
        return viewActions.isListEmpty();
    }

    private Context getContext() {
        return recyclerView.getContext();
    }
}
