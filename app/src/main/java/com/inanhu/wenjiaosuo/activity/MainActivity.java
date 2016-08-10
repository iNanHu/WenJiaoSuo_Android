package com.inanhu.wenjiaosuo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.fragment.AccountFragment;
import com.inanhu.wenjiaosuo.fragment.EquityFragment;
import com.inanhu.wenjiaosuo.fragment.NewsFragment;
import com.inanhu.wenjiaosuo.fragment.ProfileFragment;
import com.inanhu.wenjiaosuo.fragment.YangMaoFragment;
import com.inanhu.wenjiaosuo.util.AccountUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    private LinearLayout mTabNews;
    private LinearLayout mTabEquity;

    private LinearLayout mTabAccount;
    private LinearLayout mTabProfile;

    private ImageButton mImgNews;
    private ImageButton mImgEquity;
    private ImageButton mImgAccount;
    private ImageButton mImgProfile;

    private TextView mTextViewNews;
    private TextView mTextViewEquity;
    private TextView mTextViewAccount;
    private TextView mTextViewProfile;

    private NewsFragment mFragmentNews;
//    private EquityFragment mFragmentEquity;
    private YangMaoFragment mFragmentYangMao;
    private AccountFragment mFragmentAccount;
    private ProfileFragment mFragmentProfile;
    private int currentFragment = 0; // 表示当前Fragment, 0-News/1-Blog/2-Focus/3-Profile

    //    private GroupAdapter groupAdapter;
//    private ArrayList<String> groups = new ArrayList<String>();
//    private PopupWindow mPopupWindow;
    private View contentView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initEvent();
        setSelect(0); // 默认第一个
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    public void setSelect(int i) {
        resetImgsandText();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                // 显示对应的fragment
                if (mFragmentNews == null) {
                    mFragmentNews = new NewsFragment();
                    transaction.add(R.id.id_content, mFragmentNews);
                } else {
                    transaction.show(mFragmentNews);
                }
                currentFragment = 0;
                // 设置底部Tab图标和文字状态
                mImgNews.setImageResource(R.mipmap.tabbar_news_highlight);
                mTextViewNews.setTextColor(ContextCompat.getColor(this, R.color.red));
                showTopBar(true);
                setTopBarTitle(R.string.news);
                showTopBarBack(false);
                showTopBarRight(false);
                break;
            case 1:
                // 显示对应的fragment
//                if (mFragmentEquity == null) {
//                    mFragmentEquity = new EquityFragment();
//                    transaction.add(R.id.id_content, mFragmentEquity);
//                } else {
//                    transaction.show(mFragmentEquity);
//                }
                if (mFragmentYangMao == null) {
                    mFragmentYangMao = new YangMaoFragment();
                    transaction.add(R.id.id_content, mFragmentYangMao);
                } else {
                    transaction.show(mFragmentYangMao);
                }
                currentFragment = 1;
                // 设置底部Tab图标和文字状态
                mImgEquity.setImageResource(R.mipmap.tabbar_equity_highlight);
                mTextViewEquity.setTextColor(ContextCompat.getColor(this, R.color.red));
                showTopBar(true);
                setTopBarTitle(R.string.news_menu_activity);
                showTopBarBack(false);
                showTopBarRight(false);
                break;
            case 2:
                // 显示对应的fragment
                if (mFragmentAccount == null) {
                    mFragmentAccount = new AccountFragment();
                    transaction.add(R.id.id_content, mFragmentAccount);
                } else {
                    transaction.show(mFragmentAccount);
                }
                currentFragment = 2;
                // 设置底部Tab图标和文字状态
                mImgAccount.setImageResource(R.mipmap.tabbar_account_highlight);
                mTextViewAccount.setTextColor(ContextCompat.getColor(this, R.color.red));
                showTopBar(true);
                setTopBarTitle(R.string.account);
                showTopBarBack(false);
                showTopBarRight(true);
                setTopBarRight(R.string.proccess_search);
                break;
            case 3:
                // 显示对应的fragment
                if (mFragmentProfile == null) {
                    mFragmentProfile = new ProfileFragment();
                    transaction.add(R.id.id_content, mFragmentProfile);
                } else {
                    transaction.show(mFragmentProfile);
                }
                currentFragment = 3;
                // 设置底部Tab图标和文字状态
                mImgProfile.setImageResource(R.mipmap.tabbar_mine_highlight);
                mTextViewProfile.setTextColor(ContextCompat.getColor(this, R.color.red));
                showTopBar(false);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mFragmentNews != null) {
            transaction.hide(mFragmentNews);
        }
        if (mFragmentYangMao != null) {
//            transaction.hide(mFragmentEquity);
            transaction.hide(mFragmentYangMao);
        }
        if (mFragmentAccount != null) {
            transaction.hide(mFragmentAccount);
        }
        if (mFragmentProfile != null) {
            transaction.hide(mFragmentProfile);
        }
    }

    private void initEvent() {
        mTabNews.setOnClickListener(this);
        mTabEquity.setOnClickListener(this);
        mTabAccount.setOnClickListener(this);
        mTabProfile.setOnClickListener(this);
    }

    private void initView() {
        mTabNews = (LinearLayout) findViewById(R.id.id_tab_news);
        mTabEquity = (LinearLayout) findViewById(R.id.id_tab_blog);
        mTabAccount = (LinearLayout) findViewById(R.id.id_tab_focus);
        mTabProfile = (LinearLayout) findViewById(R.id.id_tab_profile);

        mImgNews = (ImageButton) findViewById(R.id.id_tab_news_img);
        mImgEquity = (ImageButton) findViewById(R.id.id_tab_blog_img);
        mImgAccount = (ImageButton) findViewById(R.id.id_tab_focus_img);
        mImgProfile = (ImageButton) findViewById(R.id.id_tab_profile_img);

        mTextViewNews = (TextView) findViewById(R.id.id_tab_news_tv);
        mTextViewEquity = (TextView) findViewById(R.id.id_tab_blog_tv);
        mTextViewAccount = (TextView) findViewById(R.id.id_tab_focus_tv);
        mTextViewProfile = (TextView) findViewById(R.id.id_tab_profile_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_news:
                setSelect(0);
                break;
            case R.id.id_tab_blog:
                setSelect(1);
                break;
            case R.id.id_tab_focus:
                setSelect(2);
                break;
            case R.id.id_tab_profile:
                setSelect(3);
                break;
            case R.id.id_topbar_title:
//                showPopwindow(v);
            default:
                break;
        }
    }

