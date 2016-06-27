package com.inanhu.wenjiaosuo.util.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * 封装的图片加载类
 * <p/>
 * Created by iNanHu on 2016/6/24.
 */
public class ImageLoader {

    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    public static void with(String imageUrl, ImageView imageView) {
        with(context, imageUrl, imageView);
    }

    public static void with(Context context, String imageUrl, ImageView imageView) {
        Picasso.with(context).load(imageUrl).into(imageView);
    }
}
