package com.liushuhua.androidlib.present;

import com.liushuhua.androidlib.DataManager;
import com.liushuhua.androidlib.view.UploadView;
import com.liushuhua.androidlib.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liushuhua on 17-12-13.
 * Description:上传文件
 */

public class UploadPresent extends BasePresent {

    private UploadView uploadView;
    private CompositeSubscription subscription;


    @Override
    public void attachView(View view) {
        uploadView = (UploadView) view;
    }

    @Override
    public void onCreate() {
        subscription = new CompositeSubscription();
    }

    @Override
    public void onStop() {
        if (subscription != null && subscription.hasSubscriptions()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 上传单个文件
     *
     * @param description 描述
     * @param file        文件列表
     */
    public void uploadFile(String description, File file) {
        List<File> files = new ArrayList<>();
        files.add(file);
        uploadFiles(description, files);
    }

    /**
     * 上传多个文件
     *
     * @param description 描述
     * @param files       文件列表
     */
    public void uploadFiles(String description, List<File> files) {
        Map<String, RequestBody> params = new HashMap<>();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            params.put(file.getName(), requestBody);
        }
        subscription.add(DataManager.getInstance().
                uploadFile(description, params).
                subscribeOn(Schedulers.io()).
                observeOn(Schedulers.io()).
                subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //TODO 后续完善
                        onStop();
                    }

                    @Override
                    public void onError(Throwable e) {
                        uploadView.onFail(e.getMessage());
                        onStop();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //TODO 后续完善
                    }
                }));
    }
}
