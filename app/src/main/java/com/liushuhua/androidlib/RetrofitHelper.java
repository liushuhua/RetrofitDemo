package com.liushuhua.androidlib;

import com.google.gson.GsonBuilder;
import com.liushuhua.androidlib.interceptor.CacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liushuhua on 17-12-8.
 * Description:Retrofit帮助类
 */

public class RetrofitHelper {

    private static final long DEFAULT_TIMEOUT = 60;
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
        //创建Cache文件
        File cacheFile = new File(MyApplication.getMyApplicationContext().getCacheDir(), "respond");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheFile, cacheSize);
        //Http-logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        //初始化Retrofit,单例模式，避免资源的浪费
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(new CacheInterceptor()).
                addInterceptor(interceptor).
                cache(cache).
                connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                build();
        retrofit = new Retrofit.Builder().
                baseUrl(Api.BASE_URL).
                client(client).
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
