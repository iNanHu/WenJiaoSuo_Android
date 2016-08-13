package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.adapter.NewsListAdapter;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.NewsBean;
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
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 新闻中心
 * <p/>
 * Created by iNanHu on 2016/7/12.
 */
public class NewsCenterActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BGAOnRVItemClickListener {

    @BindView(R.id.list_news)
    RecyclerView listNews;
    @BindView(R.id.swipe_news_center_container)
    CustomSwipeToRefresh swipeNewsCenterContainer;

    private NewsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_center);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.news_center);
        showTopBarRight(false);
        // 初始化下拉刷新
        initRefreshLayout();
        // 初始化RecylerView
        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        listNews.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewsListAdapter(listNews);
        mAdapter.setOnRVItemClickListener(this);
        listNews.setAdapter(mAdapter);
        listNews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initRefreshLayout() {
        swipeNewsCenterContainer.setOnRefreshListener(this);
        swipeNewsCenterContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void getData() {
        RequestParams params = new RequestParams(this);
        params.addFormDataPart(Constant.RequestKey.CID, "2"); // 不传分类列表就获取全类别的
        params.addFormDataPart(Constant.RequestKey.ORDER, "time"); // 排序，方式有time/views/rec
//        params.addFormDataPart(Constant.RequestKey.PAGESIZE, "2");
//        params.addFormDataPart(Constant.RequestKey.PAGE, "1");
        HttpEngine.doGet(URLUtil.NewsApi.GETLIST, params, new BaseHttpRequestCallback() {

            @Override
            public void onStart() {
                showProgressDialog("", true);
            }

            @Override
            public void onResponse(String response, Headers headers) {
                LogUtil.e(TAG, response);
                closeProgressDialog();
                ApiResponse<ArrayList<NewsBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<NewsBean>>>() {
                }.getType());
                if (rsp != null && rsp.isSuccess()) {
                    List<NewsBean> list = rsp.getData();
                    if (list != null) {
                        mAdapter.addNewDatas(list);
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeNewsCenterContainer.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int position) {
        if (!TextUtils.isEmpty(mAdapter.getItem(position).getAurl())) {
            Intent intent = new Intent();
            intent.setClass(NewsCenterActivity.this, WebviewActivity.class);
            intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, mAdapter.getItem(position).getAurl());
            intent.putExtra(MessageFlag.IS_SHOW_TOPBAR_SHARE, true);
            startActivity(intent);
        }
    }
}
