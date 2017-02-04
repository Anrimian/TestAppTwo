package com.testapptwo.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 27.01.2017.
 */

public class ApiWrapper {
    private volatile static ApiWrapper instance;

    private Retrofit retrofit;
    private Api api;

    private ApiWrapper() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://213.184.248.43:9099/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public static ApiWrapper getInstance() {
        if (instance == null) {
            synchronized (ApiWrapper.class) {
                if (instance == null) {
                    instance = new ApiWrapper();
                }
            }
        }
        return instance;
    }

    public static Api getApi() {
        return getInstance().api;
    }

    public static Retrofit getRetrofit() {
        return getInstance().retrofit;
    }
}
