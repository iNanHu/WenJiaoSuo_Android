package com.inanhu.wenjiaosuo.util;

import android.text.TextUtils;

import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;

/**
 * 用户工具类
 * <p/>
 * Created by iNanHu on 2016/7/21.
 */
public class AccountUtil {

    /**
     * 用户是否已登录（用户or游客）
     *
     * @return
     */
    public static boolean isLogin() {
        String token = (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, "");
        return TextUtils.isEmpty(token) ? false : true;
    }

}
