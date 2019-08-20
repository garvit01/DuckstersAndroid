package com.atd.ducksters.repository;

import android.util.Log;

import com.atd.ducksters.common.APIClient;
import com.atd.ducksters.response.SplashResponse;
import com.atd.ducksters.service.SplashService;

import io.reactivex.Observable;

public class SplashRepository {
//    private String url = "http://localhost:8020/api/userprofile/";
    private String url = "http://192.168.1.10:8020/api/";
    public Observable<SplashResponse> getUserData(String id) {
        id = "oY/KRA3TsdACoBvaWgZmNA==";
        SplashService splashService = APIClient.getClient(url).create(SplashService.class);
        Log.d("repository response", "shiva = "+splashService.toString());
        Observable<SplashResponse> responceObservable = splashService.getUserData(id);
//        Observable<SplashResponse> responceObservable = splashService.getUserData("/userprofile"+id);
        Log.d("repository response 2", "shiva = "+responceObservable.toString());
        return responceObservable;
    }
}
