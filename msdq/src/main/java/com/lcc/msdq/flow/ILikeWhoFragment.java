package com.lcc.msdq.flow;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.lcc.frame.fragment.base.BaseLazyLoadFragment;
import com.lcc.msdq.R;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ILikeWhoFragment (自己看的话就不用没后面的关注，但是别人看的话能关注)
 */
public class ILikeWhoFragment extends BaseLazyLoadFragment  implements
        SwipeRefreshLayout.OnRefreshListener{

    static final int ACTION_NONE = 0;
    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;

    public static ILikeWhoFragment newInstance(String fid) {
        ILikeWhoFragment mFragment = new ILikeWhoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", fid);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_ilikewho;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {

    }
}
