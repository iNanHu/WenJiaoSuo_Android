package com.inanhu.wenjiaosuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.GonggaoActivity;
import com.inanhu.wenjiaosuo.activity.HuodongActivity;
import com.inanhu.wenjiaosuo.activity.MainActivity;
import com.inanhu.wenjiaosuo.activity.NewsCenterActivity;
import com.inanhu.wenjiaosuo.activity.WebviewActivity;
import com.inanhu.wenjiaosuo.activity.WenMinSXYActivity;
import com.inanhu.wenjiaosuo.activity.WenMinSheQunActivity;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.Banner;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.NewsBean;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.util.AccountUtil;
import com.inanhu.wenjiaosuo.util.DateUtil;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.inanhu.wenjiaosuo.widget.convenientbanner.NetworkImageHolderView;
import com.inanhu.wenjiaosuo.widget.customswipetorefresh.CustomSwipeToRefresh;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;


/**
 * 首页资讯界面
 * <p>
 * Created by zzmiao on 2015/9/23.
 */
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_news_container)
    CustomSwipeToRefresh mRereshLayout;
    @BindView(R.id.cb_news_ad)
    ConvenientBanner convenientBanner;
    @BindView(R.id.sv_news_container)
    ScrollView scrollviewContent;

    // 当前登录用户
    private UserInfo userInfo;

    /**
     * 新闻模块
     */
    @BindView(R.id.iv_news_pic)
    ImageView ivNewsPic;
    @BindView(R.id.tv_news_title)
    TextView tvNewsTitle;
    @BindView(R.id.tv_news_intro)
    TextView tvNewsIntro;
    @BindView(R.id.tv_news_date)
    TextView tvNewsDate;
    @BindView(R.id.tv_news_readtimes)
    TextView tvNewsReadtimes;
    @BindView(R.id.iv_news_pic1)
    ImageView ivNewsPic1;
    @BindView(R.id.tv_news_title1)
    TextView tvNewsTitle1;
    @BindView(R.id.tv_news_intro1)
    TextView tvNewsIntro1;
    @BindView(R.id.tv_news_date1)
    TextView tvNewsDate1;
    @BindView(R.id.tv_news_readtimes1)
    TextView tvNewsReadtimes1;
    @BindView(R.id.tv_news_copyfrom)
    TextView tvNewsCopyfrom;
    @BindView(R.id.tv_news_copyfrom1)
    TextView tvNewsCopyfrom1;

    @OnClick(R.id.rl_news_center)
    public void toNewsCenter() {
        startActivity(new Intent(getActivity(), NewsCenterActivity.class));
    }

    // 网络加载banner图片资源
    private List<Banner> banners = new ArrayList<>();
    private NewsBean newsBean, newsBean1;
    private List<NewsBean> news = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public void fetchData() {
        LogUtil.e(TAG, "===fetchData===");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.e(TAG, "===onCreateView===");
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        init();
        initBanner();
        initRefreshLayout();
        return view;
    }

    private void init() {
        if (AccountUtil.isLogin()) {
            // 登录成功获取用户基本信息
            RequestParams params = new RequestParams(this);
            params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN));
            HttpEngine.doGet(URLUtil.UserApi.INFO, params, new BaseHttpRequestCallback() {
                @Override
                public void onResponse(String response, Headers headers) {
                    ApiResponse<UserInfo> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<UserInfo>>() {
                    }.getType());
                    if (rsp != null && rsp.isSuccess()) {
                        userInfo = rsp.getData();
                        if (userInfo != null) { // 保存当前用户到全局变量
                            GlobalValue.getInstance().saveGlobal(MessageFlag.CURRENT_USER_INFO, userInfo);
                        }

                    }
                }
            });
        }
    }


    /**
     * 配置banner
     */
    private void initBanner() {
        HttpEngine.doGet(URLUtil.NewsApi.GETBANNER, new BaseHttpRequestCallback() {
            @Override
            public void onResponse(String response, Headers headers) {
                ApiResponse<ArrayList<Banner>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<Banner>>>() {
                }.getType());
                if (rsp != null) {
                    banners = rsp.getData();
                    convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, banners)
                            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                            .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
                    // 开启自动翻页
                    convenientBanner.startTurning(3000);
                }
            }
        });
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshLayout() {
        mRereshLayout.setOnRefreshListener(this);
        mRereshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
        // 加载新闻（2条）
        RequestParams params = new RequestParams(this);
//        params.addFormDataPart(Constant.RequestKey.CID, "2"); // 不传分类列表就获取全类别的
        params.addFormDataPart(Constant.RequestKey.ORDER, "time"); // 排序，方式有time/views/rec
        params.addFormDataPart(Constant.RequestKey.NEWS_PAGESIZE, "2");
        params.addFormDataPart(Constant.RequestKey.NEWS_PAGE, "1");
        HttpEngine.doGet(URLUtil.NewsApi.GETLIST, params, new BaseHttpRequestCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onResponse(String response, Headers headers) {
                ApiResponse<ArrayList<NewsBean>> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<ArrayList<NewsBean>>>() {
                }.getType());
                if (rsp != null && rsp.isSuccess()) {
                    news = rsp.getData();
                    setNews();
                }
                if (mRereshLayout.isRefreshing()) {
                    mRereshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void setNews() {
        if (news != null) {
            // 设置第一条新闻
            newsBean = news.get(0);
            ImageLoader.with(R.mipmap.app_icon, newsBean.getImage(), ivNewsPic);
            tvNewsTitle.setText(newsBean.getTitle());
            tvNewsIntro.setText(newsBean.getDescription());
            tvNewsDate.setText(DateUtil.timeStamp2Date(newsBean.getTime(), null));
            tvNewsReadtimes.setText("阅读：" + newsBean.getViews());
            tvNewsCopyfrom.setText(newsBean.getCopyfrom());

            // 设置第二条新闻
            newsBean1 = news.get(1);
            ImageLoader.with(R.mipmap.app_icon, newsBean1.getImage(), ivNewsPic1);
            tvNewsTitle1.setText(newsBean1.getTitle());
            tvNewsIntro1.setText(newsBean1.getDescription());
            tvNewsDate1.setText(DateUtil.timeStamp2Date(newsBean1.getTime(), null));
            tvNewsReadtimes1.setText("阅读：" + newsBean1.getViews());
            tvNewsCopyfrom1.setText(newsBean1.getCopyfrom());
        }
    }

    @OnClick(R.id.ll_news)
    public void toNews() {
        Intent intent = new Intent();
        if (newsBean != null) {
            if (!TextUtils.isEmpty(newsBean.getAurl())) {
                intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, newsBean.getAurl());
            }
        }
        intent.setClass(getActivity(), WebviewActivity.class);
        intent.putExtra(MessageFlag.IS_SHOW_TOPBAR_SHARE, true);
        startActivity(intent);
    }

    @OnClick(R.id.ll_news1)
    public void toNews1() {
        Intent intent = new Intent();
        if (newsBean1 != null) {
            if (!TextUtils.isEmpty(newsBean1.getAurl())) {
                intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, newsBean1.getAurl());
            }
        }
        intent.setClass(getActivity(), WebviewActivity.class);
        intent.putExtra(MessageFlag.IS_SHOW_TOPBAR_SHARE, true);
        startActivity(intent);
    }

    @OnClick({R.id.ll_stock, R.id.ll_gg, R.id.ll_activity, R.id.ll_wmsq, R.id.ll_wmsxy, R.id.ll_qmjj})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_stock:
                // 切换到电子盘界面
                ((MainActivity) getActivity()).setSelect(1);
                break;
            case R.id.ll_gg:
                startActivity(new Intent(getActivity(), GonggaoActivity.class));
                break;
            case R.id.ll_activity:
                startActivity(new Intent(getActivity(), HuodongActivity.class));
                break;
            case R.id.ll_wmsq:
                startActivity(new Intent(getActivity(), WenMinSheQunActivity.class));
                break;
            case R.id.ll_wmsxy:
                startActivity(new Intent(getActivity(), WenMinSXYActivity.class));
                break;
            case R.id.ll_qmjj:
                ((MainActivity)getActivity()).setSelect(3);
                break;
        }
    }
}

