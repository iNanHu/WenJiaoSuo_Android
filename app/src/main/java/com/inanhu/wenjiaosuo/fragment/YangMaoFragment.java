package com.inanhu.wenjiaosuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.WebviewActivity;
import com.inanhu.wenjiaosuo.adapter.NewsListAdapter;
import com.inanhu.wenjiaosuo.adapter.YangmaoListAdapter;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.NewsBean;
import com.inanhu.wenjiaosuo.bean.YangmaoBean;
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
 * 羊毛专区
 * <p/>
 * Created by Jason on 2016/8/10.
 */
public class YangMaoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BGAOnRVItemClickListener {


    @BindView(R.id.list_yangmao)
    RecyclerView listYangmao;
    @BindView(R.id.swipe_yangmao_center_container)
    CustomSwipeToRefresh swipeYangmaoCenterContainer;

    //    private List<YangmaoBean> yangmaos = new ArrayList<>();
    private YangmaoListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yangmao, container, false);
        ButterKnife.bind(this, view);
        showProgressDialog("数据加载中...");
        initRefreshLayout();
        initRecyclerView();
        refreshData();
        return view;
    }

    private void initRefreshLayout() {
        swipeYangmaoCenterContainer.setOnRefreshListener(this);
        swipeYangmaoCenterContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void initRecyclerView() {
        listYangmao.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new YangmaoListAdapter(listYangmao);
        mAdapter.setOnRVItemClickListener(this);
        listYangmao.setAdapter(mAdapter);
//        listYangmao.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void refreshData() {
        if (isNetConnected()) {
            RequestParams params = new RequestParams(this);
            params.addFormDataPart(Constant.RequestKey.CID, "11"); // 不传分类列表就获取全类别的
            params.addFormDataPart(Constant.RequestKey.ORDER, "time"); // 排序，方式有time/views/rec
//        params.addFormDataPart(Constant.RequestKey.PAGESIZE, "2");
//        params.addFormDataPart(Constant.RequestKey.PAGE, "1");
            HttpEngine.doGet(URLUtil.NewsApi.GETLIST, params, new BaseHttpRequestCallback() {

                @Override
                public void onResponse(String response, Headers headers) {
                    LogUtil.e(TAG, response);
                    closeProgressDialog();
                    ApiResponse<ArrayList<YangmaoBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<YangmaoBean>>>() {
                    }.getType());
                    if (rsp != null && rsp.isSuccess()) {
                        List<YangmaoBean> list = rsp.getData();
                        if (list != null) {
                            mAdapter.setDatas(list);
                        }
                    }
                    if (swipeYangmaoCenterContainer.isRefreshing()) {
                        swipeYangmaoCenterContainer.setRefreshing(false);
                    }
                }

                @Override
                public void onFinish() {
                    closeProgressDialog();
                }
            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
            if (swipeYangmaoCenterContainer.isRefreshing()) {
                swipeYangmaoCenterContainer.setRefreshing(false);
            }
        }
    }


    @Override
    public void fetchData() {

    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int position) {
        if (!TextUtils.isEmpty(mAdapter.getItem(position).getAurl())) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), WebviewActivity.class);
            intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, mAdapter.getItem(position).getAurl());
            intent.putExtra(MessageFlag.IS_SHOW_TOPBAR_SHARE, true);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        refreshData();
    }
}
