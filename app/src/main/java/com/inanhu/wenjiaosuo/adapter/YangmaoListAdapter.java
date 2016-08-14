package com.inanhu.wenjiaosuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.bean.YangmaoBean;
import com.inanhu.wenjiaosuo.util.ImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Jason on 2016/8/14.
 */
public class YangmaoListAdapter extends BGARecyclerViewAdapter<YangmaoBean> {

    public YangmaoListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_yangmao);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int position, YangmaoBean yangmaoBean) {
        ImageLoader.with(R.mipmap.ic_launcher, yangmaoBean.getImage(), (ImageView) bgaViewHolderHelper.getView(R.id.iv_yangmao));
    }
}
