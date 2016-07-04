package com.inanhu.wenjiaosuo.fragment.adapter;

import android.support.v7.widget.RecyclerView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.bean.EquityDataBean;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by iNanHu on 2016/7/4.
 */
public class EquityListAdapter extends BGARecyclerViewAdapter<EquityDataBean> {

    public EquityListAdapter(RecyclerView recyclerView, int itemLayoutId) {
        super(recyclerView, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, EquityDataBean equityDataBean) {
        bgaViewHolderHelper.setText(R.id.tv_wjs_up, equityDataBean.getUp()).setText(R.id.tv_wjs_count, equityDataBean.getCount())
                .setText(R.id.tv_wjs_down, equityDataBean.getDown()).setText(R.id.tv_wjs_flat, equityDataBean.getFlat())
                .setText(R.id.tv_wjs_money, equityDataBean.getMoney()).setText(R.id.tv_wjs_name, "南京文交所")
                .setText(R.id.tv_wjs_price, equityDataBean.getPrice()).setText(R.id.tv_wjs_rate, equityDataBean.getRate())
                .setText(R.id.tv_wjs_top, equityDataBean.getTop()).setText(R.id.tv_wjs_bottom, equityDataBean.getBottom())
                .setText(R.id.tv_wjs_total, equityDataBean.getTotal());
    }
}
