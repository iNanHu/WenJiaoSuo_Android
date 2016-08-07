package com.inanhu.wenjiaosuo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员中心
 * <p/>
 * Created by Jason on 2016/8/7.
 */
public class MemberCenterActivity extends BaseActivity {


    @BindView(R.id.tv_condition_of_copper)
    TextView tvConditionOfCopper;
    @BindView(R.id.tv_condition_of_silver)
    TextView tvConditionOfSilver;
    @BindView(R.id.tv_condition_of_gold)
    TextView tvConditionOfGold;
    @BindView(R.id.tv_condition_of_diamond)
    TextView tvConditionOfDiamond;
    @BindView(R.id.tv_show_level)
    TextView tvShowLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.member_center);
    }

    @OnClick({R.id.ll_level_copper, R.id.ll_level_silver, R.id.ll_level_gold, R.id.ll_level_diamond})
    public void onClick(View view) {
        hideAllLevelCondition();
        switch (view.getId()) {
            case R.id.ll_level_copper:
                tvShowLevel.setText("铜牌经纪人");
                tvConditionOfCopper.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_level_silver:
                tvShowLevel.setText("银牌经纪人");
                tvConditionOfSilver.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_level_gold:
                tvShowLevel.setText("金牌经纪人");
                tvConditionOfGold.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_level_diamond:
                tvShowLevel.setText("钻石合伙人");
                tvConditionOfDiamond.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 隐藏等级条件区域
     */
    private void hideAllLevelCondition() {
        tvConditionOfCopper.setVisibility(View.GONE);
        tvConditionOfSilver.setVisibility(View.GONE);
        tvConditionOfGold.setVisibility(View.GONE);
        tvConditionOfDiamond.setVisibility(View.GONE);
    }
}
