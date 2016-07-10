package com.inanhu.wenjiaosuo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * 完善用户资料（第二步，提交）
 * <p/>
 * Created by Jason on 2016/7/6.
 */
public class ProfileCompleteTwoActivity extends BaseActivity {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    @BindView(R.id.iv_certificate_front_image)
    ImageView ivCertificateFrontImage;
    @BindView(R.id.iv_certificate_back_image)
    ImageView ivCertificateBackImage;
    @BindView(R.id.iv_bank_card_image)
    ImageView ivBankCardImage;

    private File file = null;

    @OnClick(R.id.btn_complete_commit)
    public void toCommit() {
        // 上传文件
        RequestParams params = new RequestParams(this);
        params.addFormDataPart(Constant.Key.UPFILE, file);
        HttpRequest.post(URLUtil.CommonApi.UPFILE, params, new BaseHttpRequestCallback(){
            @Override
            protected void onSuccess(Headers headers, Object o) {
                LogUtil.e(TAG, o.toString());
            }
        });
        // 提交详细资料
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete_two);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.profile_complete);
    }

    // /storage/emulated/0/DCIM/GalleryFinal/IMG20160710225318.jpg

    /**
     * 选取照片
     */
    private void toSelectPic(final ImageView imageView) {
//        final AlertView dialog = new AlertView(null, null, "取消", null,
//                new String[]{"拍照", "从相册中选择"},
//                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
//            public void onItemClick(Object o, int position) {
//                switch (position) {
//                    case 0:
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, new GalleryFinal.OnHanlderResultCallback() {
                            @Override
                            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                if (resultList != null) {
                                    for (PhotoInfo photoInfo : resultList) {
                                        LogUtil.e(TAG, photoInfo.getPhotoPath());
                                        file = new File(photoInfo.getPhotoPath());
                                    }
                                    Bitmap bitmap = BitmapFactory.decodeFile(resultList.get(0).getPhotoPath());
                                    imageView.setImageBitmap(bitmap);
                                }
                            }

                            @Override
                            public void onHanlderFailure(int requestCode, String errorMsg) {
                                ToastUtil.showToast(errorMsg);
                            }
                        });

//                        break;
//                    case 1:
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, new GalleryFinal.OnHanlderResultCallback() {
//                                    @Override
//                                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
//                                        if (resultList != null) {
//                                            for (PhotoInfo photoInfo : resultList) {
//                                                LogUtil.e(TAG, photoInfo.getPhotoPath());
//                                            }
//                                            Bitmap bitmap = BitmapFactory.decodeFile(resultList.get(0).getPhotoPath());
//                                            imageView.setImageBitmap(bitmap);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onHanlderFailure(int requestCode, String errorMsg) {
//                                        ToastUtil.showToast(errorMsg);
//                                    }
//                                });
//                            }
//                        }, 1000);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        dialog.show();
    }

    @OnClick({R.id.iv_certificate_front_image, R.id.iv_certificate_back_image, R.id.iv_bank_card_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_certificate_front_image:
                toSelectPic(ivCertificateFrontImage);
                break;
            case R.id.iv_certificate_back_image:
                toSelectPic(ivCertificateBackImage);
                break;
            case R.id.iv_bank_card_image:
                toSelectPic(ivBankCardImage);
                break;
        }
    }
}
