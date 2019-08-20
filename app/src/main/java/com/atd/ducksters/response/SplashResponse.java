package com.atd.ducksters.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.atd.ducksters.common.Status;
import com.atd.ducksters.pojo.LoginPojo;

import io.reactivex.Observable;
import io.reactivex.Observer;

import static com.atd.ducksters.common.Status.ERROR;
import static com.atd.ducksters.common.Status.LOADING;
import static com.atd.ducksters.common.Status.SUCCESS;

public class SplashResponse extends Observable<SplashResponse> {

    public final Status status;

    @Nullable
    public final LoginPojo data;

    @Nullable
    public final Throwable error;

    public SplashResponse(Status status, @Nullable LoginPojo data, @Nullable Throwable error) {
        Log.d("response  = = ", "jakhar"+status.toString()+"  +++++    "+error.getMessage());
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static SplashResponse loading() {
        return new SplashResponse(LOADING, null, null);
    }

    public static SplashResponse success(@NonNull SplashResponse SplashResponse) {
        return new SplashResponse(SUCCESS, SplashResponse.data, null);
    }

    public static SplashResponse error(@NonNull Throwable error) {
        return new SplashResponse(ERROR, null, error);
    }

    @Override
    protected void subscribeActual(Observer<? super SplashResponse> observer) {

    }
}
