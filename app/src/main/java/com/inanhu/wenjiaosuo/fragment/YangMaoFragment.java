package com.inanhu.wenjiaosuo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * 羊毛专区
 * <p/>
 * Created by Jason on 2016/8/10.
 */
public class YangMaoFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yangmao, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void fetchData() {

    }
}
