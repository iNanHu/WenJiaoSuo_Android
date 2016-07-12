package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.MessageFlag;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jason on 2016/7/12.
 */
public class AboutUsWebviewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_about_us);
        ButterKnife.bind(this);
        // 显示返回图标
        showWebviewTopBarBack();
        intent = getIntent();
        setTitle();
        loadWebview();
    }

    private void loadWebview() {
        if (intent != null) {
            String url = intent.getStringExtra(MessageFlag.WEBVIEW_LOAD_URL);
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

    private void setTitle() {
        if (intent != null) {
            String title = intent.getStringExtra(MessageFlag.WEBVIEW_TOPBAR_TITLE);
            if (title != null) {
                setWebviewTopBarTitle(title);
            }
        }
    }
}