//    private void showPopwindow(View parent) {
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenWidth = dm.widthPixels;
//        int screenHeight = dm.heightPixels;
//        Log.e("TAG", String.valueOf(screenWidth) + "*" + String.valueOf(screenHeight));
//        if (mPopupWindow == null) {
//            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
//            contentView = mLayoutInflater.inflate(R.layout.group_list, null);
//            listView = (ListView) contentView.findViewById(R.id.lv_group);
//            groupAdapter = new GroupAdapter(this, groups);
//            listView.setAdapter(groupAdapter);
//            mPopupWindow = new PopupWindow(contentView, screenWidth / 2, screenHeight / 3);
//        }
//        // 使其聚集
//        mPopupWindow.setFocusable(true);
//        // 设置允许在外点击消失
//        mPopupWindow.setOutsideTouchable(true);
//        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap)null));
//        // showAsDropDown的偏移量参考的是anchor(依靠)控件底部左边(bottom-left corner of the anchor view)
//        mPopupWindow.showAsDropDown(parent, mTextViewTopTitle.getWidth() / 2 - mPopupWindow.getWidth() / 2, 0);
//        listView.setOnItemClickListener(this);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String title = groups.get(position);
//        setTopTitleText(title);
//        switch (currentFragment){
//            case 0:
//                mFragmentNews.setmTopbarTitle(title);
//                break;
//            case 1:
//                mFragmentBlog.setmTopbarTitle(title);
//                break;
//            case 2:
//            case 3:
//                break;
//        }
//        if (mPopupWindow != null) {
//            mPopupWindow.dismiss();
//        }
    }

    public int getCurrentFragment() {
        return currentFragment;
    }


    /**
     * 切换图片和文字为默认状态
     */
    private void resetImgsandText() {
        mImgNews.setImageResource(R.mipmap.tabbar_news);
        mImgEquity.setImageResource(R.mipmap.tabbar_equity);
        mImgAccount.setImageResource(R.mipmap.tabbar_account);
        mImgProfile.setImageResource(R.mipmap.tabbar_mine);

        mTextViewNews.setTextColor(ContextCompat.getColor(this, R.color.tabbar_text_default));
        mTextViewEquity.setTextColor(ContextCompat.getColor(this, R.color.tabbar_text_default));
        mTextViewAccount.setTextColor(ContextCompat.getColor(this, R.color.tabbar_text_default));
        mTextViewProfile.setTextColor(ContextCompat.getColor(this, R.color.tabbar_text_default));
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                // 关闭极光推送
                JPushInterface.stopPush(MainActivity.this);
                activityManagerUtil.appExit();
            } else {
                ToastUtil.showToast("再按一次 退出文龙一账通");
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.id_topbar_right)
    public void onClick() {
        if (AccountUtil.isUserProfileComplete()){ // 完善了详细信息的用户才能使用一账通模块
            startActivity(new Intent(MainActivity.this, WJSApplyStatusActivity.class));
        } else {
            ToastUtil.showToast("登录并完善用户详细信息后方可使用该功能");
        }
    }
}
