package com.inanhu.wenjiaosuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.WJSApplyStatusActivity;
import com.inanhu.wenjiaosuo.adapter.FansListAdapter;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.base.BaseV4Fragment;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.FansBean;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.inanhu.wenjiaosuo.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 二度粉丝
 * <p/>
 * Created by Jason on 2016/7/28.
 */
public class LevelTwoFansFragment extends BaseV4Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener {

    @BindView(R.id.rv_frag_fans)
    RecyclerView rvFragFans;
    @BindView(R.id.refresh_fans)
    BGARefreshLayout refreshFans;

    private List<FansBean> fansBeanList = new ArrayList<>();
    private FansListAdapter mApater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fans, container, false);
        ButterKnife.bind(this, view);
        showProgressDialog("数据加载中...");
        refreshData();
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    private void refreshData() {
        if (isNetConnected()) {
            RequestParams params = new RequestParams(this);
            params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN));
            params.addFormDataPart(Constant.RequestKey.FANS_LEVEL, 2);
            params.addFormDataPart(Constant.RequestKey.FANS_PAGESIZE, 10);
            params.addFormDataPart(Constant.RequestKey.FANS_PAGENUM, 1);
            HttpEngine.doGet(URLUtil.UserApi.GET_FAN_LIST, params, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    LogUtil.e(TAG, response);
                    closeProgressDialog();
                    ApiResponse<List<FansBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<List<FansBean>>>() {
                    }.getType());
                    if (rsp != null && rsp.isSuccess()) {
                        fansBeanList = rsp.getData();
                        if (fansBeanList != null && fansBeanList.size() > 0) {
                            mApater.setDatas(fansBeanList);
                        }
                    }
                }
            });
        } else {
            ToastUtil.showToast(R.string.toast_network_unconnceted);
            closeProgressDialog();
        }
    }

    private void initRefreshLayout() {
        refreshFans.setDelegate(this);
        refreshFans.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
    }

    private void initRecyclerView() {
        rvFragFans.setLayoutManager(new LinearLayoutManager(getActivity()));
        mApater = new FansListAdapter(rvFragFans);
        mApater.setOnRVItemClickListener(this);
        rvFragFans.setAdapter(mApater);
        rvFragFans.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int position) {
        Intent intent = new Intent(getActivity(), WJSApplyStatusActivity.class);
        intent.putExtra(MessageFlag.UID, fansBeanList.get(position).getUid());
        startActivity(intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //TODO 完善刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshFans.endRefreshing();
            }
        }, 3000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void fetchData() {

    }
}
