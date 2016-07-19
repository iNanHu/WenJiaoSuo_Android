package com.inanhu.wenjiaosuo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.WJSApplication;
import com.inanhu.wenjiaosuo.util.ActivityManagerUtil;
import com.inanhu.wenjiaosuo.util.MyHttpCycleContext;
import com.inanhu.wenjiaosuo.util.NetUtil;
import com.inanhu.wenjiaosuo.widget.customprogressdialog.CustomProgress;

import cn.finalteam.okhttpfinal.HttpTaskHandler;

/**
 * Created by iNanHu on 2016/6/27.
 */
public class BaseActivity extends AppCompatActivity implements MyHttpCycleContext {
    protected String TAG;
    protected CustomProgress dialog;
    protected WJSApplication application;
    public ActivityManagerUtil activityManagerUtil;

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        application = WJSApplication.getInstance();

        super.onCreate(savedInstanceState);
        activityManagerUtil = ActivityManagerUtil.getInstance();
        activityManagerUtil.pushOneActivity(this);
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
     *
     * @param isNeedToShow
     */
    protected void showTopBar(boolean isNeedToShow) {
        RelativeLayout rlTopBar = (RelativeLayout) findViewById(R.id.id_topbar);
        if (isNeedToShow) {
            rlTopBar.setVisibility(View.VISIBLE);
        } else {
            rlTopBar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏标题
     *
     * @param title
     */
    protected void setTopBarTitle(String title) {
        ((TextView) findViewById(R.id.id_topbar_title)).setText(title);
    }

    /**
     * 设置标题栏标题
     *
     * @param resId
     */
    protected void setTopBarTitle(int resId) {
        ((TextView) findViewById(R.id.id_topbar_title)).setText(resId);
    }


    /**
     * 判断网络是否连接
     *
     * @return
     */
    protected boolean isNetConnected() {
        return NetUtil.isConnected(this);
    }

    /**
     * 判断是否为WiFi连接
     *
     * @return
     */
    protected boolean isNetWifi() {
        return NetUtil.isWifi(this);
    }

    /**
     * 显示进度条
     *
     * @param message
     */
    protected void showProgressDialog(String message) {
        dialog = CustomProgress.show(this, message, true, null);
    }

    /**
     * 关闭进度条
     */
    protected void closeProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
        //结束Activity&从栈中移除该Activity
        activityManagerUtil.popOneActivity(this);
    }

}
