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

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Jason on 2016/7/21.
 */
public class WJSListAdapter extends BGARecyclerViewAdapter<WJSBean> implements MyHttpCycleContext {

    private Context context;
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    public WJSListAdapter(Context context, RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_wjs);
        this.context = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int position, final WJSBean wjsBean) {
        ImageLoader.with(R.mipmap.nanjing, wjsBean.getLogo(), (ImageView) bgaViewHolderHelper.getView(R.id.iv_wjs_logo));
        bgaViewHolderHelper.setText(R.id.tv_wjs_name, wjsBean.getName());
        final String onkey = wjsBean.getOnekey();
        String text = "立即开户";
        if ("1".equals(onkey)) { // 一键开户
            text = "一键开户";
        } else if ("2".equals(onkey)) { // 立即开户
            text = "立即开户";
        }
        bgaViewHolderHelper.setText(R.id.tv_wjs_apply, text);
        bgaViewHolderHelper.getTextView(R.id.tv_wjs_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.isUserProfileComplete()) { // 完善了详细信息的用户才能使用一账通模块
                    if ("1".equals(onkey)) { // 一键开户
                        RequestParams params = new RequestParams(WJSListAdapter.this);
                        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
                        params.addFormDataPart(Constant.RequestKey.WJSID, wjsBean.getData_id());
                        HttpEngine.doPost(URLUtil.UserApi.APPLY_WJS, params, new BaseHttpRequestCallback() {
                            @Override
                            public void onResponse(String response, Headers headers) {
                                LogUtil.e("TAG", response);
                                ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                                }.getType());
                                String data = rsp.getData();
                                if (rsp.isSuccess()){ // 提交申请成功

                                } else {
                                    ToastUtil.showToast("提交申请失败 " + data);
                                }
                            }
                        });
                    } else if ("2".equals(onkey)) { // 立即开户
                        Intent intent = new Intent(context, WebviewActivity.class);
                        intent.putExtra(MessageFlag.WEBVIEW_TOPBAR_TITLE, wjsBean.getName() + "开户");
                        intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, wjsBean.getLink());
                        context.startActivity(intent);
                    }
                } else {
                    ToastUtil.showToast("登录并完善用户详细信息后方可使用该功能");
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
