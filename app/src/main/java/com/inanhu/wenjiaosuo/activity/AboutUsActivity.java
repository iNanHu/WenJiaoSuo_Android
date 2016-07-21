package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.VersionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 * <p/>
 * Created by Jason on 2016/7/12.
 */
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.about_us);
        // 设置版本号
        tvAppVersion.setText(getResources().getString(R.string.app_name) + VersionUtil.getVersionName(this) + "版");
    }

    @OnClick({R.id.rl_app_intro, R.id.rl_app_cooperate, R.id.rl_app_help, R.id.rl_app_contact, R.id.rl_app_rule})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(AboutUsActivity.this, WebviewActivity.class);
        String title = "";
        String url = "";
        switch (view.getId()) {
            case R.id.rl_app_intro:
                title = getResources().getString(R.string.about_us_intro);
                url = "http://wmyzt.applinzi.com/admin.php?r=page/Category/index&class_id=5";
                break;
            case R.id.rl_app_cooperate:
                title = getResources().getString(R.string.about_us_cooperate);
                url = "http://wmyzt.applinzi.com/admin.php?r=page/Category/index&class_id=7";
                break;
            case R.id.rl_app_help:
                title = getResources().getString(R.string.about_us_help);
                url = "http://wmyzt.applinzi.com/admin.php?r=page/Category/index&class_id=8";
                break;
            case R.id.rl_app_contact:
                title = getResources().getString(R.string.about_us_contact);
                url = "http://wmyzt.applinzi.com/admin.php?r=page/Category/index&class_id=9";
                break;
            case R.id.rl_app_rule:
                title = getResources().getString(R.string.about_us_rule);
                url = "http://wmyzt.applinzi.com/admin.php?r=page/Category/index&class_id=10";
                break;
        }
        intent.putExtra(MessageFlag.WEBVIEW_TOPBAR_TITLE, title);
        intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, url);
        startActivity(intent);
    }
}
