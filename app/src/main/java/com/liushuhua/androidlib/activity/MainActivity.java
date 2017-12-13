package com.liushuhua.androidlib.activity;

import android.os.Bundle;
import android.view.View;

import com.liushuhua.androidlib.R;
import com.liushuhua.androidlib.model.Book;
import com.liushuhua.androidlib.model.Motto;
import com.liushuhua.androidlib.present.BookPresent;
import com.liushuhua.androidlib.present.DownloadPresent;
import com.liushuhua.androidlib.present.MottoPresent;
import com.liushuhua.androidlib.present.UploadPresent;
import com.liushuhua.androidlib.view.BookView;
import com.liushuhua.androidlib.view.MottoView;
import com.liushuhua.androidlib.view.ProgressView;
import com.liushuhua.androidlib.view.UploadView;
import com.liushuhua.baseutils.LogUtils;

import java.io.File;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private BookPresent bookPresent = new BookPresent();
    private DownloadPresent downloadPresent = new DownloadPresent();
    private MottoPresent mottoPresent = new MottoPresent();
    private UploadPresent uploadPresent = new UploadPresent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textView).setOnClickListener(this);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.motto).setOnClickListener(this);
    }

    @Override
    public void initPresent() {
        super.initPresent();

        bookPresent.onCreate();
        bookPresent.attachView(bookView);

        downloadPresent.onCreate();
        downloadPresent.attachView(progressView);

        mottoPresent.onCreate();
        mottoPresent.attachView(mottoView);

        uploadPresent.onCreate();
        uploadPresent.attachView(uploadView);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textView) {
            bookPresent.searchBook("金瓶梅", null, 0, 1);
        } else if (v.getId() == R.id.download) {
            downloadPresent.downloadFile("qqteam/AndroidQQ/mobileqq_android.apk", "mobileqq_android.apk");
        } else if (v.getId() == R.id.motto) {
            mottoPresent.getMottoList("7ff49d067102772f69d7b4f26c380287", "人才", 1, 20);
        } else if (v.getId() == R.id.upload) {
            uploadPresent.uploadFile("test", new File(""));
        }
    }


    private BookView bookView = new BookView() {

        public void onSuccess(Book book) {
            LogUtils.i(TAG, "onSuccess: " + book.getTotal());
        }

        @Override
        public void onError(String e) {
            LogUtils.e(TAG, "onError: " + e);
        }
    };

    private ProgressView progressView = new ProgressView() {
        private int currentProgress;

        @Override
        public void onProgress(int progress) {
            if (progress != currentProgress) {
                currentProgress = progress;
                LogUtils.i(TAG, "onProgress: " + progress + "%");
            }
        }

        @Override
        public void onFail(String error) {
            LogUtils.e(TAG, "onError: " + error);
        }

        @Override
        public void onComplete() {
            LogUtils.i(TAG, "onComplete: 下载完成");
        }

    };

    private MottoView mottoView = new MottoView() {
        @Override
        public void onSuccess(List<Motto.ResultBean> list) {
            for (Motto.ResultBean bean : list) {
                LogUtils.e(TAG, bean.getFamous_name());
            }
        }

        @Override
        public void onFail(String error) {
            LogUtils.e(TAG, "onFail: " + error);
        }
    };

    private UploadView uploadView = new UploadView() {
    };
}
