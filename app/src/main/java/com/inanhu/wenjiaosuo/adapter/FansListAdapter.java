package com.inanhu.wenjiaosuo.adapter;

import android.support.v7.widget.RecyclerView;

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
        bgaViewHolderHelper.setText(R.id.tv_fans_realname, fansBean.getRealname()).setText(R.id.tv_fans_telphone, fansBean.getTelphone());
    }
}
