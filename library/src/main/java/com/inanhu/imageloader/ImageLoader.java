package com.inanhu.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * 封装的图片加载类
 *
 * Created by iNanHu on 2016/6/24.
 */
public class ImageLoader {
    public static void with(Context context, String imageUrl, ImageView imageView) {
        Picasso.with(context).load(imageUrl).into(imageView);
    }
}
