package com.liushuhua.androidlib.view;

import com.liushuhua.androidlib.model.Motto;

import java.util.List;

/**
 * Created by liushuhua on 17-12-11.
 * Description:
 */

public interface MottoView extends View {
    void onSuccess(List<Motto.ResultBean> list);

    void onFail(String error);
}
