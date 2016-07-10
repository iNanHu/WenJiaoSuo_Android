package com.inanhu.wenjiaosuo.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.bean.EquityDataBean;
import com.inanhu.wenjiaosuo.bean.WJSLogo;
import com.inanhu.wenjiaosuo.util.AssetsUtil;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.NumberUtil;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.finalteam.toolsfinal.StringUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by iNanHu on 2016/7/4.
 */
public class EquityListAdapter extends BGARecyclerViewAdapter<EquityDataBean> {

    private Context context;
    private List<WJSLogo> list = new ArrayList<>();

    public EquityListAdapter(Context context, RecyclerView recyclerView, int itemLayoutId) {
        super(recyclerView, itemLayoutId);
        this.context = context;
        initLogo();
    }

    private void initLogo() {
        String json = AssetsUtil.readText(context, "wjs.json");
        list.addAll(JSON.parseArray(json, WJSLogo.class));
    }

    private String getLogoByEid(String eid) {
        for (WJSLogo wjsLogo : list) {
            if (wjsLogo.getEid().equals(eid)) {
                LogUtil.e("logo", wjsLogo.getLogo());
                return URLUtil.getWJSLogoUrl(wjsLogo.getLogo());
            }
        }
        return "logo not find";
    }

    private String getNameByEid(String eid) {
        for (WJSLogo wjsLogo : list) {
            if (wjsLogo.getEid().equals(eid)) {
                return wjsLogo.getName();
            }
        }
        return "----";
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, EquityDataBean equityDataBean) {
        String rate = equityDataBean.getRate();
        String price = equityDataBean.getPrice();
        if (RegexUtil.checkNumber(rate) && RegexUtil.checkNumber(price)) {
            BigDecimal bdRate = new BigDecimal(rate).setScale(2, RoundingMode.HALF_UP);
            int j = bdRate.signum(); // j:-1表示负数，0表示0,1表示正数
            Spanned rateSpanned = null;
            Spanned priceSpanned = null;
            switch (j) {
                case -1:
                    rateSpanned = Html.fromHtml("<font color=green>" + bdRate.toString() + "%" + "</font>");
                    priceSpanned = Html.fromHtml("<font color=green>" + price + "</font>");
                    break;
                case 0:
                    rateSpanned = Html.fromHtml(bdRate.toString() + "%");
                    priceSpanned = Html.fromHtml(price);
                    break;
                case 1:
                    rateSpanned = Html.fromHtml("<font color=red>" + "+" + bdRate.toString() + "%" + "</font>");
                    priceSpanned = Html.fromHtml("<font color=red>" + price + "</font>");
                    break;
            }
            bgaViewHolderHelper.setText(R.id.tv_wjs_rate, rateSpanned);
            bgaViewHolderHelper.setText(R.id.tv_wjs_price, priceSpanned);
        } else {
            bgaViewHolderHelper.setText(R.id.tv_wjs_rate, rate);
            bgaViewHolderHelper.setText(R.id.tv_wjs_price, price);
        }

        ImageLoader.with(getLogoByEid(equityDataBean.getEid()), (CircleImageView) bgaViewHolderHelper.getView(R.id.iv_wjs_logo));

        bgaViewHolderHelper
                .setText(R.id.tv_wjs_up, Html.fromHtml("<font color=red>" + equityDataBean.getUp() + "</font>只"))
                .setText(R.id.tv_wjs_flat, equityDataBean.getFlat() + "只")
                .setText(R.id.tv_wjs_down, Html.fromHtml("<font color=green>" + equityDataBean.getDown() + "</font>只"))
                .setText(R.id.tv_wjs_total, equityDataBean.getTotal() + "只")
                .setText(R.id.tv_wjs_money, NumberUtil.numberToYi(equityDataBean.getMoney()))
                .setText(R.id.tv_wjs_count, NumberUtil.numberToWan(equityDataBean.getCount()))
                .setText(R.id.tv_wjs_top, equityDataBean.getTop())
                .setText(R.id.tv_wjs_bottom, equityDataBean.getBottom())
                .setText(R.id.tv_wjs_name, getNameByEid(equityDataBean.getEid()));
    }
}
