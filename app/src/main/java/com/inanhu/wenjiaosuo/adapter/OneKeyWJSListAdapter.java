package com.inanhu.wenjiaosuo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.WebviewActivity;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.WJSBean;
import com.inanhu.wenjiaosuo.util.AccountUtil;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.MyHttpCycleContext;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 支持一键开户文交所列表适配器
 * <p/>
 * Created by Jason on 2016/7/21.
 */
public class OneKeyWJSListAdapter extends BGARecyclerViewAdapter<WJSBean> implements MyHttpCycleContext {

    private Context context;
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    public OneKeyWJSListAdapter(Context context, RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_wjs);
        this.context = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int position, final WJSBean wjsBean) {
        ImageLoader.with(R.mipmap.nanjing, wjsBean.getLogo(), (ImageView) bgaViewHolderHelper.getView(R.id.iv_wjs_logo));
        bgaViewHolderHelper.setText(R.id.tv_wjs_name, wjsBean.getName());
        bgaViewHolderHelper.setText(R.id.tv_wjs_apply, "一键开户");
        bgaViewHolderHelper.getTextView(R.id.tv_wjs_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.isUserProfileComplete()) { // 完善了详细信息的用户才能使用一账通模块
                    RequestParams params = new RequestParams(OneKeyWJSListAdapter.this);
                    params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
                    params.addFormDataPart(Constant.RequestKey.WJSID, wjsBean.getData_id());
                    HttpEngine.doPost(URLUtil.UserApi.APPLY_WJS, params, new BaseHttpRequestCallback() {
                        @Override
                        public void onResponse(String response, Headers headers) {
                            LogUtil.e("TAG", response);
                            ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                            }.getType());
                            String data = rsp.getData();
                            if (rsp.isSuccess()) { // 提交申请成功
                                ToastUtil.showToast("提交申请成功");
                            } else {
                                ToastUtil.showToast("提交申请失败 " + data);
                            }
                        }
                    });
                } else {
                    ToastUtil.showToast(R.string.commit_profile_onekey);
                }

            }
        });

        bgaViewHolderHelper.getTextView(R.id.tv_wjs_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.isUserProfileComplete()) { // 完善了详细信息的用户才能使用一账通模块
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra(MessageFlag.WEBVIEW_TOPBAR_TITLE, wjsBean.getName() + "入金激活教程");
                    intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, wjsBean.getTutorial_link());
                    context.startActivity(intent);
                } else {
                    ToastUtil.showToast(R.string.commit_to_jihuo);
                }
            }
        });
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
}
