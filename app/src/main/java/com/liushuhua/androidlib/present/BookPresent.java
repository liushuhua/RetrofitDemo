package com.liushuhua.androidlib.present;

import com.liushuhua.androidlib.DataManager;
import com.liushuhua.androidlib.model.Book;
import com.liushuhua.androidlib.view.BookView;
import com.liushuhua.androidlib.view.View;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liushuhua on 17-12-8.
 * Description:
 */

public class BookPresent extends BasePresent {

    private BookView bookView;
    private CompositeSubscription subscription;
    private Book mBook;

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

    @Override
    public void attachView(View view) {
        bookView = (BookView) view;
    }


    public void searchBook(String name, String tag, int start, int count) {
        subscription.add(DataManager.getInstance().
                searchBook(name, tag, start, count).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        if (mBook != null) {
                            bookView.onSuccess(mBook);
                        }
                        onStop();
                    }

                    @Override
                    public void onError(Throwable e) {
                        bookView.onError(e.getMessage());
                        onStop();
                    }

                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                    }
                }));
    }
}
