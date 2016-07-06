package com.inanhu.wenjiaosuo;

import android.app.Application;

import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.PicassoImageLoader;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
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

    private void initApplication() {
        // 图片加载库初始化
        ImageLoader.init(this);

        // 日志开关
        LogUtil.setPrintable(true);

        // Toast工具初始化
        ToastUtil.init(this);

        // 初始化网络请求框架
        HttpEngine.init();

        // 初始化图片选择器框架
        initGalleryFinal();
    }

    private void initGalleryFinal() {
        // 设置主题
        ThemeConfig theme = ThemeConfig.CYAN;
        // 配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(false)
                .setEnableCrop(false)
                .setEnableRotate(false)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        // 配置imageloader
        cn.finalteam.galleryfinal.ImageLoader imageLoader = new PicassoImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageLoader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static WJSApplication getInstance() {
        return application;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
