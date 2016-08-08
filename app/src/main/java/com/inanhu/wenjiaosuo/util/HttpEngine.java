package com.inanhu.wenjiaosuo.util;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 封装网络请求框架
 * <p/>
 * Created by Jason on 2016/6/30.
 */
public class HttpEngine {

    public static void init() {
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());
    }

    /**
     * Get请求（无参数）
     *
     * @param url      请求地址
     * @param callback 异步回调
     */
    public static void doGet(String url, BaseHttpRequestCallback callback) {
        doGet(url, null, callback);
    }

    /**
     * Get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 异步回调
     */
    public static void doGet(String url, RequestParams params, BaseHttpRequestCallback callback) {
        if (params == null) {
            HttpRequest.get(url, callback);
        } else {
            HttpRequest.get(url, params, callback);
        }
    }

    /**
     * Post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void doPost(String url, RequestParams params, BaseHttpRequestCallback callback) {
        HttpRequest.post(url, params, callback);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param target
     * @param callback
     */
    public static void download(String url, File target, FileDownloadCallback callback) {
        HttpRequest.download(url, target, callback);
    }

}
