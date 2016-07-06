package com.inanhu.wenjiaosuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 完善用户资料（第一步）
 * <p>
 * Created by Jason on 2016/7/6.
 */
public class ProfileCompleteOneActivity extends BaseActivity {

    @OnClick(R.id.btn_complete_next)
    public void toNext(){
        startActivity(new Intent(ProfileCompleteOneActivity.this, ProfileCompleteTwoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete_one);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.profile_complete);
    }

}
