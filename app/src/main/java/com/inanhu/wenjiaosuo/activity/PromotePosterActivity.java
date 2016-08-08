package com.inanhu.wenjiaosuo.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.FileDownloadCallback;

/**
 * 推广海报界面
 * <p/>
 * Created by Jason on 2016/8/7.
 */
public class PromotePosterActivity extends BaseActivity {

    @BindView(R.id.iv_poster)
    ImageView ivPoster;

    private String posterUrl = null;
    private File saveFile = new File("/sdcard/poster.jpeg");
    // 图片文件是否下载成功
    private boolean downloadFileSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(true);
        setTopBarRight("保存");
        setTopBarTitle(R.string.promote_poster);
        posterUrl = getIntent().getStringExtra(MessageFlag.POSTER_URL);
        if (!TextUtils.isEmpty(posterUrl)) {
            HttpEngine.download(posterUrl, saveFile, new FileDownloadCallback() {
                @Override
                public void onStart() {
                    showProgressDialog("海报下载中...请稍后", false);
                }

                @Override
                public void onFailure() {
                    downloadFileSuccess = false;
                    ToastUtil.showToast("下载失败");
                    closeProgressDialog();
                }

                @Override
                public void onDone() {
                    downloadFileSuccess = true;
                    closeProgressDialog();
                    ivPoster.setImageURI(Uri.fromFile(saveFile));
                }
            });
        }
    }

    @OnClick(R.id.id_topbar_right)
    public void onClick() {
        if (downloadFileSuccess){
            ToastUtil.showToast("海报保存成功");
        }
    }
}
