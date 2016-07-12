package com.inanhu.wenjiaosuo.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.adapter.EquityListAdapter;
import com.inanhu.wenjiaosuo.base.BaseFragment;
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
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class EquityFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_equity_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_equity_container)
    CustomSwipeToRefresh mRereshLayout;

    private List<EquityDataBean> equityDatas = new ArrayList<>();
    private EquityListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG, "===onCreate===");
    }

    @Override
    public void fetchData() {
        LogUtil.e(TAG, "===fetchData===");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equity, container, false);
        LogUtil.e(TAG, "===onCreateView===");
        ButterKnife.bind(this, view);
        showProgressDialog("数据加载中...");
        refreshData();
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    private void refreshData() {
        if (isNetConnected()) {
            HttpEngine.doGet(URLUtil.EQUITY_FROM_YOUBICARD, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    closeProgressDialog();
                    LogUtil.e(TAG, response);
                    equityDatas = new Gson().fromJson(response, new TypeToken<List<EquityDataBean>>() {}.getType());
                    LogUtil.e(TAG, equityDatas.size());
                    mAdapter.setDatas(equityDatas);
                    if (mRereshLayout.isRefreshing()) {
                        mRereshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new EquityListAdapter(getActivity(), recyclerView, R.layout.item_equity);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

}
