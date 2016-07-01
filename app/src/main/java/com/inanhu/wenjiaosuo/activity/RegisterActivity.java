package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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
        if (!RegexUtil.checkMobile(userPhone)){
            ToastUtil.showToast("手机号输入有误");
            return;
        }
        if (!RegexUtil.checkPassword(userPwd)){
            ToastUtil.showToast("密码为6-15位字母加数字");
            return;
        }
        if (!userPwd.equals(userPwdCheck)){
            ToastUtil.showToast("两次输入密码不一致");
            return;
        }
        if (!RegexUtil.checkEmail(userEmail)){
            ToastUtil.showToast("邮箱输入有误");
            return;
        }
        ToastUtil.showToast("注册成功：" + userPhone + "/" + userPwd + "/" + userEmail);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.Key.USERNAME, userPhone);
        params.put(Constant.Key.PASSWORD, userPwd);
        params.put(Constant.Key.EMAIL, userEmail);
        params.put(Constant.Key.INVITE, userInvite);
        HttpEngine.doPost(URLUtil.UserApi.REGISTER, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.register_);
    }
}
