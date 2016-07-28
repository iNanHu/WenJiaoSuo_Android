package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.fragment.LevelOneFansFragment;
import com.inanhu.wenjiaosuo.fragment.LevelTwoFansFragment;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgaindicator.BGAFixedIndicator;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 我的粉丝界面
 * <p/>
 * Created by Jason on 2016/7/27.
 */
public class MyFansActivity extends BaseActivity {

    @BindView(R.id.indicator)
    BGAFixedIndicator mIndicator;
    @BindView(R.id.vp_viewpager_content)
    ViewPager mContentVp;

    private String[] mTitles;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        ButterKnife.bind(this);
        initView();


//        RequestParams params = new RequestParams(this);
//        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN));
//        HttpEngine.doGet(URLUtil.UserApi.GET_FAN_LIST, params, new BaseHttpRequestCallback() {
//            @Override
//            public void onResponse(String response, Headers headers) {
//                LogUtil.e(TAG, response);
//            }
//        });
    }

    private void initView() {
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.my_fans);

        mFragments = new Fragment[2];
        mFragments[0] = new LevelOneFansFragment();
        mFragments[1] = new LevelTwoFansFragment();

        mTitles = new String[2];
        mTitles[0] = "一度粉丝";
        mTitles[1] = "二度粉丝";
        mContentVp.setAdapter(new ContentViewPagerAdapter(getSupportFragmentManager()));
        mIndicator.initData(0, mContentVp);
    }


    class ContentViewPagerAdapter extends FragmentPagerAdapter {

        public ContentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
