package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我要分享界面
 * <p/>
 * Created by Jason on 2016/7/19.
 */
public class ShareActivity extends BaseActivity {

    UMImage image = new UMImage(this, "http://www.umeng.com/images/pic/social/integrated_3.png");
    String url = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.social_share);
    }

    /**
     * 分享到微信
     */
    @OnClick(R.id.wexinChat)
    public void shareToWechat() {
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener)
                .withTitle("Title")
                .withText("Text")
                .withTargetUrl(url)
                .withMedia(image)
                .share();
    }

    /**
     * 分享到微信朋友圈
     */
    @OnClick(R.id.wexinCircle)
    public void shareToWXCircle() {
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener)
                .withTitle("Title")
                .withText("Text")
                .withTargetUrl(url)
                .withMedia(image)
                .share();
    }

    /**
     * 分享到QQ好友
     */
    @OnClick(R.id.qqFrinds)
    public void shareToQQ() {
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.QQ)
                .setCallback(umShareListener)
                .withTitle("Title")
                .withText("Text")
                .withTargetUrl(url)
                .withMedia(image)
                .share();
    }

    /**
     * 分享到QQ空间
     */
    @OnClick(R.id.qqZone)
    public void shareToQZone() {
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.QZONE)
                .setCallback(umShareListener)
                .withTitle("Title")
                .withText("Text")
                .withTargetUrl(url)
                .withMedia(image)
                .share();
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
}
