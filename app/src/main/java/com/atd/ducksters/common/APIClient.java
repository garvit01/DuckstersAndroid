package com.atd.ducksters.common;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        LoggingInterceptor interceptor = new LoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


//        retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
//                .client(new OkHttpClient.Builder().build())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("retrofit response", "shiva = "+retrofit.toString());
        return retrofit;
    }

}