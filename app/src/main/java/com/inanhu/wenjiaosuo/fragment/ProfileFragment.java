package com.inanhu.wenjiaosuo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.widget.tabview.ITabClickListener;

/**
 * Created by yx on 16/4/3.
 */
public class ProfileFragment extends BaseFragment implements ITabClickListener {
    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_layout, container, false);
        return view;
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
