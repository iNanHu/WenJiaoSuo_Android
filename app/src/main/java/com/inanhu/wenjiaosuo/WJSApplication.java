package com.inanhu.wenjiaosuo;

import android.app.Application;

import com.inanhu.wenjiaosuo.util.imageloader.ImageLoader;
import com.inanhu.wenjiaosuo.util.log.Log;

/**
 * Created by Jason on 2016/6/27.
 */
public class WJSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    public void initApplication() {
        // 图片加载库初始化
        ImageLoader.init(this);

        // 日志开关
        Log.setPrintable(true);

    }
}
