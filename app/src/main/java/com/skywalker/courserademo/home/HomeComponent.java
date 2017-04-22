package com.skywalker.courserademo.home;

import com.skywalker.courserademo.ApplicationComponent;
import com.skywalker.courserademo.PerActivity;
import com.skywalker.courserademo.api.ApiModule;
import dagger.Component;

@PerActivity
@Component(
    dependencies = {
        ApplicationComponent.class
    },
    modules = {
        HomeModule.class,
        ApiModule.class
    }
)
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}