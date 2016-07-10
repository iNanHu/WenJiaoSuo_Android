package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.MD5Util;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;
import okhttp3.Response;

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
        String userPhone = etUserPhone.getText().toString().trim();
        String userPwd = etUserPwd.getText().toString().trim();
        if (!RegexUtil.checkMobile(userPhone)) {
            ToastUtil.showToast("手机号输入有误");
            return;
        }
        if (!RegexUtil.checkPassword(userPwd)) {
            ToastUtil.showToast("密码为6-15位字母加数字");
            return;
        }
        RequestParams params = new RequestParams(this);
        params.addFormDataPart(Constant.Key.NAME, userPhone);
        params.addFormDataPart(Constant.Key.LOGIN_PASSWORD, MD5Util.getMD5String(userPwd));
        if (isNetConnected()){
            HttpEngine.doPost(URLUtil.UserApi.LOGIN, params, new BaseHttpRequestCallback(){

                @Override
                public void onResponse(Response httpResponse, String response, Headers headers) {
                    ApiResponse rsp = new Gson().fromJson(response, ApiResponse.class);
                    LogUtil.e(TAG, response);
                    LogUtil.e(TAG, rsp.isSuccess() + "/" + rsp.getData());
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
        setTopBarTitle(R.string.login);
    }

}
