package com.inanhu.wenjiaosuo.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.inanhu.wenjiaosuo.util.MyHttpCycleContext;
import com.inanhu.wenjiaosuo.util.NetUtil;
import com.inanhu.wenjiaosuo.widget.customprogressdialog.CustomProgress;

import cn.finalteam.okhttpfinal.HttpTaskHandler;

/**
 * Created by yx on 16/4/3.
 */
public abstract class BaseFragment extends Fragment implements MyHttpCycleContext{

    protected String TAG;
    protected CustomProgress dialog;

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    protected boolean isNetConnected() {
        return NetUtil.isConnected(getActivity());
    }

    /**
     * 判断是否为WiFi连接
     *
     * @return
     */
    protected boolean isNetWifi() {
        return NetUtil.isWifi(getActivity());
    }

    /**
     * 显示进度条
     *
     * @param message
     */
    protected void showProgressDialog(String message) {
        dialog = CustomProgress.show(getActivity(), message, true, null);
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
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }
}