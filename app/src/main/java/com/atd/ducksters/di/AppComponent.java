package com.atd.ducksters.di;

import com.atd.ducksters.DucksterApplication;
import com.atd.ducksters.activity.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, SplashModule.class})
public interface AppComponent {

    void inject(DucksterApplication myApplication);

    void injectSplash(SplashActivity splashActivity);

}
