package com.lcc.msdq.index;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.PopupMenu;

import com.lcc.adapter.WeekDataAdapter;
import com.lcc.base.BaseFragment;
import com.lcc.entity.ActivityEntity;
import com.lcc.entity.WeekData;
import com.lcc.frame.Advertisements;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.AboutActivity;
import com.lcc.msdq.MainActivity;
import com.lcc.msdq.R;
import com.lcc.msdq.area.LoginDialogFragment;
import com.lcc.msdq.choice.ChoiceTypeoneActivity;
import com.lcc.msdq.favorite.FavoriteList;
import com.lcc.msdq.index.article.IndexMenuActivity;
import com.lcc.msdq.index.activity.IndexWebView;
import com.lcc.msdq.news.NewsIndex;
import com.lcc.mvp.presenter.IndexPresenter;
import com.lcc.mvp.presenter.impl.IndexPresenterImpl;
import com.lcc.mvp.view.IndexView;
import com.lcc.utils.SharePreferenceUtil;
import com.lcc.view.FullyLinearLayoutManager;
import com.lcc.view.loadview.LoadingLayout;
import com.lcc.view.menu.GuillotineAnimation;

import java.util.List;

import de.greenrobot.event.EventBus;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

import android.view.MenuItem;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;
import net.youmi.android.normal.common.ErrorCode;
import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2016年04月21日07:17:52
 * Description: 第一页fragment
 */
public class IndexFragment extends BaseFragment implements IndexView, SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private LinearLayout llAdvertiseBoard;
    private LayoutInflater inflaters;
    private ImageView iv_more;

    private IndexPresenter mPresenter;
    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;
    private WeekDataAdapter mAdapter;

    public static Fragment newInstance() {
        return new IndexFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment, null);
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this);
        view.findViewById(R.id.ll_fav).setOnClickListener(this);
        view.findViewById(R.id.mszb).setOnClickListener(this);
        view.findViewById(R.id.msjl).setOnClickListener(this);
        view.findViewById(R.id.mszz).setOnClickListener(this);
        view.findViewById(R.id.msjq).setOnClickListener(this);
        view.findViewById(R.id.msgx).setOnClickListener(this);
        view.findViewById(R.id.msjz).setOnClickListener(this);
        view.findViewById(R.id.msjt).setOnClickListener(this);
        view.findViewById(R.id.qt).setOnClickListener(this);
        view.findViewById(R.id.ll_news).setOnClickListener(this);

        inflaters = LayoutInflater.from(getActivity());
        llAdvertiseBoard = (LinearLayout) view.findViewById(R.id.llAdvertiseBoard);
        mPresenter = new IndexPresenterImpl(this);
        mPresenter.getActivity();

        onRefresh();

        return view;
    }

    @Override
    public void getFail(String msg) {
        FrameManager.getInstance().toastPrompt(msg);
    }

    @Override
    public void getSuccess(final List<ActivityEntity> list) {
        try {
            if (list != null && list.size() > 0) {
                Advertisements advertisements = new Advertisements(getActivity(), true, inflaters, 3000);
                View view = advertisements.initView(list);
                advertisements.setOnPictureClickListener(new Advertisements.onPictrueClickListener() {
                    @Override
                    public void onClick(int position) {
                        Intent intent = new Intent(getActivity(), IndexWebView.class);
                        intent.putExtra(IndexWebView.KEY_URL, list.get(position).getMid());
                        intent.putExtra(IndexWebView.IMAGE_URL, list.get(position).getActivity_pic());
                        intent.putExtra(IndexWebView.TITLE, list.get(position).getActivity_title());
                        startActivity(intent);
                    }
                });
                llAdvertiseBoard.addView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWeekDataLoading() {

    }

    @Override
    public void getWeekDataEmpty() {

    }

    @Override
    public void getWeekDataFail(String msg) {

    }

    @Override
    public void refreshWeekDataSuccess(List<WeekData> entities) {

    }

    @Override
    public void loadMoreWeekDataSuccess(final List<WeekData> entities) {
        int delay = 0;
        if (TimeUtils.getCurrentTime() - currentTime < DEF_DELAY) {
            delay = DEF_DELAY;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentState = STATE_NORMAL;
                if (entities.isEmpty()) {
                    mAdapter.setHasMoreDataAndFooter(false, false);
                    FrameManager.getInstance().toastPrompt("没有更多数据...");
                } else {
                    mAdapter.appendToList(entities);
                    mAdapter.setHasMoreDataAndFooter(true, false);
                }
                mAdapter.notifyDataSetChanged();
            }
        }, delay);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        String user_name = DataManager.getUserName();
        switch (v.getId()) {
            case R.id.mszb:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "面试准备");
                break;

            case R.id.msjl:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "面试简历");
                break;

            case R.id.msjq:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "面试技巧");
                break;

            case R.id.mszz:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "常问问题");
                break;

            case R.id.msgx:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "面试感想");
                break;

            case R.id.msjz:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "面试举止");
                break;

            case R.id.msjt:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "面试流程");
                break;

            case R.id.qt:
                IndexMenuActivity.startIndexMenuActivity(getActivity(), "其他");
                break;

            //去我的消息的界面
            case R.id.ll_news:
                if (TextUtils.isEmpty(user_name)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(getActivity().getFragmentManager(), "loginDialog");
                    return;
                }
                NewsIndex.startNewsIndex(getActivity());
                break;

            //去我的收藏的界面
            case R.id.ll_fav:
                if (TextUtils.isEmpty(user_name)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(getActivity().getFragmentManager(), "loginDialog");
                    return;
                }

                startActivity(new Intent(getActivity(), FavoriteList.class));
                break;

            case R.id.iv_more:
                PopupMenu popup = new PopupMenu(getActivity(), iv_more);
                popup.getMenuInflater()
                        .inflate(R.menu.index_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(this);
                popup.show();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chnage_type:
                String user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(getActivity().getFragmentManager(), "loginDialog");
                } else {
                    Intent intent = new Intent(getActivity(), ChoiceTypeoneActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.author:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void checkToken() {
        DataManager.deleteAllUser();
        SharePreferenceUtil.setUserTk("");
        FrameManager.getInstance().toastPrompt("身份失效请重现登录");
        LoginDialogFragment dialog = new LoginDialogFragment();
        dialog.show(getActivity().getFragmentManager(), "loginDialog");
    }

}
