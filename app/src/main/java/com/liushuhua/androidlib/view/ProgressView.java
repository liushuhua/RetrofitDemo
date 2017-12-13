package com.liushuhua.androidlib.view;

/**
 * Created by liushuhua on 17-12-11.
 * Description:进度监听的View
 */

public interface ProgressView extends View {
    /**
     * 进度
     * @param progress 进度
     */
    void onProgress(int progress);

    /**
     * 下载失败
     *
     * @param error 错误
     */
    void onFail(String error);

    /**
     * 下载完成
     */
    void onComplete();
}
