package com.inanhu.wenjiaosuo.widget.convenientbanner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.Holder;
import com.inanhu.wenjiaosuo.base.Banner;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * banner网络图片加载
 * <p/>
 * Created by zzmiao on 2015/10/22.
 */
public class NetworkImageHolderView implements Holder<Banner> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, final Banner banner) {
        ImageLoader.with(banner.getImage(), imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(banner.getLink());
            }
        });
    }
}
