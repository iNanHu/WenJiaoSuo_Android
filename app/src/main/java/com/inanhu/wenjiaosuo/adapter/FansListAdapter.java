package com.inanhu.wenjiaosuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.bean.FansBean;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Jason on 2016/7/31.
 */
public class FansListAdapter extends BGARecyclerViewAdapter<FansBean> {

    public FansListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_fans);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int position, FansBean fansBean) {
        String username = fansBean.getUsername();
        String telphone = fansBean.getTelphone();
        String realname = fansBean.getRealname();
        if (TextUtils.isEmpty(telphone) && TextUtils.isEmpty(realname)) { // 粉丝未完善信息，将username作为telephone，realname填默认
            bgaViewHolderHelper.setText(R.id.tv_fans_realname, Html.fromHtml("<font color=red>未完善资料</font>")).setText(R.id.tv_fans_telphone, username);
        }
        if (!TextUtils.isEmpty(telphone) && !TextUtils.isEmpty(realname)) {
            bgaViewHolderHelper.setText(R.id.tv_fans_realname, fansBean.getRealname()).setText(R.id.tv_fans_telphone, fansBean.getTelphone());
        }
    }
}
