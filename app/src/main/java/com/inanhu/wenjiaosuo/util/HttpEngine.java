package com.inanhu.wenjiaosuo.util;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;


import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 封装网络请求框架
 * <p/>
 * Created by Jason on 2016/6/30.
 */
public class HttpEngine {

    public static void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * Get请求（无参数）
     *
     * @param url      请求地址
     * @param callback 异步回调
     */
    public static void doGet(String url, Callback callback) {
        doGet(url, null, callback);
    }

    /**
     * Get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 异步回调
     */
    public static void doGet(String url, Map<String, String> params, Callback callback) {
//        OkHttpUtils.get().url(url).build().execute(callback);
        GetBuilder builder = OkHttpUtils.get().url(url);
        if (params != null) {
            builder.params(params);
        }
        builder.build().execute(callback);
    }

    /**
     * Post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void doPost(String url, Map<String, String> params, Callback callback) {
        doPost(url, params, null, callback);
    }

    public static void doPost(String url, Map<String, String> params, Map<String, File> files, Callback callback) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        if (params != null){
            builder.params(params);
        }
        if (files != null){
            for (String key : files.keySet()){
                builder.addFile(key, "", files.get(key));
            }
        }
        builder.build().execute(callback);
    }
}
