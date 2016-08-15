package com.inanhu.wenjiaosuo.fragment;

import android.content.Intent;
import android.net.Uri;
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
import com.inanhu.wenjiaosuo.activity.MemberCenterActivity;
import com.inanhu.wenjiaosuo.activity.MyFansActivity;
import com.inanhu.wenjiaosuo.activity.ProfileCompleteRuleActivity;
import com.inanhu.wenjiaosuo.activity.PromotePosterActivity;
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
import com.inanhu.wenjiaosuo.util.ImageLoader;
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
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends BaseFragment /*implements View.OnClickListener */ {

    public static int REQUEST_CODE_LOGIN = 0x10;
    public static int REQUEST_CODE_USER_MANAGE = 0x11;

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
        if (AccountUtil.isUserProfileComplete()) { // 当前用户已经完善信息
            ToastUtil.showToast("您已经完善信息啦");
        } else {
            if (AccountUtil.isLogin()) {
                startActivity(new Intent(getActivity(), ProfileCompleteRuleActivity.class));
            } else {
                ToastUtil.showToast("请先登录");
            }
        }
    }

    @OnClick(R.id.civ_avatar)
    public void toLogin() {
        if (AccountUtil.isLogin()) { // 登录用户点击跳转账户管理界面
            startActivityForResult(new Intent(getActivity(), UserInfoDetailActivity.class), REQUEST_CODE_USER_MANAGE);
        } else { // 未登录则跳转到登录界面
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQUEST_CODE_LOGIN);
        }
    }

    @OnClick(R.id.feed_back_btn)
    public void toFeedBack() {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:wenlongservice@126.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
//        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
        startActivity(data);
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
            ToastUtil.showToast("高级文民才能分享注册哦");
        }
    }

    @OnClick(R.id.to_promote)
    public void toPromote() {
        if (AccountUtil.isUserProfileComplete()) { // 登录并完善用户详细信息后方可使用该功能
            if (TextUtils.isEmpty(userInfo.getInvite_number())) { // 重新获取当前登录用户
                userInfo = (UserInfo) GlobalValue.getInstance().getGlobal(MessageFlag.CURRENT_USER_INFO, null);
            }
            String url = URLUtil.UserApi.GET_POSTER + userInfo.getInvite_number();
            HttpEngine.doGet(url, new BaseHttpRequestCallback() {

                @Override
                public void onStart() {
                    showProgressDialog("推广海报生成中...请稍后");
                }

                @Override
                public void onResponse(String response, Headers headers) {
                    closeProgressDialog();
                    LogUtil.e(TAG, response);
                    ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                    }.getType());
                    String data = rsp.getData();
                    if (rsp.isSuccess() && !TextUtils.isEmpty(data)) { // 生成海报成功
                        Intent intent = new Intent(getActivity(), PromotePosterActivity.class);
                        intent.putExtra(MessageFlag.POSTER_URL, data);
                        startActivity(intent);
                    } else { // 生成海报失败
                        ToastUtil.showToast("生成推广海报失败");
                    }
                }

                @Override
                public void onFinish() {
                    closeProgressDialog();
                }
            });
        } else {
            ToastUtil.showToast("高级文民才能生成推广海报哦");
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
        getUserInfo();
        return view;
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
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
                            String avatar = userInfo.getAvatar();
                            if (!TextUtils.isEmpty(avatar)) {
                                ImageLoader.with(R.mipmap.nologin, avatar, civAvatar);
                            }
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
                        String avatar = userInfo.getAvatar();
                        if (!TextUtils.isEmpty(avatar)) {
                            ImageLoader.with(R.mipmap.nologin, avatar, civAvatar);
                        }
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_USER_MANAGE) { // 登出返回
            if (data != null) {
                String logoutSuccess = data.getStringExtra(MessageFlag.LOGOUT_SUCCESS);
                if (!TextUtils.isEmpty(logoutSuccess) && "true".equals(logoutSuccess)) { // 登出成功，更新界面
                    tvLoginNow.setText(getResources().getString(R.string.login_now));
                    completeProfileBtn.setVisibility(View.GONE);
                    civAvatar.setImageResource(R.mipmap.nologin);
                }
                String changeAvatarSuccess = data.getStringExtra(MessageFlag.CHANGE_AVATAR_SUCCESS);
                if (!TextUtils.isEmpty(changeAvatarSuccess) && "true".equals(changeAvatarSuccess)) { // 修改头像成功，更新头像
                    getUserInfo();
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
                if (AccountUtil.isLogin()) {
                    // 获取最新用户信息
                    RequestParams params = new RequestParams(this);
                    params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
                    HttpEngine.doGet(URLUtil.UserApi.INFO, params, new BaseHttpRequestCallback() {
                        @Override
                        public void onResponse(String response, Headers headers) {
                            LogUtil.e(TAG, response);
                            ApiResponse<UserInfo> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<UserInfo>>() {
                            }.getType());
                            if (rsp != null && rsp.isSuccess()) {
                                userInfo = rsp.getData();
                                if (userInfo != null) { // 保存当前用户到全局变量
                                    GlobalValue.getInstance().saveGlobal(MessageFlag.CURRENT_USER_INFO, userInfo);
                                    startActivity(new Intent(getActivity(), MemberCenterActivity.class));
                                }
                            }
                        }
                    });
                } else {
                    ToastUtil.showToast("请登录后查看");
                }
                break;
            case R.id.mine_fans_btn:
                if (AccountUtil.isLogin()) {
                    startActivity(new Intent(getActivity(), MyFansActivity.class));
                } else {
                    ToastUtil.showToast("请登录后查看");
                }
                break;
            case R.id.mine_optional_btn:
                ToastUtil.showToast("暂未开放该功能");
                break;
        }
    }
}
