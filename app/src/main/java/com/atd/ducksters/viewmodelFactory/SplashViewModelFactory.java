package com.atd.ducksters.viewmodelFactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.atd.ducksters.rx.SchedulersFacade;
import com.atd.ducksters.serviceImpl.SplashServiceImpl;
import com.atd.ducksters.viewmodel.SplashViewModel;

public class SplashViewModelFactory implements ViewModelProvider.Factory {

    public final SplashServiceImpl splashServiceImpl;

    public final SchedulersFacade schedulersFacade;

    public SplashViewModelFactory(SplashServiceImpl splashServiceImpl,
                                  SchedulersFacade schedulersFacade) {
        this.splashServiceImpl = splashServiceImpl;
        this.schedulersFacade = schedulersFacade;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(splashServiceImpl, schedulersFacade);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
