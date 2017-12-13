package com.liushuhua.androidlib;


import com.liushuhua.androidlib.model.Book;
import com.liushuhua.androidlib.model.Motto;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by liushuhua on 17-12-8.
 * Description:项目Api，所有的接口信息均来源网络
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

    @Multipart
    @POST("update.php")
    Observable<ResponseBody> uploadFile(@Part("filedes") String des, @PartMap Map<String, RequestBody> params);

}
