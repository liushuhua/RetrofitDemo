package com.liushuhua.androidlib.present;

import android.content.Context;

import com.liushuhua.androidlib.DataManager;
import com.liushuhua.androidlib.MyApplication;
import com.liushuhua.androidlib.view.ProgressView;
import com.liushuhua.androidlib.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liushuhua on 17-12-8.
 * Description:
 */

public class DownloadPresent extends BasePresent {
    private ProgressView progressView;
    private CompositeSubscription subscription;

    @Override
    public void onCreate() {
        subscription = new CompositeSubscription();
    }

    @Override
    public void attachView(View view) {
        progressView = (ProgressView) view;
    }

    @Override
    public void onStop() {
        if (subscription != null && subscription.hasSubscriptions()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 下载文件
     *
     * @param url      Url
     * @param savePath 保存本地位置
     */
    public void downloadFile(String url, final String savePath) {
        subscription.add(DataManager.getInstance().
                downloadFile(url).
                subscribeOn(Schedulers.io()).
                observeOn(Schedulers.io()).
                subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        progressView.onComplete();
                        onStop();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressView.onFail(e.getMessage());
                        onStop();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (progressView != null) {
                            saveLocal(savePath, responseBody);
                        }
                    }
                }));
    }

    /**
     * 保存文件
     *
     * @param savePath path
     * @param body     data
     */
    private void saveLocal(String savePath, ResponseBody body) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        byte[] fileReader = new byte[4096];
        long fileSize = body.contentLength();
        long fileSizeDownloaded = 0;
        try {
            outputStream = MyApplication.getMyApplicationContext().openFileOutput(savePath, Context.MODE_PRIVATE);
            inputStream = body.byteStream();
            int line;
            while ((line = inputStream.read(fileReader)) != -1) {
                outputStream.write(fileReader, 0, line);
                fileSizeDownloaded += line;
                int progress = (int) ((fileSizeDownloaded * 100.0 / (fileSize * 100.0)) * 100);
                progressView.onProgress(progress);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
