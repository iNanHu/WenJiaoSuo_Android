package com.inanhu.wenjiaosuo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inanhu.wenjiaosuo.util.LogUtil;


/**
 * 关注Fragment
 *
 * Created by zzmiao on 2015/9/23.
 */
public class FocusFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("FocusFragment", "===onCreate===");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.e("FocusFragment", "===onCreateView===");
        return null;
    }
}
