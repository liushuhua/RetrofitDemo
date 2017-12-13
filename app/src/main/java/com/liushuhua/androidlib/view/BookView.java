package com.liushuhua.androidlib.view;

import com.liushuhua.androidlib.model.Book;

/**
 * Created by liushuhua on 17-12-8.
 * Description:BookView
 */

public interface BookView extends View {
    void onSuccess(Book book);

    void onError(String error);
}
