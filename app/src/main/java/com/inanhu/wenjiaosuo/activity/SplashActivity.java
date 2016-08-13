package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.MD5Util;
import com.inanhu.wenjiaosuo.util.SPUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Headers;

public class SplashActivity extends BaseActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    // 初次使用标记
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(3000);
        ivSplash.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // 是否第一次使用
                isFirst = (boolean) SPUtil.get(SplashActivity.this, Constant.SPKey.FIRST_RUN, true);
                // 免登录
                String userPhone = (String) SPUtil.get(SplashActivity.this, Constant.SPKey.USERNAME, "");
                String userPwd = (String) SPUtil.get(SplashActivity.this, Constant.SPKey.PASSWORD, "");
                if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPwd)) { // 本地文件中有免登录账号的用户名和密码则去做登录
                    RequestParams params = new RequestParams(SplashActivity.this);
                    params.addFormDataPart(Constant.RequestKey.NAME, userPhone);
                    params.addFormDataPart(Constant.RequestKey.LOGIN_PASSWORD, userPwd);
                    if (isNetConnected()) {
                        HttpEngine.doPost(URLUtil.UserApi.LOGIN, params, new BaseHttpRequestCallback() {

                            @Override
                            public void onResponse(String response, Headers headers) {
                                ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                                }.getType());
                                String data = rsp.getData();
                                if (rsp.isSuccess() && !TextUtils.isEmpty(data)) {
                                    // 登录成功，保存token
//                                    ToastUtil.showToast("登录成功");
                                    GlobalValue.getInstance().saveGlobal(Constant.RequestKey.ACCESS_TOKEN, data);
                                }
                            }
                        });
                    } else {
                        ToastUtil.showToast(R.string.toast_network_unconnceted);
                    }
                }
                // 开启极光推送
                boolean isPushStopped = JPushInterface.isPushStopped(SplashActivity.this);
                if (isPushStopped) {
                    JPushInterface.resumePush(SplashActivity.this);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toNext();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    private void toNext() {
        if (isFirst) { // 第一次运行进入引导页
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        activityManagerUtil.finishActivity(this);
    }

}
