package com.inanhu.wenjiaosuo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.WJSApplication;

import butterknife.OnClick;

/**
 * Created by iNanHu on 2016/6/27.
 */
public class BaseActivity extends Activity {
    protected String TAG;
    protected WJSApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        application = WJSApplication.getInstance();

        super.onCreate(savedInstanceState);
    }

    /**
     * 标题栏是否显示返回键
     *
     * @param isNeedToShow
     */
    protected void showTopBarBack(boolean isNeedToShow) {
        TextView tvTopBarBack = (TextView) findViewById(R.id.id_topbar_back);
        if (isNeedToShow) {
            tvTopBarBack.setVisibility(View.VISIBLE);
            tvTopBarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            tvTopBarBack.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 是否显示标题栏
     * @param isNeedToShow
     */
    protected void showTopBar(boolean isNeedToShow) {
        RelativeLayout rlTopBar = (RelativeLayout) findViewById(R.id.id_topbar);
        if (isNeedToShow) {
            rlTopBar.setVisibility(View.VISIBLE);
        } else {
            rlTopBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置标题栏标题
     *
     * @param resId
     */
    protected void setTopBarTitle(int resId) {
        TextView tvTopBarTitle = (TextView) findViewById(R.id.id_topbar_title);
        tvTopBarTitle.setText(resId);
    }

}
