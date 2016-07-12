package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

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

    private Intent intent;
    // Webview入口地址
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        intent = getIntent();
        setTopBar();
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
                        showProgressDialog("");
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
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

    @OnClick({R.id.toolbar_back_btn, R.id.tv_share_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_btn:
                finish();
                break;
            case R.id.tv_share_btn:
                ToastUtil.showToast("分享啦：" + url);
                break;
        }
    }
}
