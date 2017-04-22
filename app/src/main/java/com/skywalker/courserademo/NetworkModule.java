package com.skywalker.courserademo;

import android.app.Application;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static final String CACHE_FILE_NAME = "okhttp";
    private static final int CACHE_MAX_SIZE = 16 * 1024 * 1024; // 16MB
    private static final String ROOT_URL = "https://api.coursera.org/api/";

    @Provides
    @Singleton
    Gson providGson() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOKHttpClient(Application application) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        final File cacheDir = new File(application.getCacheDir(), CACHE_FILE_NAME);
        final Cache cache = new Cache(cacheDir, CACHE_MAX_SIZE);
        builder.cache(cache);

        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ROOT_URL)
            .client(okHttpClient)
            .build();
    }

}
