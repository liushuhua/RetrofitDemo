package com.liushuhua.androidlib;


import com.liushuhua.androidlib.model.Book;
import com.liushuhua.androidlib.model.Motto;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by liushuhua on 17-12-8.
 * Description:项目Api
 */

public interface Api {
    @GET("book/search")
    Observable<Book> searchBook(@Query("q") String name, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @Streaming
    @GET()
    Observable<ResponseBody> downloadFile(@Url String path);

    @GET("avatardata/mingrenmingyan/lookup")
    Observable<Motto> getMottoList(@Header("apiKey") String apiKey,
                                    @Query("keyword") String keyword,
                                    @Query("page") int page,
                                    @Query("rows") int rows);

}
