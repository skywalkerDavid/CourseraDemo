package com.skywalker.courserademo.home;

import java.util.List;

public interface HomeMVPContractor {
    interface View {
        void showSearching();
        void showSearchResult(List<ItemViewModel> itemViewModels);
        void showMoreSearchResult(List<ItemViewModel> itemViewModels);
        void showError();
        void allowRequestMore();
        void disallowRequestMore();
        void showLoadingMoreView();
        void hideLoadingMoreView();
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void onDestroy();
        void search(String query);
        void onLoadMore();
    }
}
