package com.atd.ducksters.serviceImpl;

import com.atd.ducksters.common.APIClient;
import com.atd.ducksters.repository.SplashRepository;
import com.atd.ducksters.response.SplashResponse;
import com.atd.ducksters.service.SplashService;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SplashServiceImpl implements SplashService {

    public final SplashRepository splashRepository;

    @Inject
    public SplashServiceImpl(SplashRepository splashRepository) {
        this.splashRepository = splashRepository;
    }

    @Override
    public Observable<SplashResponse> getUserData(String id) {
       return splashRepository.getUserData(id);
//        SplashService splashService = APIClient.getClient(url).create(SplashService.class);
//        Observable<SplashResponse> responceObservable = splashService.getUserData("btc");
//        return responceObservable;
    }



}
