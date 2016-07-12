package com.inanhu.wenjiaosuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.bean.NewsBean;
import com.inanhu.wenjiaosuo.util.DateUtil;
import com.inanhu.wenjiaosuo.util.ImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Jason on 2016/7/4.
 */
public class NewsListAdapter extends BGARecyclerViewAdapter<NewsBean> {

    public NewsListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_news);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, NewsBean newsBean) {
        ImageLoader.with(R.mipmap.ic_launcher, newsBean.getImage(), (ImageView) bgaViewHolderHelper.getView(R.id.iv_news_pic));
        bgaViewHolderHelper.setText(R.id.tv_news_title, newsBean.getTitle()).setText(R.id.tv_news_intro, newsBean.getDescription()).setText(R.id.tv_news_date, DateUtil.timeStamp2Date(newsBean.getTime(), null)).setText(R.id.tv_news_readtimes, "阅读：" + newsBean.getViews());
    }
}
