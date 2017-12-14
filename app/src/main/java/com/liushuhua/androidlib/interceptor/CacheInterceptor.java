package com.liushuhua.androidlib.interceptor;

import com.liushuhua.androidlib.MyApplication;
import com.liushuhua.baseutils.NetUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liushuhua on 17-12-14.
 * Description:Cache拦截器
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);//有效期
        CacheControl cacheControl = cacheBuilder.build();
        Request request = chain.request();
        if (NetUtils.isNetworkAvailable(MyApplication.getMyApplicationContext())) {
            request = request.newBuilder().cacheControl(cacheControl).build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetUtils.isNetworkAvailable(MyApplication.getMyApplicationContext())) {
            int maxAge = 0;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
