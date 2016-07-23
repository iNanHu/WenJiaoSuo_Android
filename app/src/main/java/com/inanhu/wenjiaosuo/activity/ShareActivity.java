package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 我要分享界面
 * <p/>
 * Created by Jason on 2016/7/19.
 */
public class ShareActivity extends BaseActivity {

    UMImage image = new UMImage(this, R.mipmap.app_icon);
    String url = URLUtil.SHARE_REGISTER;

    // 当前登录用户
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.social_share);
        // 获取当前登录用户
        userInfo = (UserInfo) GlobalValue.getInstance().getGlobal(MessageFlag.CURRENT_USER_INFO, null);
        // 拼接分享注册url
        if (userInfo != null) {
            url += userInfo.getInvite_number();
        }
//        generateQR();
    }

    /**
     * 获取用户推荐注册二维码
     */
    private void generateQR() {
        if (userInfo != null) {
            RequestParams params = new RequestParams(this);
            params.addFormDataPart(Constant.RequestKey.INVITE, userInfo.getInvite_number());
            HttpEngine.doPost(URLUtil.UserApi.GETQR, params, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    LogUtil.e(TAG, response);
                }
            });
        }
    }

    /**
     * 分享到微信
     */
    @OnClick(R.id.wexinChat)
    public void shareToWechat() {
        if (userInfo != null) {
            new ShareAction(this)
                    .setPlatform(SHARE_MEDIA.WEIXIN)
                    .setCallback(umShareListener)
                    .withTitle(getResources().getString(R.string.app_name))
                    .withText("分享注册赢福利啦")
                    .withTargetUrl(url)
                    .withMedia(image)
                    .share();
        } else {
            ToastUtil.showToast("分享失败");
        }
    }

    /**
     * 分享到微信朋友圈
     */
    @OnClick(R.id.wexinCircle)
    public void shareToWXCircle() {
        if (userInfo != null) {
            new ShareAction(this)
                    .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(umShareListener)
                    .withTitle(getResources().getString(R.string.app_name))
                    .withText("分享注册赢福利啦")
                    .withTargetUrl(url)
                    .withMedia(image)
                    .share();
        } else {
            ToastUtil.showToast("分享失败");
        }
    }

    /**
     * 分享到QQ好友
     */
    @OnClick(R.id.qqFrinds)
    public void shareToQQ() {
        if (userInfo != null) {
            new ShareAction(this)
                    .setPlatform(SHARE_MEDIA.QQ)
                    .setCallback(umShareListener)
                    .withTitle(getResources().getString(R.string.app_name))
                    .withText("分享注册赢福利啦")
                    .withTargetUrl(url)
                    .withMedia(image)
                    .share();
        } else {
            ToastUtil.showToast("分享失败");
        }
    }

    /**
     * 分享到QQ空间
     */
    @OnClick(R.id.qqZone)
    public void shareToQZone() {
        if (userInfo != null) {
            new ShareAction(this)
                    .setPlatform(SHARE_MEDIA.QZONE)
                    .setCallback(umShareListener)
                    .withTitle(getResources().getString(R.string.app_name))
                    .withText("分享注册赢福利啦")
                    .withTargetUrl(url)
                    .withMedia(image)
                    .share();
        } else {
            ToastUtil.showToast("分享失败");
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showToast("分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast("分享失败啦");
            if (t != null) {
                LogUtil.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast("分享取消了");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        LogUtil.d("result", "onActivityResult");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        url = null;
    }
}
