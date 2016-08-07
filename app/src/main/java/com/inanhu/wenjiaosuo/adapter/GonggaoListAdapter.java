package com.inanhu.wenjiaosuo.adapter;

import android.support.v7.widget.RecyclerView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.bean.GonggaoBean;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Jason on 2016/8/7.
 */
public class GonggaoListAdapter extends BGARecyclerViewAdapter<GonggaoBean> {

    public GonggaoListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_gonggao);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int position, GonggaoBean gonggaoBean) {
        bgaViewHolderHelper.setText(R.id.tv_gg_title, gonggaoBean.getTitle()).setText(R.id.tv_gg_intro, gonggaoBean.getDescription()).setText(R.id.tv_gg_date, gonggaoBean.getPubdate());
    }
}
