package com.skywalker.courserademo.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * An {@link RecyclerView.OnScrollListener} implementation
 * that triggers a "load more" event whenever a user scrolls
 * though collection and breaches the threshold of visible items
 * before the end.
 *
 * Note: It only works with {@link LinearLayoutManager} or {@link android.support.v7.widget.GridLayoutManager}
 *
 * @see https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews
 */
public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 3;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;

    // Adapter that stores the dataset
    private final RecyclerView.Adapter adapter;

    public EndlessRecyclerViewOnScrollListener(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = adapter.getItemCount();
        final int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we call onLoadMore to fetch the data.
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore();
            loading = true;
        }
    }

    /**
     * Reset back to initial state
     */
    public void reset() {
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data
    public abstract void onLoadMore();
}
