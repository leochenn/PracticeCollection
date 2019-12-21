package com.leo.app.retrofit;

import com.leo.app.okhttp.OkHttpManager;
import com.leo.app.retrofit.gson.GsonConverterFactory2;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leo on 2019/7/26.
 */
public class RetrofitHelper {

    public static final String BASE_URL = "https://wanandroid.com/";

    private static Retrofit instance;

    public static Retrofit getRetrofit() {
        if (instance == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.client(OkHttpManager.getOkHttpClient());
            builder.addConverterFactory(GsonConverterFactory2.create());
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder.baseUrl(BASE_URL);

            instance = builder.build();
        }
        return instance;
    }

    public static <T> T create(final Class<T> service) {
        return getRetrofit().create(service);
    }
}
