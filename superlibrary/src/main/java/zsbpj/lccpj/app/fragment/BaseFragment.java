package zsbpj.lccpj.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import zsbpj.lccpj.app.StarterActivity;

public abstract class BaseFragment extends Fragment {

    private StarterActivity starterCommon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starterCommon = new StarterActivity(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    protected abstract int getFragmentLayout();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        starterCommon.onDestory();
        starterCommon = null;
    }

    public boolean isProgressShow() {
        return starterCommon != null && starterCommon.isProgressShow();
    }

    public void showProgressLoading(int resId) {
        if (starterCommon == null) {
            return;
        }
        showProgressLoading(getString(resId));
    }

    public void showProgressLoading(String text) {
        if (starterCommon == null) {
            return;
        }
        starterCommon.showProgressLoading(text);
    }

    public void dismissProgressLoading() {
        if (starterCommon == null) {
            return;
        }
        starterCommon.dismissProgressLoading();
    }

    public void showUnBackProgressLoading(int resId) {
        if (starterCommon == null) {
            return;
        }
        showUnBackProgressLoading(getString(resId));
    }

    public void showUnBackProgressLoading(String text) {
        if (starterCommon == null) {
            return;
        }
        starterCommon.showUnBackProgressLoading(text);
    }

    public void dismissUnBackProgressLoading() {
        if (starterCommon == null) {
            return;
        }
        starterCommon.dismissUnBackProgressLoading();
    }

}
