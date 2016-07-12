package com.inanhu.wenjiaosuo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.base.Upfile;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageUtil;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 上传文件
    private Map<String, File> files = new HashMap<>();
    // 保存文件路径
    private Map<String, String> imageUrls = new HashMap<>();

    @OnClick(R.id.btn_complete_commit)
    public void toCommit() {
        RequestParams params = new RequestParams(ProfileCompleteTwoActivity.this);
        params.addFormDataPart(Constant.RequestKey.UPFILE, files.get(Constant.RequestKey.CERTIFICATE_FRONT));
        HttpRequest.post(URLUtil.CommonApi.UPFILE, params, new BaseHttpRequestCallback() {

            @Override
            public void onStart() {
                showProgressDialog("证件照正面正在上传中");
            }

            @Override
            public void onResponse(String response, Headers headers) {
                LogUtil.e(TAG, response);
                closeProgressDialog();
                ApiResponse<Upfile> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<Upfile>>() {
                }.getType());
                if (rsp.isSuccess()) { // 上传成功
                    Upfile upfile = rsp.getData();
                    if (upfile != null) {
                        imageUrls.put(Constant.RequestKey.CERTIFICATE_FRONT, upfile.getUrl());
                    }
                    RequestParams params = new RequestParams(ProfileCompleteTwoActivity.this);
                    params.addFormDataPart(Constant.RequestKey.UPFILE, files.get(Constant.RequestKey.CERTIFICATE_BACK));
                    HttpRequest.post(URLUtil.CommonApi.UPFILE, params, new BaseHttpRequestCallback() {
                        @Override
                        public void onStart() {
                            showProgressDialog("证件照反面正在上传中");
                        }

                        @Override
                        public void onResponse(String response, Headers headers) {
                            LogUtil.e(TAG, response);
                            closeProgressDialog();
                            ApiResponse<Upfile> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<Upfile>>() {
                            }.getType());
                            if (rsp.isSuccess()) {
                                Upfile upfile = rsp.getData();
                                if (upfile != null) {
                                    imageUrls.put(Constant.RequestKey.CERTIFICATE_BACK, upfile.getUrl());
                                }
                                RequestParams params = new RequestParams(ProfileCompleteTwoActivity.this);
                                params.addFormDataPart(Constant.RequestKey.UPFILE, files.get(Constant.RequestKey.BANK_CARD));
                                HttpRequest.post(URLUtil.CommonApi.UPFILE, params, new BaseHttpRequestCallback() {
                                    @Override
                                    public void onStart() {
                                        showProgressDialog("银行卡正面照正在上传中");
                                    }

                                    @Override
                                    public void onResponse(String response, Headers headers) {
                                        LogUtil.e(TAG, response);
                                        closeProgressDialog();
                                        ApiResponse<Upfile> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<Upfile>>() {
                                        }.getType());
                                        if (rsp.isSuccess()){ // 至此文件上传结束
                                            Upfile upfile = rsp.getData();
                                            if (upfile != null) {
                                                imageUrls.put(Constant.RequestKey.BANK_CARD, upfile.getUrl());
                                                commit();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 最终提交
     *
     */
    private void commit(){
        ToastUtil.showToast("提交啦");
        RequestParams params = new RequestParams(this);
        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN));
//        params.addFormDataPart(Constant.RequestKey.UID, );
        params.addFormDataPart(Constant.RequestKey.REALNAME, (String) GlobalValue.getInstance().getGlobal(MessageFlag.REALNAME));
        params.addFormDataPart(Constant.RequestKey.SEX, (int) GlobalValue.getInstance().getGlobal(MessageFlag.GENDER));
        params.addFormDataPart(Constant.RequestKey.CERTIFICATE_TYPE, (String) GlobalValue.getInstance().getGlobal(MessageFlag.CERTIFICATE_TYPE));
        params.addFormDataPart(Constant.RequestKey.CERTIFICATE_NUMBER, (String) GlobalValue.getInstance().getGlobal(MessageFlag.CERTIFICATE_NUMBER));
        params.addFormDataPart(Constant.RequestKey.TELPHONE, (String) GlobalValue.getInstance().getGlobal(MessageFlag.TELPHONE));
        params.addFormDataPart(Constant.RequestKey.ADDRESS, (String) GlobalValue.getInstance().getGlobal(MessageFlag.ADDRESS));
        params.addFormDataPart(Constant.RequestKey.BANK, (String) GlobalValue.getInstance().getGlobal(MessageFlag.BANK));
        params.addFormDataPart(Constant.RequestKey.ACCOUNT_NUMBER, (String) GlobalValue.getInstance().getGlobal(MessageFlag.ACCOUNT_NUMBER));
        params.addFormDataPart(Constant.RequestKey.BANK_LOCATION, (String) GlobalValue.getInstance().getGlobal(MessageFlag.BANK_LOCATION));
        params.addFormDataPart(Constant.RequestKey.BRANCH_NAME, (String) GlobalValue.getInstance().getGlobal(MessageFlag.BRANCH_NAME));
        params.addFormDataPart(Constant.RequestKey.CERTIFICATE_FRONT_IMAGE, imageUrls.get(Constant.RequestKey.CERTIFICATE_FRONT));
        params.addFormDataPart(Constant.RequestKey.CERTIFICATE_BACK_IMAGE, imageUrls.get(Constant.RequestKey.CERTIFICATE_BACK));
        params.addFormDataPart(Constant.RequestKey.BANK_CARD_IMAGE, imageUrls.get(Constant.RequestKey.BANK_CARD));
        HttpEngine.doPost(URLUtil.UserApi.COMPLETE, params, new BaseHttpRequestCallback(){
            @Override
            public void onStart() {
                showProgressDialog("完整资料上传中");
            }

            @Override
            public void onResponse(String response, Headers headers) {
                closeProgressDialog();
                LogUtil.e(TAG, response);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete_two);
        ButterKnife.bind(this);
        showTopBarBack(true);
        setTopBarTitle(R.string.profile_complete);
    }

    /**
     * 选取照片
     */
    private void toSelectPic(final ImageView imageView) {
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

                                    String path = resultList.get(0).getPhotoPath();
                                    if (!TextUtils.isEmpty(path)) {
                                        saveImage(imageView, path); // 保存文件待上传
                                        Bitmap bitmap = ImageUtil.getSmallBitmap(path);
                                        if (bitmap != null) {
                                            imageView.setImageBitmap(bitmap);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onHanlderFailure(int requestCode, String errorMsg) {
                                ToastUtil.showToast(errorMsg);
                            }
                        });

                        break;
                    case 1:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, new GalleryFinal.OnHanlderResultCallback() {
                                    @Override
                                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                        if (resultList != null) {
                                            for (PhotoInfo photoInfo : resultList) {
                                                LogUtil.e(TAG, photoInfo.getPhotoPath());
                                            }

                                            String path = resultList.get(0).getPhotoPath();
                                            if (!TextUtils.isEmpty(path)) {
                                                saveImage(imageView, path); // 保存文件待上传
                                                Bitmap bitmap = ImageUtil.getSmallBitmap(path);
                                                if (bitmap != null) {
                                                    imageView.setImageBitmap(bitmap);
                                                }
                                            }
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

    @OnClick({R.id.iv_certificate_front_image, R.id.iv_certificate_back_image, R.id.iv_bank_card_image})
    public void onClick(View view) {
        toSelectPic((ImageView) view);
    }

    private void saveImage(ImageView imageView, String filePath) {
        switch (imageView.getId()) {
            case R.id.iv_certificate_front_image:
                files.put(Constant.RequestKey.CERTIFICATE_FRONT, new File(filePath));
                break;
            case R.id.iv_certificate_back_image:
                files.put(Constant.RequestKey.CERTIFICATE_BACK, new File(filePath));
                break;
            case R.id.iv_bank_card_image:
                files.put(Constant.RequestKey.BANK_CARD, new File(filePath));
                break;
        }
    }
}
