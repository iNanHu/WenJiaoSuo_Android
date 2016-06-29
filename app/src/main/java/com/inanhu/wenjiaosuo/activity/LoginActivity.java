package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.util.MD5Util;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        ToastUtil.showToast("去找密码啦");
    }

    @OnClick(R.id.btn_login)
    public void toLogin() {
//        ToastUtil.showToast("登录成功");
        String userPhone = etUserPhone.getText().toString().trim();
        String userPwd = etUserPwd.getText().toString().trim();
        if (!RegexUtil.checkMobile(userPhone)){
            ToastUtil.showToast("手机号输入有误");
            return;
        }
        if (!RegexUtil.checkPassword(userPwd)){
            ToastUtil.showToast("密码为6-15位字母加数字");
            return;
        }
        ToastUtil.showToast(MD5Util.getMD5String("abc123"));
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
