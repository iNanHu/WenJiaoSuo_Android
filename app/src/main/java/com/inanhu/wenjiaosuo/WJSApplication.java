package com.inanhu.wenjiaosuo;

import android.app.Application;

import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.PicassoImageLoader;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.umeng.socialize.PlatformConfig;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;


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

        // 初始化极光推送
        initJPush();

        // 初始化友盟分享
        initShareSDK();
    }


    private void initShareSDK() {
//        PlatformConfig.setWeixin();
        PlatformConfig.setQQZone("1105530752", "NfPhWr0MKs4lrKwm");
//        PlatformConfig.setSinaWeibo();
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initGalleryFinal() {
        // 设置主题
        ThemeConfig theme = ThemeConfig.CYAN;
        // 配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(false)
                .setEnableCrop(true)
                .setEnableRotate(false)
//                .setForceCrop(true)
//                .setCropSquare(true)
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
