package com.lcc.frame.fragment.base;

import android.os.Bundle;
import android.view.View;

public abstract class BaseLazyLoadFragment extends BaseFragment {
    //这里暂时取消第一次加载的标记
    private boolean isFirstLoad = true;
    private boolean hasStarted = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getBundle(getArguments());
        initUI(view);
        initData();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (getUserVisibleHint()) {
//            onVisible();
//        }
//    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            hasStarted=true;
//        }else {
//            if (hasStarted) {
//                hasStarted = false;
//            }
//        }
//    }
//
//    private void onVisible() {
//        if (isFirstLoad && isPrepare) {
//            initData();
//            isFirstLoad = false;
//        }
//    }

}
