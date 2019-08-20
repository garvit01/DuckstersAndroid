package com.atd.ducksters.service;

import com.atd.ducksters.response.SplashResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SplashService {
//    @POST("{userId}")
//    @POST("/api/userprofile/{data}")
//    @POST("userprofile/{data}-shiva")
//    Observable<SplashResponse> getUserData(@Path("data") String data);

    @FormUrlEncoded
    @POST("/userprofile/")
    Observable<SplashResponse> getUserData(@Field("data") String data);
}


//    @GET("{coin}-usd")
//    Observable<Crypto> getCoinData(@Path("coin") String coin);
//
//    @GET("/api/unknown")
//    Call<MultipleResource> doGetListResources();
//
//    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);