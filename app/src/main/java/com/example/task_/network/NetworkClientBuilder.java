package com.example.task_.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClientBuilder<T> {
    private final String baseUrl;
    private Retrofit retrofit;


    public NetworkClientBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
        initRetrofit();
    }


    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public T getApiService(Class<T> service) {
        return retrofit.create(service);
    }

}
