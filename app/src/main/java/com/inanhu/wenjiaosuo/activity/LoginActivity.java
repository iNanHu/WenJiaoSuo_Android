package com.inanhu.wenjiaosuo.activity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.fragment.ProfileFragment;
import com.inanhu.wenjiaosuo.util.AccountUtil;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.MD5Util;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.SPUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Jason on 2016/6/29.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;

    @OnClick(R.id.text_login_register)
    public void toRegister() {
//        ToastUtil.showToast("去注册啦");
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @OnClick(R.id.tv_forget_password)
    public void toFindPwd() {
        startActivity(new Intent(LoginActivity.this, FindPwdActivity.class));
    }

    @OnClick(R.id.btn_login)
    public void toLogin() {
        final String userPhone = etUserPhone.getText().toString().trim();
        final String userPwd = etUserPwd.getText().toString().trim();
        if (!RegexUtil.checkMobile(userPhone)) {
            ToastUtil.showToast("手机号输入有误");
            return;
        }
        if (!RegexUtil.checkPassword(userPwd)) {
            ToastUtil.showToast("密码为6-15位字母加数字");
            return;
        }
        RequestParams params = new RequestParams(this);
        params.addFormDataPart(Constant.RequestKey.NAME, userPhone);
        params.addFormDataPart(Constant.RequestKey.LOGIN_PASSWORD, MD5Util.getMD5String(userPwd));
        if (isNetConnected()) {
            HttpEngine.doPost(URLUtil.UserApi.LOGIN, params, new BaseHttpRequestCallback() {

                @Override
                public void onResponse(String response, Headers headers) {
                    ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                    }.getType());
                    LogUtil.e(TAG, rsp.isSuccess() + "/" + rsp.getData());
                    String data = rsp.getData();
                    if (rsp.isSuccess() && !TextUtils.isEmpty(data)) {
                        // 登录成功，保存token
                        GlobalValue.getInstance().saveGlobal(Constant.RequestKey.ACCESS_TOKEN, data);
                        // 登录成功保存用户名密码（MD5加密后）到本地文件，以后一般启动免登录
                        SPUtil.put(LoginActivity.this, Constant.SPKey.USERNAME, userPhone);
                        SPUtil.put(LoginActivity.this, Constant.SPKey.PASSWORD, MD5Util.getMD5String(userPwd));
                        // 登录成功获取用户基本信息
                        RequestParams params = new RequestParams(LoginActivity.this);
                        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, data);
                        HttpEngine.doGet(URLUtil.UserApi.INFO, params, new BaseHttpRequestCallback() {
                            @Override
                            public void onResponse(String response, Headers headers) {
                                ApiResponse<UserInfo> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<UserInfo>>() {
                                }.getType());
                                if (rsp != null && rsp.isSuccess()) {
                                    UserInfo userInfo = rsp.getData();
                                    if (userInfo != null) { // 保存当前用户到全局变量
                                        GlobalValue.getInstance().saveGlobal(MessageFlag.CURRENT_USER_INFO, userInfo);
                                        setResult(ProfileFragment.REQUEST_CODE_LOGIN, new Intent().putExtra(MessageFlag.LOGIN_SUCCESS, "true"));
                                        finish();
                                    }
                                }
                            }
                        });
                    } else {
                        ToastUtil.showToast("登录失败 " + data);
                    }
                }

                @Override
                public void onStart() {
                    LogUtil.e(TAG, "onStart");
                }

                @Override
                public void onFinish() {
                    LogUtil.e(TAG, "onFinish");
                }
            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.login);
    }

}
