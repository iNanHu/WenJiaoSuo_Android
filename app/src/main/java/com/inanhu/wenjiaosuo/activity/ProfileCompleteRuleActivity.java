package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 完善资料前的免责条款
 * <p/>
 * Created by Administrator on 2016/8/15.
 */
public class ProfileCompleteRuleActivity extends BaseActivity {

    @BindView(R.id.wv_profile_complete_rule)
    WebView wvProfileCompleteRule;

    private String ruleUrl = "http://wmyzt.applinzi.com/admin.php?r=page/Category/index&class_id=13";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete_rule);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.profile_complete_rule);
        showTopBarRight(false);
        wvProfileCompleteRule.loadUrl(ruleUrl);
    }

    @OnClick(R.id.btn_agree)
    public void onClick() {
        startActivity(new Intent(this, ProfileCompleteOneActivity.class));
        activityManagerUtil.finishActivity(this);
    }
}
