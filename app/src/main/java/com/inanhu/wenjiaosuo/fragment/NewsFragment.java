package com.inanhu.wenjiaosuo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.MainActivity;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.fragment.adapter.NewsListAdapter;
import com.inanhu.wenjiaosuo.bean.NewsBean;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.widget.convenientbanner.NetworkImageHolderView;
import com.inanhu.wenjiaosuo.widget.customswipetorefresh.CustomSwipeToRefresh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 首页资讯界面
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_news_container)
    CustomSwipeToRefresh mRereshLayout;
    //    @BindView(R.id.rv_news)
//    RecyclerView recyclerViewNews;
    @BindView(R.id.cb_news_ad)
    ConvenientBanner convenientBanner;
    @BindView(R.id.sv_news_container)
    ScrollView scrollviewContent;

    @OnClick(R.id.rl_news_center)
    public void toNewsCenter() {
        ToastUtil.showToast("去新闻中心啦");
    }

    // 网络加载banner图片资源
    private List<String> networkImages;
    private List<NewsBean> newsBeen = new ArrayList<>();
    private NewsListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        GlobalValue.getInstance().saveGlobal("ABC", "haha");
    }

    @Override
    public void fetchData() {
        LogUtil.e(TAG, "===fetchData===");
        networkImages = Arrays.asList("http://ww2.sinaimg.cn/large/610dc034jw1f5aqgzu2oej20rt15owo7.jpg", "http://ww3.sinaimg.cn/large/610dc034gw1f59lsn7wjnj20du0ku40c.jpg", "http://ww1.sinaimg.cn/large/610dc034jw1f566a296rpj20lc0sggoj.jpg");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.e(TAG, "===onCreateView===");
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        initBanner();
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    /**
     * 配置banner
     */
    private void initBanner() {
        networkImages = Arrays.asList("http://ww2.sinaimg.cn/large/610dc034jw1f5aqgzu2oej20rt15owo7.jpg", "http://ww3.sinaimg.cn/large/610dc034gw1f59lsn7wjnj20du0ku40c.jpg", "http://ww1.sinaimg.cn/large/610dc034jw1f566a296rpj20lc0sggoj.jpg");
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        // 开启自动翻页
        convenientBanner.startTurning(3000);
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshLayout() {
        mRereshLayout.setOnRefreshListener(this);
        mRereshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void initRecyclerView() {
//        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list));
//        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new NewsListAdapter(recyclerViewNews);
//        recyclerViewNews.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRereshLayout.setRefreshing(false);
            }
        }, 5000);
    }

    private void getData() {
        String avatarUrl = "https://avatars1.githubusercontent.com/u/5058324?v=3&u=06df9935b0f3e13c28f000fafd7ca59bdef2594d&s=140";
        newsBeen.add(new NewsBean(avatarUrl, "文民一账通", "文民一账通", "2016-7-4", "100", "12"));
        newsBeen.add(new NewsBean(avatarUrl, "文民一账通", "文民一账通", "2016-7-4", "100", "12"));
        newsBeen.add(new NewsBean(avatarUrl, "文民一账通", "文民一账通", "2016-7-4", "100", "12"));
        newsBeen.add(new NewsBean(avatarUrl, "文民一账通", "文民一账通", "2016-7-4", "100", "12"));
        newsBeen.add(new NewsBean(avatarUrl, "文民一账通", "文民一账通", "2016-7-4", "100", "12"));
    }

}

