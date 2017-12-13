package com.liushuhua.androidlib.present;

import com.liushuhua.androidlib.DataManager;
import com.liushuhua.androidlib.model.Motto;
import com.liushuhua.androidlib.view.MottoView;
import com.liushuhua.androidlib.view.View;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liushuhua on 17-12-11.
 * Description:名人名言present
 */

public class MottoPresent extends BasePresent {

    private MottoView mottoView;
    private CompositeSubscription subscription;
    private List<Motto.ResultBean> list;

    @Override
    public void onCreate() {
        subscription = new CompositeSubscription();
    }

    @Override
    public void attachView(View view) {
        mottoView = (MottoView) view;
    }

    @Override
    public void onStop() {
        if (subscription != null && subscription.hasSubscriptions()) {
            subscription.unsubscribe();
        }
    }

    public void getMottoList(String apiKey, String keyword, int page, int rows) {
        subscription.add(DataManager.getInstance().
                getMottoList(apiKey, keyword, page, rows).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<Motto>() {
                    @Override
                    public void onCompleted() {
                        if (list != null && mottoView != null) {
                            mottoView.onSuccess(list);
                        }
                        onStop();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mottoView != null) {
                            mottoView.onFail(e.getMessage());
                        }
                        onStop();
                    }

                    @Override
                    public void onNext(Motto motto) {
                        list = motto.getResult();
                    }
                }));
    }
}
