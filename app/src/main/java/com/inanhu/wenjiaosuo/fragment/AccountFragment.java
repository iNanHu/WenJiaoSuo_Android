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
import com.inanhu.wenjiaosuo.adapter.WJSListAdapter;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.bean.EquityDataBean;
import com.inanhu.wenjiaosuo.bean.NewsBean;
import com.inanhu.wenjiaosuo.bean.WJSBean;
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
import okhttp3.Headers;


/**
 * 一账通界面
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class AccountFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_wjs_list)
    RecyclerView rvWjsList;
    @BindView(R.id.swipe_wjs_container)
    CustomSwipeToRefresh swipeWjsContainer;

    private List<WJSBean> wjsDatas = new ArrayList<>();
    private WJSListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshData();
    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        LogUtil.e(TAG, "===onCreateView===");
        ButterKnife.bind(this, view);
        showProgressDialog("数据加载中...");
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    private void refreshData() {
        if (isNetConnected()) {
            HttpEngine.doGet(URLUtil.WJSApi.GETLIST, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    closeProgressDialog();
                    LogUtil.e(TAG, response);
                    ApiResponse<ArrayList<WJSBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<WJSBean>>>() {
                    }.getType());
                    if (rsp != null && rsp.isSuccess()) {
                        wjsDatas = rsp.getData();
                        if (wjsDatas != null) {
                            mAdapter.setDatas(wjsDatas);
                        }
                    }

                    if (swipeWjsContainer.isRefreshing()) {
                        swipeWjsContainer.setRefreshing(false);
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
        swipeWjsContainer.setOnRefreshListener(this);
        swipeWjsContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void initRecyclerView() {
        rvWjsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WJSListAdapter(getActivity(), rvWjsList);
        rvWjsList.setAdapter(mAdapter);
        rvWjsList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onRefresh() {
        refreshData();
    }
}
