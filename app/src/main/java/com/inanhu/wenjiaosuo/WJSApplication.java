package com.inanhu.wenjiaosuo;

import android.app.Application;

import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by Jason on 2016/6/27.
 */
public class WJSApplication extends Application {

    private static WJSApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    public void initApplication() {
        // 图片加载库初始化
        ImageLoader.init(this);

        // 日志开关
        LogUtil.setPrintable(true);

        // Toast工具初始化
        ToastUtil.init(this);

        // 初始化网络请求框架
        HttpEngine.init();
    }

    public static WJSApplication getInstance() {
        return application;
    }
}
