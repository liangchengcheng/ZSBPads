package com.lcc.msdq.personinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.github.johnpersano.supertoasts.SuperToast;
import com.lcc.db.test.UserInfo;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.description.user.UserProfileActivity;
import com.lcc.msdq.favorite.FavoriteList;
import com.lcc.msdq.login.LoginActivity;
import com.lcc.msdq.R;
import com.lcc.msdq.login.ResetPasswordActivity;
import com.lcc.msdq.fabu.FabuList;
import com.lcc.msdq.setting.SettingActivity;
import com.lcc.utils.CoCoinToast;
import com.lcc.utils.ShareUtil;

import de.greenrobot.event.EventBus;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.view.glide.GlideCircleTransform;
import zsbpj.lccpj.view.toast.SuperCustomToast;

public class PersonInfoIndexFragment extends Fragment implements View.OnClickListener {
    //头像
    private ImageView iv_more;
    //用户名
    private TextView tv_username;
    //签名
    private TextView tv_qm;
    private TextView tv_zy;
    //签到
    private RelativeLayout rl_qd;
    private TextView tv_qd;

    public static Fragment newInstance() {
        return new PersonInfoIndexFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_info_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx)
                view.findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(getActivity())
                .inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(getActivity())
                .inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(getActivity())
                .inflate(R.layout.profile_content_view, null, false);

        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);

        view.findViewById(R.id.ll_change_pwd).setOnClickListener(this);
        view.findViewById(R.id.ll_fabu).setOnClickListener(this);
        view.findViewById(R.id.iv_more).setOnClickListener(this);
        view.findViewById(R.id.ll_fav).setOnClickListener(this);
        view.findViewById(R.id.iv_sys_image).setOnClickListener(this);
        view.findViewById(R.id.rl_help).setOnClickListener(this);
        view.findViewById(R.id.rl_set).setOnClickListener(this);
        view.findViewById(R.id.rl_share).setOnClickListener(this);
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        tv_username.setOnClickListener(this);
        tv_qm = (TextView) view.findViewById(R.id.tv_qm);
        tv_zy = (TextView) view.findViewById(R.id.tv_zy);
        rl_qd = (RelativeLayout) view.findViewById(R.id.rl_qd);
        tv_qd = (TextView) view.findViewById(R.id.tv_qd);
        rl_qd.setOnClickListener(this);
        view.findViewById(R.id.tv_username).setOnClickListener(this);
        setData();
    }

    @Override
    public void onClick(View v) {
        String user_name;
        switch (v.getId()) {
            //登录
            case R.id.tv_username:
            case R.id.iv_more:
                if (DataManager.getUserInfo() != null) {
                    Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.UserInfo, DataManager.getUserInfo());
                    startActivityForResult(intent, 200);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 101);
                }
                break;
            case R.id.rl_qd:
                SuperCustomToast toast = SuperCustomToast.getInstance(getActivity());
                toast.setDefaultTextColor(Color.WHITE);
                toast.show("签到成功。", R.layout.layout_qd, R.id.content_toast, getActivity());
                break;
            //系统设置
            case R.id.iv_sys_image:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            //我的收藏
            case R.id.ll_fav:
                user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    FrameManager.getInstance().toastPrompt("请先登录");
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 101);
                    return;
                }
                startActivity(new Intent(getActivity(), FavoriteList.class));
                break;
            //我的发布
            case R.id.ll_fabu:
                user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    FrameManager.getInstance().toastPrompt("请先登录");
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 101);
                    return;
                }
                startActivity(new Intent(getActivity(), FabuList.class));
                break;
            //修改密码
            case R.id.ll_change_pwd:
                user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    FrameManager.getInstance().toastPrompt("请先登录");
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 101);
                    return;
                }
                startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
                break;
            //常见问题
            case R.id.rl_help:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            //系统设置
            case R.id.rl_set:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            //系统设置
            case R.id.rl_share:
                ShareUtil shareUtil = new ShareUtil();
                shareUtil.showShare(getActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 101) {
            setData();
        }
    }

    /**
     * 设置自己的数据。
     */
    private void setData() {
        UserInfo userInfo = DataManager.getUserInfo();
        if (userInfo != null) {
            tv_username.setText(userInfo.getNickname());
            if (!TextUtils.isEmpty(userInfo.getZy())){
                String z = userInfo.getZy().substring(0,userInfo.getZy().length()-33);
                tv_zy.setText(z);
            }else {
                tv_zy.setText("职业/暂未设置");
            }
            if (TextUtils.isEmpty(userInfo.getQm())) {
                tv_qm.setText("这个家伙很懒，什么也没留下");
            } else {
                tv_qm.setText(userInfo.getQm());
            }

            String user_image = userInfo.getUser_image();
            if (!TextUtils.isEmpty(user_image)) {
                Glide.with(getActivity()).load(user_image)
                        .placeholder(R.drawable.default_user_logo)
                        .error(R.drawable.default_user_logo).crossFade()
                        .transform(new GlideCircleTransform(getActivity())).into(iv_more);
            }else {
                ImageManager.getInstance().loadResImage(getActivity(), R.drawable.default_user_logo, iv_more);
            }
        }else {
            ImageManager.getInstance().loadResImage(getActivity(), R.drawable.default_user_logo, iv_more);
        }
    }

    public void onEvent(Integer event) {
        switch (event) {
            case 0x02:

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
