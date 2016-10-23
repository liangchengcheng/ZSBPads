package zsbpj.lccpj.app.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import zsbpj.lccpj.network.RetrofitQueue;
import zsbpj.lccpj.network.callback.ErrorModel;
import zsbpj.lccpj.network.callback.GenericCallback;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    StartNetActivity
 */
public abstract class StartNetActivity<T> extends StartSimpleActivity implements GenericCallback<T> {

    private RetrofitQueue<T> networkQueue;

    public abstract View provideSnackbarView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkQueue=new RetrofitQueue<>(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkQueue.cancel();
        networkQueue=null;
    }

    public RetrofitQueue<T> networkQueue() {
        return networkQueue;
    }

    public boolean showMessage() {
        return true;
    }

    @Override public void errorNotFound(ErrorModel errorModel) {
    }

    @Override public void errorUnprocessable(ErrorModel errorModel) {
    }

    @Override public void errorUnauthorized(ErrorModel errorModel) {
    }

    @Override public void errorForbidden(ErrorModel errorModel) {
    }

    @Override public void eNetUnreach(Throwable t) {

    }

    @Override public void errorSocketTimeout(Throwable t) {

    }

    @Override public void error(ErrorModel errorModel) {
        if (showMessage() && errorModel != null && provideSnackbarView() != null) {
            Snackbar.make(provideSnackbarView(),
                    errorModel.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void startRequest() {
    }

    @Override
    public void respondSuccess(T data) {
    }

    @Override public void respondWithError(Throwable t) {
    }

    @Override public void endRequest() {
    }

}
