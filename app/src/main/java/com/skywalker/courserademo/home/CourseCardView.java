package com.skywalker.courserademo.home;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.skywalker.courserademo.R;
import java.util.List;

public class CourseCardView extends FrameLayout {
    private static int defaultImageHeight;
    private static int defaultImageWidth;

    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.title) TextView titleTextView;
    @BindView(R.id.universities) TextView universitiesTextView;
    @BindView(R.id.course_number) TextView courseNumberTextView;

    public CourseCardView(@NonNull Context context) {
        super(context);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        inflate(getContext(), R.layout.course_card, this);
        ButterKnife.bind(this);

        setDefaultImageSize(imageView.getResources());
    }

    private void setDefaultImageSize(final Resources resources) {
        defaultImageWidth = resources.getDimensionPixelSize(R.dimen.image_width);
        defaultImageHeight = resources.getDimensionPixelSize(R.dimen.image_height);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setUniversities(List<String> universities) {
        if (universities == null || universities.isEmpty()) {
            return;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        final int size = universities.size();
        for (int i = 0; i < size; ++i) {
            stringBuilder.append(universities.get(i));
            if (i < size-1) {
                stringBuilder.append(",");
            }
        }
        universitiesTextView.setText(stringBuilder.toString());
    }

    public void setCourseNumber(Integer courseNumber) {
        if (courseNumber != null) {
            courseNumberTextView.setVisibility(VISIBLE);
            courseNumberTextView.setText(getResources().getQuantityString(R.plurals.coures_postfix, courseNumber, courseNumber));
        } else {
            courseNumberTextView.setVisibility(GONE);
        }
    }

    public void setImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        Glide.with(getContext().getApplicationContext())
            .load(imageUrl)
            .override(defaultImageWidth, defaultImageHeight)
            .fitCenter()
            .placeholder(R.drawable.ic_image_placeholder)
            .dontAnimate()
            .into(imageView);
    }
}
