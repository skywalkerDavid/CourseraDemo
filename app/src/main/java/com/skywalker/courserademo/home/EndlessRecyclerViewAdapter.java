package com.skywalker.courserademo.home;

import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EndlessRecyclerViewAdapter extends RecyclerView.Adapter {

    private final RecyclerView.Adapter adapter;
    private final int progressViewId;
    private ProgressViewHolder progressViewHolder;

    private boolean isProgressVisible = false;

    public EndlessRecyclerViewAdapter(@NonNull final RecyclerView.Adapter adapter, @LayoutRes int progressViewId) {
        this.adapter = adapter;
        this.progressViewId = progressViewId;

        super.setHasStableIds(adapter.hasStableIds());

        adapter.registerAdapterDataObserver(new ForwardingDataSetObserver());
    }

    public void setProgressViewVisible(final boolean visible) {
        new Handler().post(() -> {
            if (EndlessRecyclerViewAdapter.this.isProgressVisible == visible) {
                return;
            }

            EndlessRecyclerViewAdapter.this.isProgressVisible = visible;

            final int position = adapter.getItemCount();

            if (visible) {
                notifyItemInserted(position);
            } else {
                notifyItemRemoved(position);
            }
        });
    }

    public boolean isProgressView(int position) {
        return getItemViewType(position) == progressViewId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == progressViewId) {
            progressViewHolder = new ProgressViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(progressViewId, parent, false));

            return progressViewHolder;
        }
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isProgressView(position)) {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isProgressVisible && position == (getItemCount() - 1)) {
            return progressViewId;
        }
        return adapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + (isProgressVisible ? 1 : 0);
    }

    @Override
    public long getItemId(int position) {
        if (isProgressView(position)) {
            return super.getItemId(position);
        }
        return adapter.getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder == progressViewHolder) {
            return;
        }
        adapter.onViewRecycled(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder == progressViewHolder) {
            return;
        }
        adapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder == progressViewHolder) {
            return;
        }
        adapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        adapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        adapter.onDetachedFromRecyclerView(recyclerView);
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View view) {
            super(view);
        }
    }

    private class ForwardingDataSetObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition, toPosition);
        }
    }
}
