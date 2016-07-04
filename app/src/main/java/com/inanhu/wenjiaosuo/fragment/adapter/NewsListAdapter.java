package com.inanhu.wenjiaosuo.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.model.NewsItem;
import com.inanhu.wenjiaosuo.util.ImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Jason on 2016/7/4.
 */
public class NewsListAdapter extends BGARecyclerViewAdapter<NewsItem> {

    public NewsListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_news);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, NewsItem newsItem) {
        ImageLoader.with(R.mipmap.ic_launcher, newsItem.getPic(), (ImageView) bgaViewHolderHelper.getView(R.id.iv_news_pic));
        bgaViewHolderHelper.setText(R.id.tv_news_title, newsItem.getTitle()).setText(R.id.tv_news_intro, newsItem.getIntro()).setText(R.id.tv_news_date, newsItem.getDate()).setText(R.id.tv_news_readtimes, newsItem.getReadtimes()).setText(R.id.tv_news_comments, newsItem.getComments());
    }
}
