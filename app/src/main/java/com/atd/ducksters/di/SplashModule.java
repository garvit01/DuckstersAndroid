package com.atd.ducksters.di;

import com.atd.ducksters.repository.SplashRepository;
import com.atd.ducksters.rx.SchedulersFacade;
import com.atd.ducksters.serviceImpl.SplashServiceImpl;
import com.atd.ducksters.viewmodelFactory.SplashViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    @Singleton
    SplashRepository provideSplashRepository() {
        return new SplashRepository();
    }

    @Provides
    @Singleton
    SplashViewModelFactory provideSplashViewModelFactor(
            SplashServiceImpl splashServiceImpl,
            SchedulersFacade schedulersFacade) {
        return new SplashViewModelFactory(splashServiceImpl, schedulersFacade);
    }
}