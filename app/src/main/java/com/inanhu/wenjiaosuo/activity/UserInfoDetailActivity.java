package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 用户详细信息页面（在此界面可以登出）
 * <p/>
 * Created by iNanHu on 2016/7/22.
 */
public class UserInfoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.about_us);
    }
}
