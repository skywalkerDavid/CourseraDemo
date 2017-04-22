package com.skywalker.courserademo.api;

import com.skywalker.courserademo.PerActivity;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    @PerActivity
    SearchApiClient provideSearchApiClient(Retrofit retrofit) {
        return retrofit.create(SearchApiClient.class);
    }
}
