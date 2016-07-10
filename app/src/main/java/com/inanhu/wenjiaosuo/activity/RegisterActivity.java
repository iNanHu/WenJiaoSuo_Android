package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.util.MD5Util;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

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
//        ToastUtil.showToast("注册成功：" + userPhone + "/" + userPwd + "/" + userEmail);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.Key.USERNAME, userPhone);
        params.put(Constant.Key.REGISTER_PASSWORD, MD5Util.getMD5String(userPwd));
        params.put(Constant.Key.EMAIL, userEmail);
        if (!TextUtils.isEmpty(userInvite)){
            params.put(Constant.Key.INVITE, userInvite);
        }
//        if (isNetConnected()){
//            HttpEngine.doPost(URLUtil.UserApi.REGISTER, params, new StringCallback() {
//                @Override
//                public void onError(Call call, Exception e, int id) {
//
//                }
//
//                @Override
//                public void onResponse(String response, int id) {
//                    ApiResponse rsp = new Gson().fromJson(response, ApiResponse.class);
//                    LogUtil.e(TAG, response);
//                    LogUtil.e(TAG, rsp.isSuccess() + "/" + rsp.getData());
//                }
//            });
//        } else {
//            ToastUtil.showToast(R.string.toast_network_unconnceted);
//        }
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
