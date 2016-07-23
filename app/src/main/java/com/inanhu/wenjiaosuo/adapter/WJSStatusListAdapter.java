package com.inanhu.wenjiaosuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.bean.WJSStatusBean;
import com.inanhu.wenjiaosuo.util.ImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Jason on 2016/7/21.
 */
public class WJSStatusListAdapter extends BGARecyclerViewAdapter<WJSStatusBean> {


    public WJSStatusListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_wjs_status);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, WJSStatusBean wjsStatusBean) {
        String wjsName = wjsStatusBean.getName();
        if (!TextUtils.isEmpty(wjsName)) {
            bgaViewHolderHelper.setText(R.id.tv_wjs_name, wjsName);
        }
        String logo = wjsStatusBean.getLogo();
        if (!TextUtils.isEmpty(logo)) {
            ImageLoader.with(R.mipmap.nanjing, logo, bgaViewHolderHelper.getImageView(R.id.iv_wjs_logo));
        }
        String statusCode = wjsStatusBean.getStatus(); // "1"-待分配  "2"-已分配  "3"-已完成  "4"-已驳回
        String status = "待分配";
        View view = bgaViewHolderHelper.getView(R.id.ll_wjs_status_success);
        if (!TextUtils.isEmpty(statusCode)) {
            if ("1".equals(statusCode)) {
                status = "待分配";
            } else if ("2".equals(statusCode)) {
                status = "已分配";
            } else if ("3".equals(statusCode)) {
                status = "已完成";
                view.setVisibility(View.VISIBLE);
                bgaViewHolderHelper.setText(R.id.tv_wjs_username, "账号：" + wjsStatusBean.getWjsusername()).setText(R.id.tv_wjs_passwrod, "密码：" + wjsStatusBean.getWjspass());
            } else if ("4".equals(statusCode)) {
                status = "已驳回";
            }
        }
        bgaViewHolderHelper.setText(R.id.tv_wjs_apply_status, status).setBackgroundRes(R.id.tv_wjs_apply_status, R.drawable.radius_border_orange);
    }
}
