package com.lcc.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.lcc.msdq.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    BaseActivity
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 获取当前的系统颜色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

}
