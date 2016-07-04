package com.inanhu.wenjiaosuo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.util.LogUtil;


/**
 * 行情界面
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class EquityFragment extends BaseFragment{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG, "===onCreate===");
    }

    @Override
    public void fetchData() {
        LogUtil.e(TAG, "===fetchData===");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equity, container, false);
        LogUtil.e(TAG, "===onCreateView===");
        return view;
    }

}
