package com.inanhu.wenjiaosuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.AboutUsActivity;
import com.inanhu.wenjiaosuo.activity.LoginActivity;
import com.inanhu.wenjiaosuo.activity.ProfileCompleteOneActivity;
import com.inanhu.wenjiaosuo.activity.ProfileCompleteTwoActivity;
import com.inanhu.wenjiaosuo.activity.ShareActivity;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 个人中心界面
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends BaseFragment /*implements View.OnClickListener */ {

    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;

    @OnClick(R.id.guanzhu_teacher_btn)
    public void toGuanzhuTeacher() {
        startActivity(new Intent(getActivity(), ProfileCompleteOneActivity.class));
    }

    @OnClick(R.id.civ_avatar)
    public void toLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R.id.abouts_us_btn)
    public void toAboutUs() {
        startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    @OnClick(R.id.check_app_version)
    public void toCheckAppVersion() {
        ToastUtil.showToast("已经是最新版本啦");
    }

    UMImage image = new UMImage(getActivity(), "http://www.umeng.com/images/pic/social/integrated_3.png");
    String url = "http://www.umeng.com";

    @OnClick(R.id.to_share)
    public void toShare() {
//        new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
//                .withTitle("文龙一账通")
//                .withText("来自文龙一账通的分享")
//                .withMedia(image)
//                .withTargetUrl(url)
//                .setCallback(umShareListener)
//                //.withShareBoardDirection(view, Gravity.TOP|Gravity.LEFT)
//                .open();
        startActivity(new Intent(getActivity(), ShareActivity.class));
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                ToastUtil.showToast("收藏成功啦");
            } else {
                ToastUtil.showToast("分享成功啦");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast("分享失败啦");
            if (t != null) {
                LogUtil.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast("分享取消了");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("ProfileFragment", "===onCreate===");
    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        ImageLoader.with(R.mipmap.ic_launcher, "http://www.baidu.com/img/bd_logo1.png", civAvatar);
        LogUtil.e("ProfileFragment", "===onCreateView===");
        return view;
    }

    @OnClick(R.id.checkbox)
    public void onClick(CheckBox checkBox) {
        if (!checkBox.isChecked()) {
            ToastUtil.showToast("您关闭了消息提醒");
        }
    }

}
