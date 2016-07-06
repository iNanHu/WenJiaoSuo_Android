package com.inanhu.wenjiaosuo.activity;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 完善用户资料（第二步，提交）
 * <p/>
 * Created by Jason on 2016/7/6.
 */
public class ProfileCompleteTwoActivity extends BaseActivity {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    @BindView(R.id.iv_show)
    ImageView ivShow;

    @OnClick(R.id.btn_complete_commit)
    public void toCommit() {
        final AlertView dialog = new AlertView(null, null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                switch (position) {
                    case 0:
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, new GalleryFinal.OnHanlderResultCallback() {
                            @Override
                            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                if (resultList != null) {
                                    for (PhotoInfo photoInfo : resultList) {
                                        LogUtil.e(TAG, photoInfo.getPhotoPath());
                                    }
                                    Bitmap bitmap = BitmapFactory.decodeFile(resultList.get(0).getPhotoPath());
                                    ivShow.setImageBitmap(bitmap);
                                }
                            }

                            @Override
                            public void onHanlderFailure(int requestCode, String errorMsg) {
                                ToastUtil.showToast(errorMsg);
                            }
                        });

                        break;
                    case 1:
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, new GalleryFinal.OnHanlderResultCallback() {
                                    @Override
                                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                        if (resultList != null) {
                                            for (PhotoInfo photoInfo : resultList) {
                                                LogUtil.e(TAG, photoInfo.getPhotoPath());
                                            }
                                            Bitmap bitmap = BitmapFactory.decodeFile(resultList.get(0).getPhotoPath());
                                            ivShow.setImageBitmap(bitmap);
                                        }
                                    }

                                    @Override
                                    public void onHanlderFailure(int requestCode, String errorMsg) {
                                        ToastUtil.showToast(errorMsg);
                                    }
                                });
                            }
                        }, 1000);
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete_two);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.profile_complete);
    }

}
