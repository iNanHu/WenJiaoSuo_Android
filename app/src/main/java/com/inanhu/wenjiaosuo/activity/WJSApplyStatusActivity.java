package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
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
import com.inanhu.wenjiaosuo.bean.WJSStatusBean;
import com.inanhu.wenjiaosuo.util.HttpEngine;
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

    private List<WJSStatusBean> wjsStatusBeanList = new ArrayList<>();
    private WJSStatusListAdapter mAdapter;

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
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams(this);
        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN));
        HttpEngine.doGet(URLUtil.UserApi.GET_APPLY_STATUS, params, new BaseHttpRequestCallback() {
            @Override
            public void onResponse(String response, Headers headers) {
                ApiResponse<ArrayList<WJSStatusBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<WJSStatusBean>>>() {
                }.getType());
                if (rsp != null && rsp.isSuccess()) {
                    wjsStatusBeanList = rsp.getData();
                    if (wjsStatusBeanList != null) {
                        mAdapter.setDatas(wjsStatusBeanList);
                    }
                }
            }

        });
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
        getData();
    }
}
