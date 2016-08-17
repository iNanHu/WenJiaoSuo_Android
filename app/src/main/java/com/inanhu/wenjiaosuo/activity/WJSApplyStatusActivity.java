package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.adapter.WJSStatusListAdapter;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.WJSStatusBean;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.inanhu.wenjiaosuo.widget.DividerItemDecoration;
import com.inanhu.wenjiaosuo.widget.customswipetorefresh.CustomSwipeToRefresh;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 文交所开户申请进度界面
 * <p/>
 * Created by Jason on 2016/7/21.
 */
public class WJSApplyStatusActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_wjs_status_list)
    RecyclerView rvWjsStatusList;
    @BindView(R.id.wjs_status_container)
    CustomSwipeToRefresh wjsStatusContainer;

    //    private List<WJSStatusBean> wjsStatusBeanList = new ArrayList<>();
    private WJSStatusListAdapter mAdapter;

    private Intent intent;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wjs_apply_status);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.proccess_search);

        // 初始化下拉刷新
        initRefreshLayout();
        // 初始化RecylerView
        initRecyclerView();
        intent = getIntent();
        getData(intent);
    }

    private void getData(Intent intent) {
        if (intent != null) {
            uid = intent.getStringExtra(MessageFlag.UID);
            if (uid != null) { // 根据uid查询
                getDataByUid();
            } else { // 根据token查询
                getDataByToken();
            }
        }
    }

    /**
     * 根据用户token获取文交所开户状态
     */
    private void getDataByToken() {
        RequestParams params = new RequestParams(this);
        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
        if (isNetConnected()) {
            HttpEngine.doGet(URLUtil.UserApi.GET_APPLY_STATUS, params, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    LogUtil.e(TAG, response);
                    ApiResponse<ArrayList<WJSStatusBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<WJSStatusBean>>>() {
                    }.getType());
                    if (rsp != null && rsp.isSuccess()) {
                        List<WJSStatusBean> wjsStatusBeanList = rsp.getData();
                        if (wjsStatusBeanList != null) {
                            mAdapter.setDatas(wjsStatusBeanList);
                        }
                    } else {
                        ToastUtil.showToast("进度查询失败");
                    }
                    if (wjsStatusContainer.isRefreshing()) {
                        wjsStatusContainer.setRefreshing(false);
                    }
                }

            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
            if (wjsStatusContainer.isRefreshing()) {
                wjsStatusContainer.setRefreshing(false);
            }
        }
    }

    /**
     * 根据用户id获取文交所开户状态
     */
    private void getDataByUid() {
        RequestParams params = new RequestParams(this);
        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
        params.addFormDataPart(Constant.RequestKey.UID, uid);
        if (isNetConnected()) {
            HttpEngine.doGet(URLUtil.UserApi.GET_APPLY_STATUS_BYUID, params, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    LogUtil.e(TAG, response);
                    ApiResponse<ArrayList<WJSStatusBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<WJSStatusBean>>>() {
                    }.getType());
                    if (rsp != null && rsp.isSuccess()) {
                        List<WJSStatusBean> wjsStatusBeanList = rsp.getData();
                        if (wjsStatusBeanList != null) {
                            mAdapter.setDatas(wjsStatusBeanList);
                        }
                    } else {
                        ToastUtil.showToast("进度查询失败");
                    }
                    if (wjsStatusContainer.isRefreshing()) {
                        wjsStatusContainer.setRefreshing(false);
                    }
                }

            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
            if (wjsStatusContainer.isRefreshing()) {
                wjsStatusContainer.setRefreshing(false);
            }
        }
    }

    private void initRefreshLayout() {
        wjsStatusContainer.setOnRefreshListener(this);
        wjsStatusContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void initRecyclerView() {
        rvWjsStatusList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WJSStatusListAdapter(rvWjsStatusList);
        rvWjsStatusList.setAdapter(mAdapter);
        rvWjsStatusList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wjsStatusContainer.setRefreshing(false);
            }
        }, 2000);
    }
}
