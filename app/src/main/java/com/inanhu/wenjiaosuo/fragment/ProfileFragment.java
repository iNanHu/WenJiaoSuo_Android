package com.inanhu.wenjiaosuo.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.activity.LoginActivity;
import com.inanhu.wenjiaosuo.base.BaseFragment;
import com.inanhu.wenjiaosuo.util.ImageLoader;
import com.inanhu.wenjiaosuo.util.LogUtil;

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
    private View view;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;

    @OnClick(R.id.civ_avatar)
    public void toLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

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
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        ImageLoader.with(R.mipmap.ic_launcher ,"https://avatars1.githubusercontent.com/u/5058324?v=3&u=06df9935b0f3e13c28f000fafd7ca59bdef2594d&s=140", civAvatar);
//        initView();
//        initEvent();
//        initData();
        LogUtil.e("ProfileFragment", "===onCreateView===");
        return view;
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
