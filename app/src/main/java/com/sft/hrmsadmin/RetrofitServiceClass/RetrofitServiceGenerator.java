package com.sft.hrmsadmin.RetrofitServiceClass;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {

    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(600, TimeUnit.SECONDS)
            .connectTimeout(600, TimeUnit.SECONDS);
    private Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(mServiceList.Base_URL)
            .addConverterFactory(GsonConverterFactory.create());


    public <S> S createService(Class<S> serviceClass) {
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
