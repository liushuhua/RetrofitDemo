package com.liushuhua.androidlib;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liushuhua on 17-12-8.
 * Description:Retrofit帮助类
 */

public class RetrofitHelper {

    private static volatile RetrofitHelper helper;
    private Retrofit retrofit;

    public static RetrofitHelper getInstance() {
        if (helper == null) {
            synchronized (RetrofitHelper.class) {
                if (helper == null) {
                    helper = new RetrofitHelper();
                }
            }
        }
        return helper;
    }

    private RetrofitHelper() {
        //初始化Retrofit,单例模式，避免资源的浪费
        retrofit = new Retrofit.Builder().
                baseUrl(Constant.BASE_URL).
                client(new OkHttpClient()).
                addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                build();
    }


    /**
     * 返回Api
     *
     * @return Api
     */
    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
