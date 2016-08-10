package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Jason on 2016/6/29.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.et_user_pwd_check)
    EditText etUserPwdCheck;
    @BindView(R.id.et_user_email)
    EditText etUserEmail;
    @BindView(R.id.et_user_invite)
    EditText etUserInvite;

    @OnClick(R.id.btn_register)
    public void toRegister() {
        String userPhone = etUserPhone.getText().toString().trim();
        String userPwd = etUserPwd.getText().toString().trim();
        String userPwdCheck = etUserPwdCheck.getText().toString().trim();
        String userEmail = etUserEmail.getText().toString().trim();
        String userInvite = etUserInvite.getText().toString().trim();
        if (!RegexUtil.checkMobile(userPhone)) {
            ToastUtil.showToast("手机号输入有误");
            return;
        }
        if (!RegexUtil.checkPassword(userPwd)) {
            ToastUtil.showToast("密码为6-15位字母加数字");
            return;
        }
        if (!userPwd.equals(userPwdCheck)) {
            ToastUtil.showToast("两次输入密码不一致");
            return;
        }
        if (!RegexUtil.checkEmail(userEmail)) {
            ToastUtil.showToast("邮箱输入有误");
            return;
        }
        RequestParams params = new RequestParams(this);
        params.addFormDataPart(Constant.RequestKey.USERNAME, userPhone);
        params.addFormDataPart(Constant.RequestKey.REGISTER_PASSWORD, MD5Util.getMD5String(userPwd));
        params.addFormDataPart(Constant.RequestKey.EMAIL, userEmail);
        if (!TextUtils.isEmpty(userInvite)) {
            params.addFormDataPart(Constant.RequestKey.INVITE, userInvite);
        }
        if (isNetConnected()) {
            HttpEngine.doPost(URLUtil.UserApi.REGISTER, params, new BaseHttpRequestCallback() {

                @Override
                public void onStart() {
                    LogUtil.e(TAG, "onStart");
                }

                @Override
                public void onFinish() {
                    LogUtil.e(TAG, "onFinish");
                }

                @Override
                public void onResponse(String response, Headers headers) {
                    LogUtil.e(TAG, response);
                    ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                    }.getType());
                    String data = rsp.getData();
                    if (rsp.isSuccess()) { // 注册成功，返回登录界面
                        ToastUtil.showToast(R.string.register_success);
                        activityManagerUtil.finishActivity(RegisterActivity.this);
                    } else {
                        ToastUtil.showToast("注册失败 " + data);
                    }
                }
            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.register_);
    }
}
