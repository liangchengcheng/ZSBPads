package com.lcc.mvp.view;

public interface FeedBackView extends BaseView{

    /**
     * 等待
     */
    void showLoading();

    /**
     * 登录失败：错误信息
     */
    void FeekFail(String msg);

    /**
     * 登录成功
     */
    void FeekSuccess();
}
