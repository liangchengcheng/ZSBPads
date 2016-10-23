package zsbpj.lccpj.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import butterknife.ButterKnife;

public abstract class LazyFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    private boolean isPrepared;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isCallSetUserVisibleHint = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //在下面进行公用的操作
        ButterKnife.bind(this, view);
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消公用方法
        ButterKnife.unbind(this);
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume && isCallSetUserVisibleHint) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCallSetUserVisibleHint = true;

        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    protected synchronized void initPrepare() {
        if (isPrepared || !isCallSetUserVisibleHint) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    protected abstract int getContentView();

    /**
     * when fragment is visible for the first time,  refresh data only once
     */
    protected abstract void onFirstUserVisible();

    /**
     * when fragment is invisible for the first time
     */
    protected void onFirstUserInvisible() {
        Log.d(TAG, "onFirstUserInvisible() called with: " + "");
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected abstract void onUserVisible();

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected abstract void onUserInvisible();

}
