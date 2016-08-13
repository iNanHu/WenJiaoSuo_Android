package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.AbstractBaseActivity;
import com.inanhu.wenjiaosuo.base.ApiResponse;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.Constant;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.base.Upfile;
import com.inanhu.wenjiaosuo.bean.UserInfo;
import com.inanhu.wenjiaosuo.fragment.ProfileFragment;
import com.inanhu.wenjiaosuo.util.HttpEngine;
import com.inanhu.wenjiaosuo.util.ImageUtil;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.PicassoImageLoader;
import com.inanhu.wenjiaosuo.util.SPUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.inanhu.wenjiaosuo.util.URLUtil;

import java.io.File;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Headers;

/**
 * 用户详细信息页面（在此界面可以登出）
 * <p/>
 * Created by iNanHu on 2016/7/22.
 */
public class UserInfoDetailActivity extends AbstractBaseActivity {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    @BindView(R.id.head_nickname_tv)
    TextView headNicknameTv;
    @BindView(R.id.head_account_tv)
    TextView headAccountTv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.binding_phone_tv)
    TextView bindingPhoneTv;
    @BindView(R.id.head_img)
    ImageView headImg;


    // 当前登录用户
    private UserInfo userInfo;
    // 是否成功修改头像
    private boolean changeAvatarSuccess;

    @OnClick(R.id.head_img_ll)
    public void toChangeAvatar() {
        final AlertView dialog = new AlertView("修改头像", null, "取消", null,
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
                                        uploadAvatar(path); // 保存文件待上传
                                        Bitmap bitmap = ImageUtil.getSmallBitmap(path);
                                        if (bitmap != null) {
                                            headImg.setImageBitmap(bitmap);
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
                                                uploadAvatar(path); // 保存文件待上传
                                                Bitmap bitmap = ImageUtil.getSmallBitmap(path);
                                                if (bitmap != null) {
                                                    headImg.setImageBitmap(bitmap);
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

    /**
     * 上传头像
     *
     * @param path
     */
    private void uploadAvatar(String path) {
        if (!TextUtils.isEmpty(path)) {
            // 上传图片
            RequestParams params = new RequestParams(this);
            params.addFormDataPart(Constant.RequestKey.UPFILE, new File(path));
            HttpRequest.post(URLUtil.CommonApi.UPFILE, params, new BaseHttpRequestCallback() {

                @Override
                public void onStart() {
                    showProgressDialog("头像保存中", false);
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
                            String imageUrl = upfile.getUrl();
                            RequestParams params = new RequestParams(UserInfoDetailActivity.this);
                            params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
                            params.addFormDataPart(Constant.RequestKey.AVATAR, imageUrl);
                            HttpEngine.doPost(URLUtil.UserApi.CHANGE_AVATAR, params, new BaseHttpRequestCallback() {
                                @Override
                                public void onStart() {
                                    showProgressDialog("头像上传中", false);
                                }

                                @Override
                                public void onResponse(String response, Headers headers) {
                                    closeProgressDialog();
                                    LogUtil.e(TAG, response);
                                    ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                                    }.getType());
                                    if (rsp.isSuccess()) {
                                        changeAvatarSuccess = true;
                                        ToastUtil.showToast("修改头像成功");
                                    } else {
                                        changeAvatarSuccess = false;
                                        ToastUtil.showToast("修改头像失败");
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    closeProgressDialog();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFinish() {
                    closeProgressDialog();
                }
            });
        } else {
            ToastUtil.showToast("图片地址无效");
        }
    }

    private void initGallery() {
        // 设置主题
        ThemeConfig theme = ThemeConfig.CYAN;
        // 配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(false)
                .setForceCrop(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        // 配置imageloader
        ImageLoader imageLoader = new PicassoImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageLoader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.account_manage);
        initData();
        // 配置Gallery
        initGallery();
    }

    @Override
    protected void onBackPress() {
        if (changeAvatarSuccess) {
            // 返回
            setResult(ProfileFragment.REQUEST_CODE_USER_MANAGE, new Intent().putExtra(MessageFlag.CHANGE_AVATAR_SUCCESS, "true"));
        }
    }

    private void initData() {
        changeAvatarSuccess = false; // 默认不修改头像

        userInfo = (UserInfo) GlobalValue.getInstance().getGlobal(MessageFlag.CURRENT_USER_INFO, null);
        if (userInfo != null) {
            // 隐藏昵称
            headNicknameTv.setVisibility(View.INVISIBLE);
            headAccountTv.setText(TextUtils.isEmpty(userInfo.getUsername()) ? "" : userInfo.getUsername());
            String sex = userInfo.getSex();
            if (!TextUtils.isEmpty(sex)) {
                if ("1".equals(sex)) {
                    sexTv.setText("男");
                } else if ("2".equals(sex)) {
                    sexTv.setText("女");
                }
            }
            bindingPhoneTv.setText(TextUtils.isEmpty(userInfo.getUsername()) ? "" : userInfo.getUsername());
            String avatar = userInfo.getAvatar();
            if (!TextUtils.isEmpty(avatar)) {
                com.inanhu.wenjiaosuo.util.ImageLoader.with(R.mipmap.avatar_login_default, avatar, headImg);
            }
        }
    }

    @OnClick(R.id.logout_btn)
    public void toLogout() {
        // 账户登出
        RequestParams params = new RequestParams(this);
        params.addHeader(Constant.RequestKey.ACCESS_TOKEN, (String) GlobalValue.getInstance().getGlobal(Constant.RequestKey.ACCESS_TOKEN, ""));
        HttpEngine.doPost(URLUtil.UserApi.LOGOUT, params, new BaseHttpRequestCallback() {
            @Override
            public void onResponse(String response, Headers headers) {
                ApiResponse<String> rsp = new Gson().fromJson(response, new TypeToken<ApiResponse<String>>() {
                }.getType());
                String data = rsp.getData();
                if (rsp.isSuccess()) { // 登出成功
                    // 清除当前用户的token
                    GlobalValue.getInstance().deleteGlobal(Constant.RequestKey.ACCESS_TOKEN);
                    // 清除当前用户对象
                    GlobalValue.getInstance().deleteGlobal(MessageFlag.CURRENT_USER_INFO);
                    // 清除当前文件中的免登录账号和密码
                    SPUtil.remove(UserInfoDetailActivity.this, Constant.SPKey.USERNAME);
                    SPUtil.remove(UserInfoDetailActivity.this, Constant.SPKey.PASSWORD);
                    // 登出后清除之前的别名
                    JPushInterface.setAlias(UserInfoDetailActivity.this, "", new TagAliasCallback() {
                        @Override
                        public void gotResult(int code, String alias, Set<String> tags) {
                            switch (code) {
                                case 0: // 设置成功
                                    LogUtil.e(TAG, "取消别名成功");
                                    break;

                                case 6002: // 设置超时
                                    LogUtil.e(TAG, "取消别名超时");
                                    break;

                                default:
                                    LogUtil.e(TAG, "取消别名出错-" + code);
                            }
                        }
                    });
                    // 返回
                    setResult(ProfileFragment.REQUEST_CODE_USER_MANAGE, new Intent().putExtra(MessageFlag.LOGOUT_SUCCESS, "true"));
                    activityManagerUtil.finishActivity(UserInfoDetailActivity.this);
                } else {
                    ToastUtil.showToast("登出失败 " + data);
                }
            }
        });
    }
}
