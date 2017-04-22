package com.skywalker.courserademo;

import android.app.Application;
import com.skywalker.courserademo.home.CourseCardView;

public class CourseraApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .networkModule(new NetworkModule())
            .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
