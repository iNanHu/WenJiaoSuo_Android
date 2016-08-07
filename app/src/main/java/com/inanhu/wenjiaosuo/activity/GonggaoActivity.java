package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.adapter.GonggaoListAdapter;
import com.inanhu.wenjiaosuo.adapter.NewsListAdapter;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.bean.GonggaoBean;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.inanhu.wenjiaosuo.widget.DividerItemDecoration;
import com.inanhu.wenjiaosuo.widget.customswipetorefresh.CustomSwipeToRefresh;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 公告界面
 * <p/>
 * Created by Jason on 2016/8/7.
 */
public class GonggaoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BGAOnRVItemClickListener {

    @BindView(R.id.list_gg)
    RecyclerView listGg;
    @BindView(R.id.swipe_gg_container)
    CustomSwipeToRefresh swipeGgContainer;

    private GonggaoListAdapter mAdapter;
    private List<GonggaoBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.news_menu_gg);
        // 初始化下拉刷新
        initRefreshLayout();
        // 初始化RecylerView
        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        listGg.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GonggaoListAdapter(listGg);
        mAdapter.setOnRVItemClickListener(this);
        listGg.setAdapter(mAdapter);
        listGg.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initRefreshLayout() {
        swipeGgContainer.setOnRefreshListener(this);
        swipeGgContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void getData() {
        HttpEngine.doGet(URLUtil.GONGGAO_FROM_YOUBICARD, new BaseHttpRequestCallback(){

            @Override
            public void onStart() {
                showProgressDialog("数据加载中...", true);
            }

            @Override
            public void onResponse(String response, Headers headers) {
                closeProgressDialog();
                LogUtil.e(TAG, response);
                JSONObject rsp = JSON.parseObject(response);
                int ret = (int) rsp.get("success");
                if (ret == 1){ // 返回数据有效
                    String data = ((JSONArray) rsp.get("data")).toString();
                    list = JSON.parseArray(data, GonggaoBean.class);
                    mAdapter.addNewDatas(list);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeGgContainer.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int position) {

    }
}
