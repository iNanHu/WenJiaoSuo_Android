package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 文民社群界面
 *
 * Created by Jason on 2016/8/7.
 */
public class WenMinSheQunActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmsq);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.wmsq);
    }
}
