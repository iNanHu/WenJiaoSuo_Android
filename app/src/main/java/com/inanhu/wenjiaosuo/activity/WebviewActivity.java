package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 2016/7/12.
 */
public class WebviewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.activity_title)
    TextView activityTitle;
    @BindView(R.id.tv_share_btn)
    ImageView tvShareBtn;
    @BindView(R.id.webview_container)
    LinearLayout webviewContainer;

    private Intent intent;
    // Webview入口地址
    private String url;

    // 软文标题（分享用）
    private String title = "";
    // 当前用户
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        intent = getIntent();
        setTopBar();
        // 获取当前用户
        userInfo = (UserInfo) GlobalValue.getInstance().getGlobal(MessageFlag.CURRENT_USER_INFO, null);
        loadWebview();
    }

    private void loadWebview() {
        if (intent != null) {
            url = intent.getStringExtra(MessageFlag.WEBVIEW_LOAD_URL);
            if (url != null) {
                webView.loadUrl(url);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        showProgressDialog("", true);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        title = view.getTitle();
                        closeProgressDialog();
                    }

                });
            }
        }
    }

    private void setTopBar() {
        if (intent != null) {
            String title = intent.getStringExtra(MessageFlag.WEBVIEW_TOPBAR_TITLE);
            if (title != null) {
                activityTitle.setText(title);
            }
            boolean canShare = intent.getBooleanExtra(MessageFlag.IS_SHOW_TOPBAR_SHARE, false);
            if (canShare) {
                tvShareBtn.setVisibility(View.VISIBLE);
            }
        }

    }

    UMImage image = new UMImage(this, R.mipmap.app_icon);

    @OnClick({R.id.toolbar_back_btn, R.id.tv_share_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_btn:
                activityManagerUtil.finishActivity(WebviewActivity.this);
                break;
            case R.id.tv_share_btn:
//                http://wmyzt.applinzi.com/article/5.html
                if (userInfo != null) {
                    String inviteNumber = userInfo.getInvite_number();
                    if (!TextUtils.isEmpty(inviteNumber)) { //有邀请码的用户分享的软文携带邀请码
                        url = url + "?invite=" + inviteNumber;
                        LogUtil.e(TAG, url);
                    }
                }
                new ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                        .withTitle(title)
                        .withText(title)
                        .withTargetUrl(url)
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .open();

                break;
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
        LogUtil.e(TAG, "==onDestroy===");
        // 释放Webview资源
        if (webView != null) {
            webviewContainer.removeView(webView);
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
        url = null;
    }
}
