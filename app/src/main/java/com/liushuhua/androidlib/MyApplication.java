package com.liushuhua.androidlib;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by liushuhua on 17-12-8.
 * Description:全局Application
 */

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * 获取全局上下文
     *
     * @return 全局上下文
     */
    public static Context getMyApplicationContext() {
        return context;
    }
}
