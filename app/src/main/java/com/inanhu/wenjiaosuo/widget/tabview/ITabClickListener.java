package com.inanhu.wenjiaosuo.widget.tabview;

import com.inanhu.wenjiaosuo.base.BaseFragment;

/**
 * Created by yx on 16/4/6.
 */
public interface ITabClickListener {


    void onMenuItemClick();

    BaseFragment getFragment();
}
