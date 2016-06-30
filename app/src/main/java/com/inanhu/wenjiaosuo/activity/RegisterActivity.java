package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.btn_register)
    public void toRegister() {
        String userPhone = etUserPhone.getText().toString().trim();
        String userPwd = etUserPwd.getText().toString().trim();
        String userPwdCheck = etUserPwdCheck.getText().toString().trim();
        String userEmail = etUserEmail.getText().toString().trim();
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
