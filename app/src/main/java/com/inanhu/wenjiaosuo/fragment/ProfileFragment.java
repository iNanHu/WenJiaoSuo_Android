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
        new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .withTitle("友盟分享")
                .withText("来自友盟分享面板")
                .withMedia(image)
                .withTargetUrl(url)
                .setCallback(umShareListener)
                //.withShareBoardDirection(view, Gravity.TOP|Gravity.LEFT)
                .open();
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


//    private CircleImageView civProfile;
//    private TextView tvNickName;
//    private TextView tvIntro;
//    private TextView tvFollowing;
//    private TextView tvFans;
//    private Profile profile;
//


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
//        initView();
//        initEvent();
//        initData();
        LogUtil.e("ProfileFragment", "===onCreateView===");
        return view;
    }

    @OnClick(R.id.checkbox)
    public void onClick(CheckBox checkBox) {
        if (!checkBox.isChecked()) {
            ToastUtil.showToast("您关闭了消息提醒");
        }
    }
//
//    private void initData() {
//        /**
//         * 获取并解析出个人信息
//         */
//        new AsyncTask<String, Void, Void>() {
//            @Override
//            protected Void doInBackground(String... params) {
//                String html = HttpUtil.sendGet(params[0]);
//                if (html == null) {
//                    CommonUtil.showToast(getActivity(), "获取信息失败");
//                }
//                profile = DataUtil.getProfile(html);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void voids) {
//                Picasso.with(getActivity()).load(profile.getPic()).into(civProfile);
//                tvNickName.setText(profile.getNick_name());
//                tvIntro.setText(profile.getIntro());
//                tvFollowing.setText(profile.getFollowing());
//                tvFans.setText(profile.getFans());
//            }
//        }.execute(URLUtil.MYCSDN);
//    }
//
//    private void initEvent() {
//        view.findViewById(R.id.ll_profile_following).setOnClickListener(this);
//        view.findViewById(R.id.rl_profile_blog).setOnClickListener(this);
//        view.findViewById(R.id.rl_profile_collect).setOnClickListener(this);
//        view.findViewById(R.id.rl_profile_set).setOnClickListener(this);
//    }
//
//    private void initView() {
//        civProfile = (CircleImageView) view.findViewById(R.id.civ_profile);
//        tvNickName = (TextView) view.findViewById(R.id.tv_profile_nick_name);
//        tvIntro = (TextView) view.findViewById(R.id.tv_profile_intro);
//        tvFollowing = (TextView) view.findViewById(R.id.tv_profile_following);
//        tvFans = (TextView) view.findViewById(R.id.tv_profile_fans);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ll_profile_following:
//                startActivity(new Intent(getActivity(), FollowActivity.class));
//                break;
//            case R.id.rl_profile_blog:
////                CommonUtil.showToast(getActivity(), URLUtil.BLOG + profile.getUsername());
//                Intent intent = new Intent(getActivity(), BloggerShowActivity.class);
//                intent.putExtra(BloggerShowActivity.USERNAME, profile.getUsername());
//                startActivity(intent);
//                break;
//            case R.id.rl_profile_collect:
//
//                break;
//            case R.id.rl_profile_set:
//
//                break;
//        }
//    }
}
