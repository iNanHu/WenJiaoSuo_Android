package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.fragment.ContactsFragment;
import com.inanhu.wenjiaosuo.fragment.DiscoverFragment;
import com.inanhu.wenjiaosuo.fragment.ProfileFragment;
import com.inanhu.wenjiaosuo.fragment.WechatFragment;
import com.inanhu.wenjiaosuo.widget.noscrollviewpager.NoScrollViewPager;
import com.inanhu.wenjiaosuo.widget.tabview.TabItem;
import com.inanhu.wenjiaosuo.widget.tabview.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabClickListener {

    private TabLayout mTabLayout;
    BaseFragment fragment;
    NoScrollViewPager mViewPager;
    ArrayList<TabItem> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);

    }

    private void initData() {
        tabs = new ArrayList<TabItem>();
        tabs.add(new TabItem(R.drawable.selector_tab_msg, R.string.wechat, WechatFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_contact, R.string.contacts, ContactsFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_moments, R.string.discover, DiscoverFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_profile, R.string.me, ProfileFragment.class));

        mTabLayout.initData(tabs, this);
        mTabLayout.setCurrentTab(0);

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onTabClick(TabItem tabItem) {
        mViewPager.setCurrentItem(tabs.indexOf(tabItem));
    }


    public class FragAdapter extends FragmentPagerAdapter {

        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            try {
                return tabs.get(arg0).tagFragmentClz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

    }
}
