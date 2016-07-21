package com.inanhu.wenjiaosuo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.WebviewActivity;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.bean.WJSBean;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Jason on 2016/7/21.
 */
public class WJSListAdapter extends BGARecyclerViewAdapter<WJSBean> {

    private Context context;

    public WJSListAdapter(Context context, RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_wjs);
        this.context = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int position, final WJSBean wjsBean) {
        ImageLoader.with(R.mipmap.nanjing, wjsBean.getLogo(), (ImageView) bgaViewHolderHelper.getView(R.id.iv_wjs_logo));
        bgaViewHolderHelper.setText(R.id.tv_wjs_name, wjsBean.getName());
        final String onkey = wjsBean.getOnekey();
        String text = "立即开户";
        if ("1".equals(onkey)) { // 一键开户
            text = "一键开户";
        } else if ("2".equals(onkey)) { // 立即开户
            text = "立即开户";
        }
        bgaViewHolderHelper.setText(R.id.tv_wjs_apply, text);
        bgaViewHolderHelper.getTextView(R.id.tv_wjs_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(onkey)) { // 一键开户
                    //TODO 一键开户请求
                } else if ("2".equals(onkey)) { // 立即开户
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra(MessageFlag.WEBVIEW_TOPBAR_TITLE, wjsBean.getName() + "开户");
                    intent.putExtra(MessageFlag.WEBVIEW_LOAD_URL, wjsBean.getLink());
                    context.startActivity(intent);
                }

            }
        });
    }
}
