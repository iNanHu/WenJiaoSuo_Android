package com.inanhu.wenjiaosuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.AboutUsActivity;
import com.inanhu.wenjiaosuo.activity.LoginActivity;
import com.inanhu.wenjiaosuo.activity.MyFansActivity;
import com.inanhu.wenjiaosuo.activity.ProfileCompleteOneActivity;
import com.inanhu.wenjiaosuo.activity.ShareActivity;
import com.inanhu.wenjiaosuo.activity.UserInfoDetailActivity;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.util.AccountUtil;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Headers;


/**
 * 个人中心界面
 * <p>
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends BaseFragment /*implements View.OnClickListener */ {

    public static int REQUEST_CODE_LOGIN = 0x10;
    public static int REQUEST_CODE_LOGOUT = 0x11;

    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_login_now)
    TextView tvLoginNow;
    @BindView(R.id.complete_profile_btn)
    RelativeLayout completeProfileBtn;

    // 当前登录用户
    private UserInfo userInfo;

    @OnClick(R.id.complete_profile_btn)
    public void toCompleteProfile() {
        if (AccountUtil.isLogin()) {
            startActivity(new Intent(getActivity(), ProfileCompleteOneActivity.class));
        } else {
            ToastUtil.showToast("请先登录");
        }
    }

    @OnClick(R.id.civ_avatar)
    public void toLogin() {
        if (AccountUtil.isLogin()) { // 登录用户点击跳转账户管理界面
            startActivityForResult(new Intent(getActivity(), UserInfoDetailActivity.class), REQUEST_CODE_LOGOUT);
        } else { // 未登录则跳转到登录界面
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQUEST_CODE_LOGIN);
        }
    }

    @OnClick(R.id.abouts_us_btn)
    public void toAboutUs() {
        startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    @OnClick(R.id.check_app_version)
    public void toCheckAppVersion() {
        ToastUtil.showToast("已经是最新版本啦");
    }

    @OnClick(R.id.to_share)
    public void toShare() {
        if (AccountUtil.isUserProfileComplete()) { // 登录并完善用户详细信息后方可使用该功能
            startActivity(new Intent(getActivity(), ShareActivity.class));
        } else {
            ToastUtil.showToast("登录并完善用户详细信息后方可分享注册");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("ProfileFragment", "===onCreate===");
    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (AccountUtil.isLogin()) {
            // 登录成功获取用户基本信息
            RequestParams params = new RequestParams(this);
            params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN));
            HttpEngine.doGet(URLUtil.UserApi.INFO, params, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    ApiResponse<UserInfo> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<UserInfo>>() {
                    }.getType());
                    if (rsp != null && rsp.isSuccess()) {
                        userInfo = rsp.getData();
                        if (userInfo != null) { // 保存当前用户到全局变量
                            GlobalValue.getInstance().saveGlobal(MessageFlag.CURRENT_USER_INFO, userInfo);
                            String username = userInfo.getUsername();
                            if (!TextUtils.isEmpty(username)) {
                                tvLoginNow.setText(username);
                            }
                            String inviteNumber = userInfo.getInvite_number();
                            if (!TextUtils.isEmpty(inviteNumber)) { // 有邀请码则表明用户已经完善详细资料
                                completeProfileBtn.setVisibility(View.GONE);
                            } else {
                                completeProfileBtn.setVisibility(View.VISIBLE);
                            }
                            civAvatar.setImageResource(R.mipmap.app_icon);
                        }

                    }
                }
            });
        }
    }

    @OnClick(R.id.checkbox)
    public void onClick(CheckBox checkBox) {
        if (!checkBox.isChecked()) {
            ToastUtil.showToast("您关闭了消息提醒");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN) { // 登录返回
            if (data != null) { // 返回data为空不需要处理
                String loginSuccess = data.getStringExtra(MessageFlag.LOGIN_SUCCESS);
                if ("true".equals(loginSuccess)) { // 登录成功，更新界面
                    // 取出当前登录用户对象
                    userInfo = (UserInfo) GlobalValue.getInstance().getGlobal(MessageFlag.CURRENT_USER_INFO, null);
                    if (userInfo != null) {
                        String username = userInfo.getUsername();
                        if (!TextUtils.isEmpty(username)) {
                            tvLoginNow.setText(username);
                        }
                        String inviteNumber = userInfo.getInvite_number();
                        if (!TextUtils.isEmpty(inviteNumber)) { // 有邀请码则表明用户已经完善详细资料
                            completeProfileBtn.setVisibility(View.GONE);
                        } else {
                            completeProfileBtn.setVisibility(View.VISIBLE);
                        }
                        civAvatar.setImageResource(R.mipmap.app_icon);
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_LOGOUT) { // 登出返回
            if (data != null) {
                String logoutSuccess = data.getStringExtra(MessageFlag.LOGOUT_SUCCESS);
                if ("true".equals(logoutSuccess)) { // 登出成功，更新界面
                    tvLoginNow.setText(getResources().getString(R.string.login_now));
                    completeProfileBtn.setVisibility(View.GONE);
                    civAvatar.setImageResource(R.mipmap.nologin);
                }
            }
        }
    }

    @OnClick(R.id.tv_login_now)
    public void onClick() {
        toLogin();
    }

    @OnClick({R.id.mine_member_btn, R.id.mine_fans_btn, R.id.mine_optional_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_member_btn:
                ToastUtil.showToast("去会员中心拉");
                break;
            case R.id.mine_fans_btn:
//                ToastUtil.showToast("去粉丝中心拉");
                startActivity(new Intent(getActivity(), MyFansActivity.class));
                break;
            case R.id.mine_optional_btn:
                ToastUtil.showToast("暂未开放该功能");
                break;
        }
    }
}
