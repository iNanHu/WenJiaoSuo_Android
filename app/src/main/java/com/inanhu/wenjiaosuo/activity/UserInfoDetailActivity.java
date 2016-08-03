package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
import com.inanhu.wenjiaosuo.util.SPUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Headers;

/**
 * 用户详细信息页面（在此界面可以登出）
 * <p/>
 * Created by iNanHu on 2016/7/22.
 */
public class UserInfoDetailActivity extends BaseActivity {

    @BindView(R.id.head_nickname_tv)
    TextView headNicknameTv;
    @BindView(R.id.head_account_tv)
    TextView headAccountTv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.binding_phone_tv)
    TextView bindingPhoneTv;


    // 当前登录用户
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.account_manage);
        initData();
    }

    private void initData() {
        userInfo = (UserInfo) GlobalValue.getInstance().getGlobal(MessageFlag.CURRENT_USER_INFO, null);
        if (userInfo != null) {
            // 隐藏昵称
            headNicknameTv.setVisibility(View.INVISIBLE);
            headAccountTv.setText(TextUtils.isEmpty(userInfo.getUsername()) ? "" : userInfo.getUsername());
            String sex = userInfo.getSex();
            if (!TextUtils.isEmpty(sex)) {
                if ("1".equals(sex)) {
                    sexTv.setText("男");
                } else if ("2".equals(sex)) {
                    sexTv.setText("女");
                }
            }
            bindingPhoneTv.setText(TextUtils.isEmpty(userInfo.getUsername()) ? "" : userInfo.getUsername());
        }
    }

    @OnClick(R.id.logout_btn)
    public void onClick() {
        // 账户登出
        RequestParams params = new RequestParams(this);
        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
        HttpEngine.doPost(URLUtil.UserApi.LOGOUT, params, new BaseHttpRequestCallback() {
            @Override
            public void onResponse(String response, Headers headers) {
                ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                }.getType());
                String data = rsp.getData();
                if (rsp.isSuccess()) { // 登出成功
                    // 清除当前用户的token
                    GlobalValue.getInstance().deleteGlobal(Constant.RequestKey.ACCESS_TOKEN);
                    // 清除当前用户对象
                    GlobalValue.getInstance().deleteGlobal(MessageFlag.CURRENT_USER_INFO);
                    // 清除当前文件中的免登录账号和密码
                    SPUtil.remove(UserInfoDetailActivity.this, Constant.SPKey.USERNAME);
                    SPUtil.remove(UserInfoDetailActivity.this, Constant.SPKey.PASSWORD);
                    // 登出后清除之前的别名
                    JPushInterface.setAlias(UserInfoDetailActivity.this, "", new TagAliasCallback() {
                        @Override
                        public void gotResult(int code, String alias, Set<String> tags) {
                            switch (code) {
                                case 0: // 设置成功
                                    ToastUtil.showToast("取消别名成功");
                                    break;

                                case 6002: // 设置超时
                                    ToastUtil.showToast("取消别名超时");
                                    break;

                                default:
                                    ToastUtil.showToast("取消别名出错-" + code);
                            }
                        }
                    });
                    // 返回
                    setResult(ProfileFragment.REQUEST_CODE_LOGOUT, new Intent().putExtra(MessageFlag.LOGOUT_SUCCESS, "true"));
                    finish();
                } else {
                    ToastUtil.showToast("登出失败 " + data);
                }
            }
        });
    }
}
