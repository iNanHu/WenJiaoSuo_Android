package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.adapter.EquityListAdapter;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.bean.EquityDataBean;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.inanhu.wenjiaosuo.widget.customswipetorefresh.CustomSwipeToRefresh;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import okhttp3.Headers;

/**
 * 行情界面
 * <p>
 * Created by Jason on 2016/8/10.
 */
public class EquityActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_equity_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_equity_container)
    CustomSwipeToRefresh mRereshLayout;

    private List<EquityDataBean> equityDatas = new ArrayList<>();
    private EquityListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.equity);

        showProgressDialog("数据加载中...", true);
        refreshData();
        initRefreshLayout();
        initRecyclerView();
    }

    private void refreshData() {
        if (isNetConnected()) {
            HttpEngine.doGet(URLUtil.EQUITY_FROM_YOUBICARD, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    closeProgressDialog();
                    LogUtil.e(TAG, response);
                    equityDatas = new Gson().fromJson(response, new TypeToken<List<EquityDataBean>>() {
                    }.getType());
                    if (equityDatas != null && equityDatas.size() > 0) {
                        mAdapter.setDatas(equityDatas);
                    }
                    if (mRereshLayout.isRefreshing()) {
                        mRereshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFinish() {
                    closeProgressDialog();
                }
            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
            if (mRereshLayout.isRefreshing()) {
                mRereshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshLayout() {
        mRereshLayout.setOnRefreshListener(this);
        mRereshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EquityListAdapter(this, recyclerView, R.layout.item_equity);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        refreshData();
    }
}
