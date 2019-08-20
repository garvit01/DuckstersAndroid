package com.atd.ducksters.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.atd.ducksters.response.SplashResponse;
import com.atd.ducksters.rx.SchedulersFacade;
import com.atd.ducksters.serviceImpl.SplashServiceImpl;

import io.reactivex.disposables.CompositeDisposable;

public class SplashViewModel extends ViewModel {

    public final SplashServiceImpl splashServiceImpl;

    public final SchedulersFacade schedulersFacade;

    public final CompositeDisposable disposables = new CompositeDisposable();

    public final MutableLiveData<SplashResponse> splashPojoMutableLiveData = new MutableLiveData<>();

    public SplashViewModel(SplashServiceImpl splashServiceImpl, SchedulersFacade schedulersFacade) {
        this.splashServiceImpl = splashServiceImpl;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public void setSplashServiceImpl(String data) {
        splash(splashServiceImpl, data);
    }

    public MutableLiveData<SplashResponse> directLogin() {
        return splashPojoMutableLiveData;
    }

    public MutableLiveData<SplashResponse> response() {
        return splashPojoMutableLiveData;
    }

    public void splash(SplashServiceImpl splashServiceImpl, String data) {

        disposables.add(splashServiceImpl.getUserData(data)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .doOnSubscribe(__ -> splashPojoMutableLiveData.setValue(SplashResponse.loading()))
                .subscribe(
                        splashResponce -> splashPojoMutableLiveData.setValue(SplashResponse.success(splashResponce)),
                        throwable -> splashPojoMutableLiveData.setValue(SplashResponse.error(throwable))
                )
        );
    }

}
