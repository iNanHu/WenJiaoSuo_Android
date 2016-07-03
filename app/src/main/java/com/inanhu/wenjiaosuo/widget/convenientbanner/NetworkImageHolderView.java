package com.inanhu.wenjiaosuo.widget.convenientbanner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.Holder;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.squareup.picasso.Picasso;

/**
 * banner网络图片加载
 * <p/>
 * Created by zzmiao on 2015/10/22.
 */
public class NetworkImageHolderView implements Holder<String> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
//        Picasso.with(context).load(data).into(imageView);
        ImageLoader.with(data, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("点击了第" + position + "个");
            }
        });
    }
}
