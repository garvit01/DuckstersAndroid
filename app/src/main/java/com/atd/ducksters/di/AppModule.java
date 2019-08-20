package com.atd.ducksters.di;

import android.content.Context;

import com.atd.ducksters.DucksterApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(DucksterApplication application) {
        return application.getApplicationContext();
    }

}

