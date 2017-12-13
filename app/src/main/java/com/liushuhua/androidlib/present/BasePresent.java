package com.liushuhua.androidlib.present;


import com.liushuhua.androidlib.view.View;

/**
 * Created by liushuhua on 17-12-8.
 * Description:基础Present
 */

abstract class BasePresent {

    abstract public void attachView(View view);

    abstract public void onCreate();

    abstract public void onStop();
}
