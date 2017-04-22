package com.skywalker.courserademo.home;

import com.skywalker.courserademo.api.SearchApiClient;
import com.skywalker.courserademo.api.SearchResult;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter implements HomeMVPContractor.Presenter {
    private static int LIMIT = 10;

    private HomeMVPContractor.View view;
    private List<ItemViewModel> itemViewModels = new ArrayList<>();
    private SearchApiClient searchApiClient;
    private Subscription subscription;
    private boolean isSearching;
    private String searchText;
    private int offset;

    HomePresenter(SearchApiClient searchApiClient) {
        this.searchApiClient = searchApiClient;
    }

    @Override
    public void attachView(HomeMVPContractor.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void search(String text) {
        if (isSearching) {
            return;
        }
        isSearching = true;

        if (view != null) {
            view.showSearching();
        }

        searchText = text;
        offset = 0;

        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = searchApiClient.search(searchText, offset, LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
            .subscribe(this::handleSearchCoursesResponse,
                       this::handleSearchErrorResponse);
    }

    @Override
    public void onLoadMore() {
        if (isSearching) {
            return;
        }
        isSearching = true;

        if (view != null) {
            view.disallowRequestMore();
            view.showLoadingMoreView();
        }

        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = searchApiClient.search(searchText, offset, LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
            .subscribe(this::handleLoadingMoreCoursesResponse,
                       this::handleLodingMoreErrorResponse);
    }

    private void handleSearchCoursesResponse(SearchResult result) {
        isSearching = false;
        itemViewModels.clear();

        final List<ItemViewModel> items = ItemViewModel.convert(result);
        itemViewModels.addAll(items);
        if (result.paging.current != null) {
            offset = Integer.parseInt(result.paging.current);
        } else {
            offset = 0;
        }

        if (view == null) {
            return;
        }

        view.showSearchResult(itemViewModels);

        final boolean hasMore = offset < result.paging.total;
        if (hasMore) {
            view.allowRequestMore();
        } else {
            view.disallowRequestMore();
        }
    }

    private void handleSearchErrorResponse(Throwable throwable) {
        isSearching = false;
        throwable.printStackTrace();
        itemViewModels.clear();

        if (view == null) {
            return;
        }

        view.showError();
        view.disallowRequestMore();
    }

    private void handleLoadingMoreCoursesResponse(SearchResult result) {
        isSearching = false;

        final List<ItemViewModel> items = ItemViewModel.convert(result);
        itemViewModels.addAll(items);
        offset = Integer.parseInt(result.paging.current);

        if (view == null) {
            return;
        }

        view.showMoreSearchResult(items);

        final boolean hasMore = offset < result.paging.total;
        if (hasMore) {
            view.allowRequestMore();
        } else {
            view.disallowRequestMore();
        }

        view.hideLoadingMoreView();
    }

    private void handleLodingMoreErrorResponse(Throwable throwable) {
        if (view == null) {
            return;
        }

        view.disallowRequestMore();
        view.hideLoadingMoreView();
    }
}
