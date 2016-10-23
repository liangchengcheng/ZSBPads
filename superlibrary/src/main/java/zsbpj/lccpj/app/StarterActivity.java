package zsbpj.lccpj.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;

import zsbpj.lccpj.R;
import zsbpj.lccpj.utils.ObjectUtils;
import zsbpj.lccpj.view.loaddialog.ProgressLoading;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    {  }
 */
public class StarterActivity {

    private ProgressLoading mProgressLoading;
    private ProgressLoading unProgressLoading;
    private Activity activity;
    private boolean progressShow;

    public StarterActivity(Activity activity){
        ObjectUtils.checkNotNull(activity,"activity is null");
        this.activity=activity;
    }

    /**
     * 摧毁这一切吧
     */
    public void onDestory(){
        mProgressLoading=null;
        unProgressLoading=null;
        activity=null;
    }

    /**
     * 显示等待的load
     */
    public void showProgressLoading(int resId){
        if (!isFinishing()){
            showProgressLoading(activity.getString(resId));
        }
    }

    /**
     * 显示等待的load
     */
    public void showProgressLoading(String text) {
        if (mProgressLoading == null) {
            mProgressLoading = new ProgressLoading(activity, R.style.ProgressLoadingTheme);
            mProgressLoading.setCanceledOnTouchOutside(true);
            mProgressLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressShow = false;
                }
            });
        }
        if (!TextUtils.isEmpty(text)) {
            mProgressLoading.text(text);
        } else {
            mProgressLoading.text(null);
        }
        progressShow = true;
        mProgressLoading.show();
    }

    /**
     * 判断这个界面是否关闭了
     */
    private boolean isFinishing() {
        return activity == null || activity.isFinishing();
    }

    /**
     *  是否显示dialog
     */
    public boolean isProgressShow(){
        return progressShow;
    }

    /**
     * 关闭等待
     */
    public void dismissProgressLoading(){
        if (mProgressLoading!=null&&!isFinishing()){
            progressShow=false;
            mProgressLoading.dismiss();
        }
    }

    public void showUnBackProgressLoading(int resId){
        showUnBackProgressLoading(activity.getString(resId));
    }

    /**
     * 返回键的时候不可以撤销
     */
    public void showUnBackProgressLoading(String text) {
        if (unProgressLoading== null) {
            unProgressLoading = new ProgressLoading(activity, R.style.ProgressLoadingTheme) {
                @Override public void onBackPressed() {
                }
            };
        }
        if (!TextUtils.isEmpty(text)) {
            unProgressLoading.text(text);
        } else {
            unProgressLoading.text(null);
        }
        unProgressLoading.show();
    }

    public void dismissUnBackProgressLoading() {
        if (unProgressLoading != null && !isFinishing()) {
            unProgressLoading.dismiss();
        }
    }

}
