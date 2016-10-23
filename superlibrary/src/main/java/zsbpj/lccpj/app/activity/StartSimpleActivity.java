package zsbpj.lccpj.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import zsbpj.lccpj.app.StarterActivity;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:  StartSimpleActivity
 */
public abstract class StartSimpleActivity extends AppCompatActivity {

    private StarterActivity starterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starterActivity = new StarterActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        starterActivity.onDestory();
        starterActivity = null;
    }

    public boolean isProgressShow() {
        return starterActivity != null && starterActivity.isProgressShow();
    }

    public void showProgressLoading(int resId) {
        if (starterActivity == null) {
            return;
        }
        showProgressLoading(getString(resId));
    }

    public void showProgressLoading(String text) {
        if (starterActivity == null) {
            return;
        }
        starterActivity.showProgressLoading(text);
    }

    public void dismissProgressLoading() {
        if (starterActivity == null) {
            return;
        }
        starterActivity.dismissProgressLoading();
    }

    public void showUnBackProgressLoading(int resId) {
        if (starterActivity == null) {
            return;
        }
        showUnBackProgressLoading(getString(resId));
    }

    public void showUnBackProgressLoading(String text) {
        if (starterActivity == null) {
            return;
        }
        starterActivity.showUnBackProgressLoading(text);
    }

    public void dismissUnBackProgressLoading() {
        if (starterActivity == null) {
            return;
        }
        starterActivity.dismissUnBackProgressLoading();
    }

}
