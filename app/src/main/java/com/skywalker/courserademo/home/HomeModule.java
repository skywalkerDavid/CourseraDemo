package com.skywalker.courserademo.home;

import com.skywalker.courserademo.PerActivity;
import com.skywalker.courserademo.api.SearchApiClient;
import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    @PerActivity
    HomeMVPContractor.Presenter providePresenter(final SearchApiClient searchApiClient) {
        return new HomePresenter(searchApiClient);
    }
}
