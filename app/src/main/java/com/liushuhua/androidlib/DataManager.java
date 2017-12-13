package com.liushuhua.androidlib;

import com.liushuhua.androidlib.model.Book;
import com.liushuhua.androidlib.model.Motto;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by liushuhua on 17-12-8.
 * Description:数据接口管理类
 */

public class DataManager {

    private static volatile DataManager dataManager;
    private Api api;

    public static DataManager getInstance() {
        if (dataManager == null) {
            synchronized (DataManager.class) {
                if (dataManager == null) {
                    dataManager = new DataManager();
                }
            }
        }
        return dataManager;
    }

    private DataManager() {
        api = RetrofitHelper.getInstance().getApi();
    }

    /**
     * 搜索书
     *
     * @param name  名字
     * @param tag   tag
     * @param start start
     * @param count count
     * @return book
     */
    public Observable<Book> searchBook(String name, String tag, int start, int count) {
        return api.searchBook(name, tag, start, count);
    }

    /**
     * 获取名人名言
     *
     * @param apiKey
     * @param keyword
     * @param page
     * @param rows
     * @return
     */
    public Observable<Motto> getMottoList(String apiKey, String keyword, int page, int rows) {
        return api.getMottoList(apiKey, keyword, page, rows);
    }

    /**
     * 下载文件
     *
     * @param path url
     * @return RequestBody
     */
    public Observable<ResponseBody> downloadFile(String path) {
        return api.downloadFile(path);
    }

    /**
     * 上传文件
     *
     * @param description 描述
     * @param params      参数
     * @return result
     */
    public Observable<ResponseBody> uploadFile(String description, Map<String, RequestBody> params) {
        return api.uploadFile(description, params);
    }
}
