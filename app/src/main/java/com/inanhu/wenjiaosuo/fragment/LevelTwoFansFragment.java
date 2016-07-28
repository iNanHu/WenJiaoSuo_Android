package com.inanhu.wenjiaosuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二度粉丝
 * <p/>
 * Created by Jason on 2016/7/28.
 */
public class LevelTwoFansFragment extends Fragment {

    @BindView(R.id.tv_show)
    TextView tvShow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fans, container, false);
        ButterKnife.bind(this, view);
        tvShow.setText("二度粉丝");
        return view;
    }
}
