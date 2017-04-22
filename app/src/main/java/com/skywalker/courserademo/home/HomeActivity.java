package com.skywalker.courserademo.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.skywalker.courserademo.CourseraApplication;
import com.skywalker.courserademo.R;
import com.skywalker.courserademo.api.ApiModule;
import java.util.List;
import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeMVPContractor.View {

    @Inject HomeMVPContractor.Presenter presenter;

    @BindView(R.id.search_text) TextView searchTextView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.search_result_recyclerview) RecyclerView recyclerView;

    private HomeComponent component;
    private CourseRecyclerViewAdapter courseRecyclerViewAdapter;
    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private CoursesViewOnScrollListener endlessOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void initView() {
        courseRecyclerViewAdapter = new CourseRecyclerViewAdapter();
        endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(courseRecyclerViewAdapter, R.layout.loading_more);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(endlessRecyclerViewAdapter);
        endlessOnScrollListener = new CoursesViewOnScrollListener(courseRecyclerViewAdapter);
        swipeRefreshLayout.setOnRefreshListener(this::onSearch);
    }

    private HomeComponent getComponent() {
        if (this.component == null) {
            this.component = this.buildComponent();
            if (this.component == null) {
                throw new IllegalArgumentException("buildComponent() returns null, getComponent() must not be called if buildComponent() returns null");
            }
        }

        return this.component;
    }

    private HomeComponent buildComponent() {
        return DaggerHomeComponent.builder()
            .applicationComponent(((CourseraApplication) getApplication()).getApplicationComponent())
            .homeModule(new HomeModule())
            .apiModule(new ApiModule())
            .build();
    }

    @OnClick(R.id.search_button)
    public void onSearch() {
        final String searchText = searchTextView.getText().toString();
        if (!searchText.isEmpty()) {
            endlessOnScrollListener.reset();
            presenter.search(searchText);
            closeKeyboard();
        } else {
            Toast.makeText(this, getString(R.string.search_empty_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
    }

    @Override
    public void showSearching() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showSearchResult(List<ItemViewModel> itemViewModels) {
        courseRecyclerViewAdapter.setCourses(itemViewModels);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMoreSearchResult(List<ItemViewModel> itemViewModels) {
        courseRecyclerViewAdapter.addCourses(itemViewModels);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.search_general_error), Toast.LENGTH_LONG).show();
        courseRecyclerViewAdapter.clearCourses();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadingMoreView() {
        endlessRecyclerViewAdapter.setProgressViewVisible(true);
    }

    @Override
    public void hideLoadingMoreView() {
        endlessRecyclerViewAdapter.setProgressViewVisible(false);
    }

    @Override
    public void allowRequestMore() {
        recyclerView.addOnScrollListener(endlessOnScrollListener);
    }

    @Override
    public void disallowRequestMore() {
        recyclerView.removeOnScrollListener(endlessOnScrollListener);
    }

    private class CoursesViewOnScrollListener extends EndlessRecyclerViewOnScrollListener {

        public CoursesViewOnScrollListener(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public void onLoadMore() {
            presenter.onLoadMore();
        }
    }
}
