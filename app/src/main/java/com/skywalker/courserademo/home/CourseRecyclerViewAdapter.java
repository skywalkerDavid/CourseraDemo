package com.skywalker.courserademo.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.skywalker.courserademo.R;
import java.util.ArrayList;
import java.util.List;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter {
    private static final int CARD_VIEW_TYPE = 0;
    private static final int EMPTY_VIEW_TYPE = 1;

    private final List<ItemViewModel> courses = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case CARD_VIEW_TYPE:
                final CourseCardView view = new CourseCardView(parent.getContext());
                return new CourseCardViewHolder(view);
            case EMPTY_VIEW_TYPE:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) != CARD_VIEW_TYPE) {
            return;
        }

        viewHolder.itemView.setTag(position);

        final ItemViewModel item = getItem(position);
        CourseCardView cardView = (CourseCardView) viewHolder.itemView;
        cardView.setTitle(item.name);
        cardView.setImage(item.imageUrl);
        cardView.setUniversities(item.universityName);
        cardView.setCourseNumber(item.numberOfCourses);
    }

    @Override
    public int getItemViewType(final int position) {
        if (courses.size() == 0) {
            return EMPTY_VIEW_TYPE;
        }

        return CARD_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        if (courses.size() == 0) {
            return 1;
        }
        return courses.size();
    }

    public void setCourses(List<ItemViewModel> items) {
        final int oldDealsSize = getItemCount();
        final int newDealsSize = items.size();

        this.courses.clear();
        this.courses.addAll(items);

        if (oldDealsSize < newDealsSize) {
            notifyItemRangeChanged(0, oldDealsSize);
            notifyItemRangeInserted(oldDealsSize, newDealsSize - oldDealsSize);
        } else if (oldDealsSize == newDealsSize) {
            notifyItemRangeChanged(0, newDealsSize);
        } else {
            notifyItemRangeChanged(0, newDealsSize);
            notifyItemRangeRemoved(newDealsSize, oldDealsSize - newDealsSize);
        }
    }

    public void clearCourses() {
        final int itemCount = courses.size();
        this.courses.clear();

        notifyItemRangeRemoved(0, itemCount);
    }

    public void addCourses(@NonNull final List<ItemViewModel> items) {
        final int positionStart = courses.size();

        this.courses.addAll(items);

        notifyItemRangeInserted(positionStart, items.size());
    }

    private ItemViewModel getItem(int position) {
        return courses.get(position);
    }
}
