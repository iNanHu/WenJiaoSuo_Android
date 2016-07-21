package com.inanhu.wenjiaosuo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.AboutUsActivity;
import com.inanhu.wenjiaosuo.activity.LoginActivity;
import com.inanhu.wenjiaosuo.activity.ProfileCompleteOneActivity;
import com.inanhu.wenjiaosuo.activity.ShareActivity;
import com.inanhu.wenjiaosuo.activity.UserInfoDetailActivity;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.util.AccountUtil;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 个人中心界面
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends BaseFragment /*implements View.OnClickListener */ {

    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_login_now)
    TextView tvLoginNow;

    @OnClick(R.id.guanzhu_teacher_btn)
    public void toGuanzhuTeacher() {
        startActivity(new Intent(getActivity(), ProfileCompleteOneActivity.class));
    }

    @OnClick(R.id.civ_avatar)
    public void toLogin() {
        if (AccountUtil.isLogin()) { // 登录用户点击跳转展示基本信息界面
            startActivity(new Intent(getActivity(), UserInfoDetailActivity.class));
        } else { // 未登录则跳转到登录界面
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), Activity.RESULT_FIRST_USER);
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
        startActivity(new Intent(getActivity(), ShareActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("ProfileFragment", "===onCreate===");
//        ToastUtil.showToast("登录状态：" + AccountUtil.isLogin());
    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
//        ImageLoader.with(R.mipmap.ic_launcher, "http://www.baidu.com/img/bd_logo1.png", civAvatar);
        LogUtil.e("ProfileFragment", "===onCreateView===");
        return view;
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
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (data != null) { // 返回data为空不需要处理
                String loginSuccess = data.getStringExtra(MessageFlag.LOGIN_SUCCESS);
                if ("true".equals(loginSuccess)) { // 登录成功
                    // 取出当前登录用户对象
                    UserInfo userInfo = (UserInfo) GlobalValue.getInstance().getGlobal(MessageFlag.CURRENT_USER_INFO);
                    if (userInfo != null) {
                        String username = userInfo.getUsername();
                        if (!TextUtils.isEmpty(username)) {
                            tvLoginNow.setText(username);
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.tv_login_now)
    public void onClick() {
        toLogin();
    }
}
