package com.liushuhua.androidlib.view;

/**
 * Created by liushuhua on 17-12-13.
 * Description:
 */

public interface UploadView extends View {
    void onFail(String error);

    void onSuccess(String error);
}
