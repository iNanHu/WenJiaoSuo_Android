package com.inanhu.wenjiaosuo.util;

import android.content.Context;

import cn.finalteam.okhttpfinal.HttpCycleContext;

/**
 * Created by iNanHu on 2016/7/10.
 */
public interface MyHttpCycleContext extends HttpCycleContext {
    Context getContext();
}
