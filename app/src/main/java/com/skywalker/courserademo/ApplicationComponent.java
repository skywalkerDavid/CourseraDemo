package com.skywalker.courserademo;

import android.content.SharedPreferences;
import dagger.Component;
import javax.inject.Singleton;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(CourseraApplication application);

    Retrofit retrofit();
    SharedPreferences sharedPreferences();
}
