package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
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
 * 找回密码界面
 * <p/>
 * Created by iNanHu on 2016/7/3.
 */
public class FindPwdActivity extends BaseActivity {

    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_email)
    EditText etUserEmail;

    @OnClick(R.id.btn_find_pwd)
    public void toFindPwd() {
        String userPhone = etUserPhone.getText().toString().trim();
        String userEmail = etUserEmail.getText().toString().trim();
        if (!RegexUtil.checkMobile(userPhone)) {
            ToastUtil.showToast("手机号输入有误");
            return;
        }
        if (!RegexUtil.checkEmail(userEmail)) {
            ToastUtil.showToast("邮箱输入有误");
            return;
        }
        RequestParams params = new RequestParams(this);
        params.addFormDataPart(Constant.RequestKey.EMAIL, userEmail);
        if (isNetConnected()) {
            HttpEngine.doPost(URLUtil.UserApi.RESET_PASS, params, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    LogUtil.e(TAG, response);
                    ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                    }.getType());
                    String data = rsp.getData();
                    if (rsp.isSuccess()) {
                        ToastUtil.showToast("请登录注册邮箱重置登录密码");
                        activityManagerUtil.finishActivity(FindPwdActivity.this);
                    } else {
                        ToastUtil.showToast("找回密码失败 " + data);
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
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.find_pwd);
    }
}
